package miguelmaciel.capstone.symptonmanagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.repositorys.AlertMedication;
import miguelmaciel.capstone.repositorys.Medication;
import miguelmaciel.capstone.services.AlertMedicationSvc;
import miguelmaciel.capstone.services.AlertMedicationSvcApi;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicationSvcApi;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.RadioButton;
import android.widget.Toast;

public class MedicationData extends ActionBarActivity {
	static EditText etMedName, etStartDate, etEndDate, etDose, etObs;
	static RadioButton rbActive, rbInactive;
	static ImageButton btnStart, btnEnd;
	static Long idMedicine, idPatient, idPatientMedication, idDoctor;
	static String type, medName;
	static Menu menuX;
	static int numCal;
	private static Medication med;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_data);

		type = getIntent().getStringExtra("type");

		if (type.equals("create")) {
			idMedicine = Long.parseLong(getIntent()
					.getStringExtra("idMedicine"));
			idPatient = Long.parseLong(getIntent().getStringExtra("idPatient"));
			medName = getIntent().getStringExtra("medName");
		} else if (type.equals("data")) {
			medName = getIntent().getStringExtra("medName");
			idPatientMedication = Long.parseLong(getIntent().getStringExtra(
					"idPatientMedication"));
		} else if (type.equals("patient")) {
			idPatientMedication = Long.parseLong(getIntent().getStringExtra(
					"idPatientMedication"));
		}

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
			View rootView = inflater.inflate(R.layout.fragment_medication_data,
					container, false);
			setHasOptionsMenu(true);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			etMedName = (EditText) view
					.findViewById(R.id.et_MedicationData_MedicationName);
			etStartDate = (EditText) view
					.findViewById(R.id.et_MedicationData_StartDate);
			etEndDate = (EditText) view
					.findViewById(R.id.et_MedicationData_EndDate);
			etDose = (EditText) view.findViewById(R.id.et_MedicationData_Dose);
			etObs = (EditText) view
					.findViewById(R.id.et_MedicationData_Observations);
			rbActive = (RadioButton) view
					.findViewById(R.id.rb_MedicationData_Active);
			rbInactive = (RadioButton) view
					.findViewById(R.id.rb_MedicationData_Inactive);
			
			btnStart = (ImageButton) view.findViewById(R.id.btn_MedicationData_CalendarStart);
			btnEnd = (ImageButton) view.findViewById(R.id.btn_MedicationData_CalendarEnd);
			
			btnStart.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					numCal = 0;
					selectDate();
				}
			});
			
			btnEnd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					numCal = 1;
					selectDate();
				}
			});

			if (type.equals("create")) {
				enableTexts();
				etMedName.setText(medName);
			} else {
				disableTexts();
			}
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
				
				if(numCal==0){
					etStartDate.setText(day + "-" + mon + "-" + year);
				}else if(numCal==1){
					etEndDate.setText(day + "-" + mon + "-" + year);
				}
			}

		}
		
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			menuX = menu;
			inflater.inflate(R.menu.medication_data, menu);

			if (type.equals("create")) {
				menuX.getItem(0).setVisible(false);
				menuX.getItem(1).setVisible(true);
				menuX.getItem(2).setVisible(true);
			} else if (type.equals("data")) {
				menuX.getItem(0).setVisible(true);
				menuX.getItem(1).setVisible(false);
				menuX.getItem(2).setVisible(false);
			} else if (type.equals("patient")) {
				menuX.getItem(0).setVisible(false);
				menuX.getItem(1).setVisible(false);
				menuX.getItem(2).setVisible(false);
			}

			super.onCreateOptionsMenu(menu, inflater);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.action_MedicationData_Edit) {
				menuX.getItem(0).setVisible(false);
				menuX.getItem(1).setVisible(true);
				menuX.getItem(2).setVisible(true);
				enableTexts();
			} else if (id == R.id.action_MedicationData_Save) {
				// Add and quit
				try {
					if (type.equals("create")) {
						addMedication();
					} else if (type.equals("data")) {
						updateMedication();
						disableTexts();
						resetMenus();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (id == R.id.action_MedicationData_Cancel) {
				try {
					if (type.equals("create")) {
						getActivity().onBackPressed();
					} else if (type.equals("data")) {
						fillList();
						disableTexts();
						resetMenus();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public void onResume() {
			super.onResume();
			if (type.equals("data")) {
				fillList();
			} else if (type.equals("patient")) {
				fillList();
			}
		}

		public void resetMenus() {
			menuX.getItem(0).setVisible(true);
			menuX.getItem(1).setVisible(false);
			menuX.getItem(2).setVisible(false);
		}

		public void disableTexts() {
			etMedName.setEnabled(false);
			etStartDate.setEnabled(false);
			etEndDate.setEnabled(false);
			etDose.setEnabled(false);
			etObs.setEnabled(false);
			rbActive.setEnabled(false);
			rbInactive.setEnabled(false);
			btnEnd.setEnabled(false);
			btnStart.setEnabled(false);
		}

		public void enableTexts() {
			etStartDate.setEnabled(true);
			etEndDate.setEnabled(true);
			etDose.setEnabled(true);
			etObs.setEnabled(true);
			rbActive.setEnabled(true);
			rbInactive.setEnabled(true);
			btnEnd.setEnabled(true);
			btnStart.setEnabled(true);
		}

		public void addMedication() {
			try {
				final MedicationSvcApi svcMedication = MedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						String active;
						if (rbActive.isChecked()) {
							active = "1";
						} else {
							active = "0";
						}

						Medication med = new Medication(etStartDate.getText()
								.toString(), etEndDate.getText().toString(),
								etDose.getText().toString(), active, etObs
										.getText().toString(), idPatient, Utils
										.getIdDoctor(), idMedicine);
						return svcMedication.addMedication(med);
					}
				}, new TaskCallback<Void>() {
					@Override
					public void success(Void result) {
						Toast.makeText(getView().getContext(),
								R.string.successfully_created,
								Toast.LENGTH_LONG).show();
						getActivity().onBackPressed();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"Error on Add Medication", Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void updateMedication() {
			try {
				final MedicationSvcApi svcMedication = MedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Medication>() {
					@Override
					public Medication call() throws Exception {
						String active;
						if (rbActive.isChecked()) {
							active = "1";
						} else {
							active = "0";
						}

						med.setActive(active);
						med.setDose(etDose.getText().toString());
						med.setObservations(etObs.getText().toString());
						med.setStartdate(etStartDate.getText().toString());
						med.setEnddate(etEndDate.getText().toString());

						return svcMedication.setMedication(med);
					}
				}, new TaskCallback<Medication>() {
					@Override
					public void success(Medication result) {
						sendMedAlert("data");
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "Error",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void sendMedAlert(final String type) {
			final AlertMedicationSvcApi svcAlertMedication = AlertMedicationSvc
					.getOrShowLogin(getView().getContext());

			CallableTask.invoke(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String message = "";
					if (type.equals("create")) {
						message = "Medication Added";
					} else if (type.equals("data")) {
						message = "Medication Change";
					}
					String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());

					AlertMedication alertMed = new AlertMedication(message,
							date, idPatient, idDoctor, idPatientMedication);
					return svcAlertMedication.addAlertMedication(alertMed);
				}
			}, new TaskCallback<Void>() {
				@Override
				public void success(Void result) {
					if (type.equals("create")) {
						Toast.makeText(getView().getContext(),
								R.string.successfully_created,
								Toast.LENGTH_LONG).show();
						getActivity().onBackPressed();
					} else if (type.equals("data")) {
						Toast.makeText(getView().getContext(),
								"Successfully Edited", Toast.LENGTH_LONG)
								.show();
						getActivity().onBackPressed();
					}
				}

				@Override
				public void error(Exception e) {
					Toast.makeText(getView().getContext(), "Error Send Alarm",
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		public void fillList() {
			try {
				final MedicationSvcApi svcMedication = MedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Medication>() {
					@Override
					public Medication call() throws Exception {
						return svcMedication
								.getMedicationById(idPatientMedication);
					}
				}, new TaskCallback<Medication>() {
					@Override
					public void success(Medication result) {
						etMedName.setText(medName);
						etStartDate.setText(result.getStartdate());
						etEndDate.setText(result.getEnddate());
						etDose.setText(result.getDose());
						etObs.setText(result.getObservations());

						String state = result.getActive();
						if (state.equals("0")) {
							rbInactive.setChecked(true);
						} else if (state.equals("1")) {
							rbActive.setChecked(true);
						}

						idPatient = result.getPatient();
						idMedicine = result.getMedicine();
						idDoctor = result.getDoctor();

						med = result;
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
	}
}
