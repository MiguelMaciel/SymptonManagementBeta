package miguelmaciel.capstone.patients;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.CheckInReceiver;
import miguelmaciel.capstone.database.DbHelper;
import miguelmaciel.capstone.repositorys.Patient;
import miguelmaciel.capstone.services.PatientSvc;
import miguelmaciel.capstone.services.PatientSvcApi;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PatientProfileData extends ActionBarActivity {
	// Patient Form
	static EditText etFirstName, etLastName, etBirth, etEmail, etUser, etPass;
	static ImageButton btnCalendar;
	static Menu menuX;
	static DbHelper Db;
	static private Calendar calSet;
	static String timeOfAlarm;

	public static final int ADAPTOR_CREATE_CHECKIN = 2;
	private static Patient pat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_profile_data);

		Db = new DbHelper(this);
		calSet = Calendar.getInstance();

		// Put the icon from the action bar invisible
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			getActionBar().setIcon(
					new ColorDrawable(getResources().getColor(
							android.R.color.transparent)));
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_patient_profile_data, container, false);
			setHasOptionsMenu(true);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			etFirstName = (EditText) view
					.findViewById(R.id.et_PatientProfileData_FirstName);
			etLastName = (EditText) view
					.findViewById(R.id.et_PatientProfileData_LastName);
			etBirth = (EditText) view
					.findViewById(R.id.et_PatientProfileData_DateOfBirth);
			etEmail = (EditText) view
					.findViewById(R.id.et_PatientProfileData_Email);
			etUser = (EditText) view
					.findViewById(R.id.et_PatientProfileData_UserName);
			etPass = (EditText) view
					.findViewById(R.id.et_PatientProfileData_PassWord);

			btnCalendar = (ImageButton) view
					.findViewById(R.id.btn_PatientProfileData_Calendar);

			btnCalendar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					selectDate();
				}
			});

			disableTexts();
			filterList();
		}

		public void selectDate() {
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getFragmentManager(), "datePicker");
		}

		public class DatePickerFragment extends DialogFragment implements
				DatePickerDialog.OnDateSetListener {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month,
						day);
			}

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				if (view.isShown()) {
					setDateString(year, monthOfYear, dayOfMonth);
				}
			}

			private void setDateString(int year, int monthOfYear, int dayOfMonth) {
				// Increment monthOfYear for Calendar/Date -> Time Format
				// setting
				monthOfYear++;
				String mon = "" + monthOfYear;
				String day = "" + dayOfMonth;
				if (monthOfYear < 10)
					mon = "0" + monthOfYear;
				if (dayOfMonth < 10)
					day = "0" + dayOfMonth;
				etBirth.setText(day + "-" + mon + "-" + year);
			}

		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// Inflate the menu; this adds items to the action bar if it is
			// present.
			menuX = menu;
			inflater.inflate(R.menu.patient_profile_data, menu);
			menuX.getItem(2).setVisible(false);
			menuX.getItem(3).setVisible(false);

			super.onCreateOptionsMenu(menu, inflater);
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			if (requestCode == ADAPTOR_CREATE_CHECKIN
					&& resultCode == RESULT_OK) {
				try {
					ArrayList<String> arrayTime = new ArrayList<String>();
					arrayTime = data.getStringArrayListExtra("times");
					for (int i = 0; i < arrayTime.size(); i++) {
						setAlarms(arrayTime.get(i).toString(), i);
						Db.createAlarm(Utils.getIdPatient(), arrayTime.get(i)
								.toString());
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// Db.close();
				}
			}
			super.onActivityResult(requestCode, resultCode, data);
		}

		public void setAlarms(String time, Integer idAlarm) {
			String tempo[];
			tempo = time.split(":");
			calSet.clear();
			calSet.set(Calendar.MONTH,
					Calendar.getInstance().get(Calendar.MONTH));
			calSet.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
			calSet.set(Calendar.DAY_OF_MONTH,
					Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			calSet.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tempo[0]));
			calSet.set(Calendar.MINUTE, Integer.parseInt(tempo[1]));
			calSet.set(Calendar.SECOND, Integer.parseInt(tempo[2]));
			calSet.set(Calendar.MILLISECOND, 0);

			Intent launchIntent = new Intent(getView().getContext(),
					CheckInReceiver.class);
			launchIntent.putExtra("idAlarm", idAlarm.toString());
			launchIntent.putExtra("idPatient", Utils.getIdPatient().toString());
			launchIntent.putExtra("time", time);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(getView()
					.getContext(), Integer.parseInt(idAlarm.toString()),
					launchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager am = (AlarmManager) getActivity().getSystemService(
					Activity.ALARM_SERVICE);
			String calForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(calSet.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date strDate;
			try {
				strDate = sdf.parse(calForm);
				if (new Date().before(strDate)) {
					Log.d("Tempo", "AINDA NAO PASSOU");
				} else if (new Date().equals(strDate)) {
					calSet.add(Calendar.DATE, 1);
					Log.d("Tempo", "IGUAL");
				} else if (new Date().after(strDate)) {
					calSet.add(Calendar.DATE, 1);
					Log.d("Tempo", "JA PASSOU");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			am.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
					AlarmManager.INTERVAL_DAY, pendingIntent);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_PatientProfileData_Doctors) {
				Intent listDoctors = new Intent(getView().getContext(),
						PatientListOfDoctors.class);
				startActivity(listDoctors);
			} else if (id == R.id.action_PatientProfileData_Edit) {
				menuX.getItem(0).setVisible(false);
				menuX.getItem(1).setVisible(false);
				menuX.getItem(2).setVisible(true);
				menuX.getItem(3).setVisible(true);
				menuX.getItem(4).setVisible(false);
				enableTexts();
			} else if (id == R.id.action_PatientProfileData_Cancel) {
				resetMenu();
				disableTexts();
				filterList();
			} else if (id == R.id.action_PatientProfileData_Save) {
				updatePatientProfile();
			} else if (id == R.id.action_PatientProfileData_AlarmConfigurations) {
				Intent alarmConfig = new Intent(getView().getContext(),
						PatientAlarmConfigurations.class);
				startActivityForResult(alarmConfig, ADAPTOR_CREATE_CHECKIN);
			}
			return super.onOptionsItemSelected(item);
		}

		public void updatePatientProfile() {
			try {
				final PatientSvcApi svcPatients = PatientSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Patient>() {
					@Override
					public Patient call() throws Exception {
						pat.setFirstname(etFirstName.getText().toString());
						pat.setLastname(etLastName.getText().toString());
						pat.setEmail(etEmail.getText().toString());
						pat.setDateofbirth(etBirth.getText().toString());
						pat.setUsername(etUser.getText().toString());
						pat.setPassword(etPass.getText().toString());

						return svcPatients.setPatient(pat);
					}
				}, new TaskCallback<Patient>() {

					@Override
					public void success(Patient result) {
						Toast.makeText(getView().getContext(),
								R.string.successfuly_edited, Toast.LENGTH_LONG)
								.show();
						getActivity().onBackPressed();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No Patient",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void resetMenu() {
			menuX.getItem(0).setVisible(true);
			menuX.getItem(1).setVisible(true);
			menuX.getItem(2).setVisible(false);
			menuX.getItem(3).setVisible(false);
			menuX.getItem(4).setVisible(true);
		}

		public void disableTexts() {
			etFirstName.setEnabled(false);
			etLastName.setEnabled(false);
			etBirth.setEnabled(false);
			etEmail.setEnabled(false);
			etUser.setEnabled(false);
			etPass.setEnabled(false);
			btnCalendar.setEnabled(false);
		}

		public void enableTexts() {
			etFirstName.setEnabled(true);
			etLastName.setEnabled(true);
			etBirth.setEnabled(true);
			etEmail.setEnabled(true);
			etUser.setEnabled(true);
			etPass.setEnabled(true);
			btnCalendar.setEnabled(true);
		}

		public void filterList() {
			try {
				final PatientSvcApi svcPatients = PatientSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Patient>() {
					@Override
					public Patient call() throws Exception {
						return svcPatients.getPatientById(Utils.getIdPatient());
					}
				}, new TaskCallback<Patient>() {

					@Override
					public void success(Patient result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						etFirstName.setText(result.getFirstname());
						etLastName.setText(result.getLastname());
						etEmail.setText(result.getEmail());
						etBirth.setText(result.getDateofbirth());
						etUser.setText(result.getUsername());
						etPass.setText(result.getPassword());
						pat = result;
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No Patient",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
