package miguelmaciel.capstone.symptonmanagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorCheckInMedication;
import miguelmaciel.capstone.repositorys.CheckInMedication;
import miguelmaciel.capstone.repositorys.Medication;
import miguelmaciel.capstone.repositorys.Medicine;
import miguelmaciel.capstone.services.CheckInMedicationSvc;
import miguelmaciel.capstone.services.CheckInMedicationSvcApi;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicationSvcApi;
import miguelmaciel.capstone.services.MedicineSvc;
import miguelmaciel.capstone.services.MedicineSvcApi;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class CheckInMedicationActivity extends ActionBarActivity {
	static String throatLevel, eatLevel, takeMeds;
	static Long idCheckIn, idPatient;
	static CheckInMedication checkMed;
	static Button btnNext;
	static private AdaptorCheckInMedication adapterCheckInMedication;
	private static String timeString;
	private static String dateString;
	static String typeOfCheckIn, userType;
	private static Collection<Medicine> medNames;
	private static Collection<Medication> medicationAss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}

		setContentView(R.layout.activity_check_in_medication);

		typeOfCheckIn = getIntent().getStringExtra("type");
		idCheckIn = Long.parseLong(getIntent().getStringExtra("idCheckIn"));
		idPatient = Long.parseLong(getIntent().getStringExtra("idPatient"));
		throatLevel = getIntent().getStringExtra("throat");
		eatLevel = getIntent().getStringExtra("eat");
		takeMeds = getIntent().getStringExtra("meds");

		if (typeOfCheckIn.equals("data")) {
			userType = getIntent().getStringExtra("userType");
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void setActiveCheckInMedicationOnClickHandler(View v) {
		checkMed = (CheckInMedication) v.getTag();
		showDatePickerDialog();
	}

	public void setInactiveCheckInMedicationOnClickHandler(View v) {
		checkMed = (CheckInMedication) v.getTag();
		adapterCheckInMedication.remove(checkMed);
		CheckInMedication newEmptyCheck = new CheckInMedication();
		newEmptyCheck = checkMed;
		newEmptyCheck.setTakestate("0");
		newEmptyCheck.setDate("");
		newEmptyCheck.setTime("");
		adapterCheckInMedication.insert(newEmptyCheck, checkMed.getPosition());
	}

	public class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute, true);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			if (view.isShown()) {
				setTimeString(hourOfDay, minute);
			}
		}

		private void setTimeString(int hourOfDay, int minute) {
			String hour = "" + hourOfDay;
			String min = "" + minute;
			if (hourOfDay < 10)
				hour = "0" + hourOfDay;
			if (minute < 10)
				min = "0" + minute;
			timeString = hour + ":" + min + ":00";

			adapterCheckInMedication.remove(checkMed);
			CheckInMedication newFullCheck = new CheckInMedication();
			newFullCheck = checkMed;
			newFullCheck.setTakestate("1");
			newFullCheck.setDate(dateString);
			newFullCheck.setTime(timeString);
			adapterCheckInMedication.insert(newFullCheck,
					checkMed.getPosition());
		}

	}

	private void showTimePickerDialog() {
		// Open the time picked dialog
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	// DialogFragment used to pick a ToDoItem deadline date
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
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (view.isShown()) {
				setDateString(year, monthOfYear, dayOfMonth);
			}
		}

		private void setDateString(int year, int monthOfYear, int dayOfMonth) {
			// Increment monthOfYear for Calendar/Date -> Time Format setting
			monthOfYear++;
			String mon = "" + monthOfYear;
			String day = "" + dayOfMonth;
			if (monthOfYear < 10)
				mon = "0" + monthOfYear;
			if (dayOfMonth < 10)
				day = "0" + dayOfMonth;
			dateString = year + "-" + mon + "-" + day;

			showTimePickerDialog();
		}

	}

	private void showDatePickerDialog() {
		// Open the date dialog
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends ListFragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_check_in_medication, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			btnNext = (Button) view.findViewById(R.id.checkInMedication_Next);
			btnNext.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						ArrayList<String> arrayID = new ArrayList<String>();
						ArrayList<String> arrayState = new ArrayList<String>();
						ArrayList<String> arrayDate = new ArrayList<String>();
						ArrayList<String> arrayTime = new ArrayList<String>();
						int i, taken = 0;
						for (i = 0; i < adapterCheckInMedication.getCount(); i++) {
							Long idCheck = adapterCheckInMedication.getItem(i)
									.getMedication();
							arrayID.add(idCheck.toString());
							arrayState.add(adapterCheckInMedication.getItem(i)
									.getTakestate());
							arrayDate.add(adapterCheckInMedication.getItem(i)
									.getDate());
							arrayTime.add(adapterCheckInMedication.getItem(i)
									.getTime());

							// Check if take any med
							if (adapterCheckInMedication.getItem(i)
									.getTakestate().equals("1")) {
								taken = taken + 1;
							}
						}

						if (taken > 0) {
							// Navigate to CheckInMessage
							Intent checkFinalMessage = new Intent(getView()
									.getContext(), CheckInMessage.class);
							if (typeOfCheckIn.equals("data")) {
								checkFinalMessage.putExtra("type", "data");
							} else if (typeOfCheckIn.equals("create")) {
								checkFinalMessage.putExtra("type", "create");
							}
							checkFinalMessage.putExtra("userType", userType);
							checkFinalMessage.putExtra("idCheckIn",
									idCheckIn.toString());
							checkFinalMessage.putExtra("idPatient",
									idPatient.toString());
							checkFinalMessage.putExtra("throat", throatLevel);
							checkFinalMessage.putExtra("eat", eatLevel);
							checkFinalMessage.putExtra("meds", takeMeds);
							checkFinalMessage.putStringArrayListExtra("ids",
									arrayID);
							checkFinalMessage.putStringArrayListExtra("state",
									arrayState);
							checkFinalMessage.putStringArrayListExtra("date",
									arrayDate);
							checkFinalMessage.putStringArrayListExtra("time",
									arrayTime);
							startActivity(checkFinalMessage);
						} else {
							Toast.makeText(
									getView().getContext(),
									R.string.you_need_take_at_least_one_medication_,
									Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			// Fill the listview with the rules options
			adapterCheckInMedication = new AdaptorCheckInMedication(getView()
					.getContext(), R.layout.list_checkin_medication,
					new ArrayList<CheckInMedication>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterCheckInMedication);

			getMedicineNames();
		}

		public void getMedicineNames() {
			try {
				final MedicineSvcApi svcMedicine = MedicineSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Collection<Medicine>>() {
					@Override
					public Collection<Medicine> call() throws Exception {
						return svcMedicine.getMedicineList();
					}
				}, new TaskCallback<Collection<Medicine>>() {
					@Override
					public void success(Collection<Medicine> result) {
						medNames = result;
						fillMedicationList();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No Medicine Data", Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void fillMedicationList() {
			try {
				final MedicationSvcApi svcMedication = MedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Collection<Medication>>() {
					@Override
					public Collection<Medication> call() throws Exception {
						return svcMedication.findMedicationByPatient(idPatient);
					}
				}, new TaskCallback<Collection<Medication>>() {
					@Override
					public void success(Collection<Medication> result) {
						medicationAss = result;
						fillCheckInMedicationList();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No Medication Data", Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void fillCheckInMedicationList() {
			try {
				final CheckInMedicationSvcApi svcCheckInMedication = CheckInMedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(
						new Callable<Collection<CheckInMedication>>() {
							@Override
							public Collection<CheckInMedication> call()
									throws Exception {
								return svcCheckInMedication
										.findCheckinmedicationByCheckin(idCheckIn);
							}
						}, new TaskCallback<Collection<CheckInMedication>>() {
							@Override
							public void success(
									Collection<CheckInMedication> result) {
								adapterCheckInMedication.clear();
								for (CheckInMedication checkMed : result) {
									for (Medication med : medicationAss) {
										for (Medicine medN : medNames) {
											if (medN.getId() == med
													.getMedicine()) {
												med.setMedName(medN.getName());
											}
										}
										if (med.getId() == checkMed
												.getMedication()) {
											checkMed.setMedicineName(med
													.getMedName());
											adapterCheckInMedication
													.add(checkMed);
										}
									}
								}
							}

							@Override
							public void error(Exception e) {
								Toast.makeText(getView().getContext(),
										"No Medication Data",
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
