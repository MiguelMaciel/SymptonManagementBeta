package miguelmaciel.capstone.doctors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorMedication;
import miguelmaciel.capstone.patients.PatientCheckInList;
import miguelmaciel.capstone.repositorys.Medication;
import miguelmaciel.capstone.repositorys.Medicine;
import miguelmaciel.capstone.repositorys.Patient;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicationSvcApi;
import miguelmaciel.capstone.services.MedicineSvc;
import miguelmaciel.capstone.services.MedicineSvcApi;
import miguelmaciel.capstone.services.PatientSvc;
import miguelmaciel.capstone.services.PatientSvcApi;
import miguelmaciel.capstone.symptonmanagement.ChatCenter;
import miguelmaciel.capstone.symptonmanagement.MedicationData;
import miguelmaciel.capstone.symptonmanagement.MedicineList;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorPatientFile extends ActionBarActivity {
	static EditText etName, etBirth, etEmail;
	static Long idPatient;
	static String backType;
	// Template Form
	static RadioButton rbAsc, rbDesc;
	static TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_patient_file);

		idPatient = Long.parseLong(getIntent().getStringExtra("idPatient"));
		backType = getIntent().getStringExtra("backType");

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
	public static class PlaceholderFragment extends ListFragment {

		static private AdaptorMedication adapterMedications;
		private Collection<Medicine> medNames;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_doctor_patient_file, container, false);
			setHasOptionsMenu(true);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			etName = (EditText) view
					.findViewById(R.id.et_DoctorPatientFile_Name);
			etBirth = (EditText) view
					.findViewById(R.id.et_DoctorPatientFile_Birth);
			etEmail = (EditText) view
					.findViewById(R.id.et_DoctorPatientFile_Email);

			ListView listPatientMedication = getListView();
			listPatientMedication
					.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							try {
								Medication medication = adapterMedications
										.getItem(position);
								Long idPatientMedication = medication.getId();

								Intent addPatientMedication = new Intent(view
										.getContext(), MedicationData.class);
								addPatientMedication.putExtra("type", "data");
								addPatientMedication.putExtra("medName",
										medication.getMedName());
								addPatientMedication.putExtra(
										"idPatientMedication",
										idPatientMedication.toString());
								startActivity(addPatientMedication);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

			disableTexts();

			adapterMedications = new AdaptorMedication(getView().getContext(),
					R.layout.list_patient_medication,
					new ArrayList<Medication>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterMedications);

			getMedicineNames();
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// Inflate the menu; this adds items to the action bar if it is
			// present.
			inflater.inflate(R.menu.doctor_patient_file, menu);
			super.onCreateOptionsMenu(menu, inflater);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_DoctorProfileFile_CheckInRecord) {
				try {
					Intent checkRecord = new Intent(getView().getContext(),
							PatientCheckInList.class);
					checkRecord.putExtra("idPatient", idPatient.toString());
					checkRecord.putExtra("userType", "doctor");
					startActivity(checkRecord);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (id == R.id.action_DoctorProfileFile_Message) {
				try {
					Intent chatDoctor = new Intent(getView().getContext(),
							ChatCenter.class);
					chatDoctor.putExtra("idDoctor", Utils.getIdDoctor()
							.toString());
					chatDoctor.putExtra("idPatient", idPatient.toString());
					startActivity(chatDoctor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (id == R.id.action_DoctorProfileFile_AddMedication) {
				try {
					Intent addMeds = new Intent(getView().getContext(),
							MedicineList.class);
					addMeds.putExtra("idPatient", idPatient.toString());
					startActivity(addMeds);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return super.onOptionsItemSelected(item);
		}

		public void closeKeyBoard() {
			InputMethodManager inputManager = (InputMethodManager) getView()
					.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getView().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

		public void disableTexts() {
			etName.setEnabled(false);
			etBirth.setEnabled(false);
			etEmail.setEnabled(false);
		}

		@Override
		public void onResume() {
			super.onResume();
			fillUI();
		}

		public void fillUI() {
			try {
				final PatientSvcApi svcPatients = PatientSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Patient>() {
					@Override
					public Patient call() throws Exception {
						return svcPatients.getPatientById(idPatient);
					}
				}, new TaskCallback<Patient>() {

					@Override
					public void success(Patient result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						etName.setText(result.getFirstname() + " "
								+ result.getLastname());
						etEmail.setText(result.getEmail());
						etBirth.setText(result.getDateofbirth());

						fillMedicationList();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No Patient Data", Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
						return svcMedication.findMedicationByPatientAndActive(
								idPatient, "1");
					}
				}, new TaskCallback<Collection<Medication>>() {
					@Override
					public void success(Collection<Medication> result) {
						adapterMedications.clear();
						for (Medication med : result) {
							for (Medicine medN : medNames) {
								if (medN.getId() == med.getMedicine()) {
									med.setMedName(medN.getName());
									adapterMedications.add(med);
								}
							}
						}
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
