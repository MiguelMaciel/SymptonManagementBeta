package miguelmaciel.capstone.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.database.DbHelper;
import miguelmaciel.capstone.repositorys.CheckIn;
import miguelmaciel.capstone.services.AlarmingSymptonsSvc;
import miguelmaciel.capstone.services.AlertMedicationSvc;
import miguelmaciel.capstone.services.ChatSvc;
import miguelmaciel.capstone.services.CheckInMedicationSvc;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.CheckInSvcApi;
import miguelmaciel.capstone.services.DoctorPatientsSvc;
import miguelmaciel.capstone.services.DoctorSvc;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicineSvc;
import miguelmaciel.capstone.services.PatientSvc;
import miguelmaciel.capstone.symptonmanagement.CheckInActivity;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

public class CheckInReceiver extends BroadcastReceiver {
	Context contesto;
	static String timeOfAlarm, idAlarm, idPatient, typeNotification;
	static Long idCheckIn;
	static private Calendar calSet;
	DbHelper db;
	private static SharedPreferences prefs;
	static String server, user, pass;

	@Override
	public void onReceive(Context context, Intent intent) {
		contesto = context;
		calSet = Calendar.getInstance();
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);

		prefs = PreferenceManager.getDefaultSharedPreferences(contesto);

		idAlarm = intent.getStringExtra("idAlarm");
		idPatient = intent.getStringExtra("idPatient");
		timeOfAlarm = intent.getStringExtra("time");

		setServerDataPatient();

		createCheckIn();

		db = new DbHelper(context);
	}

	public void setServerDataPatient() {
		server = prefs.getString("server", "https://10.0.2.2:8443");
		user = prefs.getString("PatientUser", "pat1");
		pass = prefs.getString("PatientPass", "pass");

		Utils.setServer(server);
		Utils.setPatientUser(user);
		Utils.setPatientPass(pass);
		Utils.setIdPatient(Long.parseLong(idPatient));

		DoctorSvc.init("patient", server, user, pass);
		PatientSvc.init("patient", server, user, pass);
		DoctorPatientsSvc.init("patient", server, user, pass);
		AlarmingSymptonsSvc.init("patient", server, user, pass);
		CheckInSvc.init("patient", server, user, pass);
		AlertMedicationSvc.init("patient", server, user, pass);
		MedicineSvc.init("patient", server, user, pass);
		MedicationSvc.init("patient", server, user, pass);
		ChatSvc.init("patient", server, user, pass);
		CheckInMedicationSvc.init("patient", server, user, pass);
	}

	public void createCheckIn() {
		// final CheckInSvcApi svcCheckIn = CheckInSvc.init("patient", server,
		// user, pass);
		final CheckInSvcApi svcCheckIn = CheckInSvc.getOrShowLogin(contesto);

		CallableTask.invoke(new Callable<CheckIn>() {
			@Override
			public CheckIn call() throws Exception {
				String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());

				CheckIn check = new CheckIn(dateTime, "0", "", "", "", "", Long
						.parseLong(idPatient));

				return svcCheckIn.addCheckIn(check);
			}
		}, new TaskCallback<CheckIn>() {
			@Override
			public void success(CheckIn result) {
				getCheckInID();
			}

			@Override
			public void error(Exception e) {
			}
		});
	}

	public void getCheckInID() {
		final CheckInSvcApi svcCheckIn = CheckInSvc.getOrShowLogin(contesto);

		CallableTask.invoke(new Callable<Collection<CheckIn>>() {
			@Override
			public Collection<CheckIn> call() throws Exception {
				return svcCheckIn.getCheckInListOrderByIdDesc();
			}
		}, new TaskCallback<Collection<CheckIn>>() {
			@Override
			public void success(Collection<CheckIn> result) {
				List<Long> arrayID = new ArrayList<Long>();
				for (CheckIn ci : result) {
					arrayID.add(ci.getId());
				}
				if (arrayID.size() > 0) {
					long idIni, idFim;
					idIni = arrayID.get(0);
					idFim = arrayID.get(arrayID.size() - 1);
					if (idIni > idFim) {
						idCheckIn = idIni;
					} else {
						idCheckIn = idFim;
					}
					new notifyPatientTask().execute();
				}
			}

			@Override
			public void error(Exception e) {
			}
		});
	}

	private class notifyPatientTask extends AsyncTask<Void, Void, Boolean> {
		Boolean success = false;

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				typeNotification = db.getNotificationTypeByPatientID(Long
						.parseLong(idPatient));
				success = true;
			} catch (SQLException e) {
				success = false;
				e.printStackTrace();
			} finally {
				db.close();
			}
			return success;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result == true) {
				// new getLastPatientCheckInTask().execute();
				openNotificationSystem();
			}
		}
	}

	public void openNotificationSystem() {
		if (typeNotification.equals("0")) {
			openCheckIn();
		} else if (typeNotification.equals("1")) {
			sendNotification();
		}
	}

	public void sendNotification() {
		// Notification Android
		NotificationManager manager = (NotificationManager) contesto
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(contesto, CheckInActivity.class);
		intent.putExtra("idCheckIn", idCheckIn.toString());
		intent.putExtra("idPatient", idPatient);
		intent.putExtra("type", "create");

		PendingIntent pendingIntent = PendingIntent.getActivity(contesto,
				Integer.parseInt(idAlarm), intent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		// Create notification with the time it was fired
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				contesto);
		builder.setSmallIcon(R.drawable.ic_launcher)
				.setTicker("Alert " + idAlarm)
				.setWhen(System.currentTimeMillis()).setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_SOUND)
				.setContentTitle("Check In")
				.setContentText("Check In of: " + timeOfAlarm)
				.setContentIntent(pendingIntent);
		Notification note = builder.build();
		manager.notify(Integer.parseInt(idAlarm), note);
	}

	public void openCheckIn() {
		try {
			calSet.clear();
			calSet.set(Calendar.MONTH,
					Calendar.getInstance().get(Calendar.MONTH));
			calSet.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
			calSet.set(Calendar.DAY_OF_MONTH,
					Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			calSet.set(Calendar.HOUR_OF_DAY,
					Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
			calSet.set(Calendar.MINUTE,
					Calendar.getInstance().get(Calendar.MINUTE));
			calSet.set(Calendar.SECOND,
					Calendar.getInstance().get(Calendar.SECOND));
			calSet.set(Calendar.MILLISECOND,
					Calendar.getInstance().get(Calendar.MILLISECOND));

			Intent intent = new Intent(contesto, CheckInActivity.class);
			intent.putExtra("idCheckIn", idCheckIn.toString());
			intent.putExtra("idPatient", idPatient);
			intent.putExtra("type", "create");

			PendingIntent pendingIntent = PendingIntent.getActivity(contesto,
					Integer.parseInt(idAlarm), intent,
					PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager am = (AlarmManager) contesto
					.getSystemService(Activity.ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
					pendingIntent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
