package miguelmaciel.capstone.patients;

import java.util.ArrayList;
import java.util.Calendar;

import miguelmaciel.capstone.adapters.AdaptorAlarmTime;
import miguelmaciel.capstone.adapters.AlarmTimes;
import miguelmaciel.capstone.adapters.CheckInReceiver;
import miguelmaciel.capstone.database.DbHelper;
import miguelmaciel.capstone.database.SymptonProvider;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.Utils;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class PatientAlarmConfigurations extends ActionBarActivity {
	static RadioButton rbNotification, rbAlarm;
	static Button btnAddTime, btnConfirm;
	static private AdaptorAlarmTime adapter;
	static String timeString;
	static int numAlarmInitial;

	private static DbHelper db;
	public static final int FILL_getAlarmTimes = 0;
	public static final int FILL_getSystemPreferences = 1;
	static int numCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}
		setContentView(R.layout.activity_patient_alarm_configurations);

		db = new DbHelper(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void removeAlarmTimeOnClickHandler(View v) {
		AlarmTimes itemToRemove = (AlarmTimes) v.getTag();
		adapter.remove(itemToRemove);
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends ListFragment implements
			LoaderManager.LoaderCallbacks<Cursor> {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_patient_alarm_configurations, container,
					false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			rbAlarm = (RadioButton) view.findViewById(R.id.rbAlarm);
			rbNotification = (RadioButton) view
					.findViewById(R.id.rbNotification);
			btnAddTime = (Button) view
					.findViewById(R.id.btn_PatientAlarmConfigurations_AddTime);
			btnConfirm = (Button) view
					.findViewById(R.id.btn_PatientAlarmConfigurations_Confirm);

			btnAddTime.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Open the time picker dialog
					showTimePickerDialog();
				}
			});

			btnConfirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						if (adapter.getCount() < 4) {
							Toast.makeText(getView().getContext(),
									R.string.please_insert_at_least_4_alarms,
									Toast.LENGTH_LONG).show();
						} else {
							cancelAlarms();
							new deleteAlarmsTask().execute();
						}
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			// Fill the listview with the rules options
			adapter = new AdaptorAlarmTime(getView().getContext(),
					R.layout.time_layout, new ArrayList<AlarmTimes>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapter);

			fillList();
		}

		private class deleteAlarmsTask extends AsyncTask<Void, Void, Boolean> {
			Boolean success = false;

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					db.deleteAlarms(Utils.getIdPatient());
					success = true;
				} catch (SQLException e) {
					success = false;
					e.printStackTrace();
				} finally {
					// db.close();
				}
				return success;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (result == true) {
					new setNotificationTask().execute();
				}
			}
		}

		private class setNotificationTask extends
				AsyncTask<Void, Void, Boolean> {
			Boolean success = false;

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					db.deleteNotifications(Utils.getIdPatient());
					if (rbAlarm.isChecked()) {
						db.createNotification(Utils.getIdPatient(), "0");
					} else if (rbNotification.isChecked()) {
						db.createNotification(Utils.getIdPatient(), "1");
					}
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
					ArrayList<String> arrayTimes = new ArrayList<String>();
					int conta;
					for (conta = 0; conta < adapter.getCount(); conta++) {
						arrayTimes.add(adapter.getItem(conta).getAlarmTime());
					}
					Intent data = new Intent();
					data.putStringArrayListExtra("times", arrayTimes);
					getActivity().setResult(RESULT_OK, data);
					getActivity().finish();
				}
			}
		}

		public static class TimePickerFragment extends DialogFragment implements
				TimePickerDialog.OnTimeSetListener {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current time as the default values for the picker
				final Calendar c = Calendar.getInstance();
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);

				// Create a new instance of TimePickerDialog and return it
				return new TimePickerDialog(getActivity(), this, hour, minute,
						true);
			}

			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if (view.isShown()) {
					setTimeString(hourOfDay, minute);
				}
			}

			private static void setTimeString(int hourOfDay, int minute) {
				String hour = "" + hourOfDay;
				String min = "" + minute;
				if (hourOfDay < 10)
					hour = "0" + hourOfDay;
				if (minute < 10)
					min = "0" + minute;
				timeString = hour + ":" + min + ":00";
				adapter.insert(new AlarmTimes(timeString), adapter.getCount());
			}

		}

		private void showTimePickerDialog() {
			// Open the time picked dialog
			DialogFragment newFragment = new TimePickerFragment();
			newFragment.show(getActivity().getSupportFragmentManager(),
					"timePicker");
		}

		public void cancelAlarms() {
			// Cancel the alarm by idAlarm
			try {
				Integer x;
				for (x = 0; x < numAlarmInitial; x++) {
					Intent launchIntent = new Intent(getView().getContext(),
							CheckInReceiver.class);
					launchIntent.putExtra("idAlarm", x.toString());

					PendingIntent pendingIntent = PendingIntent.getBroadcast(
							getView().getContext(),
							Integer.parseInt(x.toString()), launchIntent,
							PendingIntent.FLAG_CANCEL_CURRENT);
					AlarmManager am = (AlarmManager) getActivity()
							.getSystemService(Activity.ALARM_SERVICE);
					am.cancel(pendingIntent);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void fillList() {
			getLoaderManager().destroyLoader(FILL_getAlarmTimes);
			getLoaderManager().initLoader(FILL_getAlarmTimes, null, this);
		}

		public void checkNotifications() {
			getLoaderManager().destroyLoader(FILL_getSystemPreferences);
			getLoaderManager()
					.initLoader(FILL_getSystemPreferences, null, this);
		}

		public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
			String[] projection = { "AlarmTime" };
			String[] projectionSystemPrefs = { "TypeNotification" };

			CursorLoader cursorLoader;
			switch (loaderID) {
			case FILL_getAlarmTimes:
				numCursor = FILL_getAlarmTimes;
				cursorLoader = new CursorLoader(getActivity()
						.getApplicationContext(),
						SymptonProvider.CONTENT_URI_getAlarmTimes, projection,
						Utils.getIdPatient().toString(), null, null);
				break;
			case FILL_getSystemPreferences:
				numCursor = FILL_getSystemPreferences;
				cursorLoader = new CursorLoader(getActivity()
						.getApplicationContext(),
						SymptonProvider.CONTENT_URI_getSystemPreferences,
						projectionSystemPrefs, Utils.getIdPatient().toString(),
						null, null);
				break;
			default:
				// An invalid id was passed in
				return null;
			}
			return cursorLoader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			if (numCursor == FILL_getAlarmTimes) {
				numAlarmInitial = cursor.getCount();
				if (cursor.moveToFirst()) {
					do {
						adapter.insert(
								new AlarmTimes(cursor.getString(cursor
										.getColumnIndex("AlarmTime"))), adapter
										.getCount());
					} while (cursor.moveToNext());
				}
				checkNotifications();
			} else if (numCursor == FILL_getSystemPreferences) {
				if (cursor.moveToFirst()) {
					String typeNot = cursor.getString(cursor
							.getColumnIndex("TypeNotification"));
					if (typeNot.equals("0")) {
						rbAlarm.setChecked(true);
					} else if (typeNot.equals("1")) {
						rbNotification.setChecked(true);
					}
				}
			}
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}
	}
}
