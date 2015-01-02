package miguelmaciel.capstone.doctors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorAlarmingSymptons;
import miguelmaciel.capstone.adapters.AdaptorPatient;
import miguelmaciel.capstone.repositorys.AlarmingSymptons;
import miguelmaciel.capstone.repositorys.CheckIn;
import miguelmaciel.capstone.repositorys.DoctorPatients;
import miguelmaciel.capstone.repositorys.Patient;
import miguelmaciel.capstone.services.AlarmingSymptonsSvc;
import miguelmaciel.capstone.services.AlarmingSymptonsSvcApi;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.CheckInSvcApi;
import miguelmaciel.capstone.services.DoctorPatientsSvc;
import miguelmaciel.capstone.services.DoctorPatientsSvcApi;
import miguelmaciel.capstone.services.PatientSvc;
import miguelmaciel.capstone.services.PatientSvcApi;
import miguelmaciel.capstone.symptonmanagement.CheckInQuestions;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorHomepage extends ActionBarActivity {
	// Doctors Form
	static Button btnPatients, btnAlarmingSymptons, btnProfile, btnLogOut;

	// Patients Form
	static EditText etPatientName;
	static ImageButton btnSearch;

	// AlarmingSymptons Form
	static TextView tvTitle;

	static String idPatientListClickAlarmingSymptons,
			idCheckInListClickAlarmingSymptons;

	static SimpleCursorAdapter adapter;
	static int numCursor;
	static String backType = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}
		setContentView(R.layout.activity_doctor_homepage);

		try {
			backType = getIntent().getStringExtra("backType");
		} catch (Exception e) {
			backType = "";
			e.printStackTrace();
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public void onBackPressed() {
		if (Utils.getSubMenu() == 0) {
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						finish();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			};
			AlertDialog.Builder builder = new AlertDialog.Builder(
					DoctorHomepage.this);
			builder.setMessage(R.string.sure_you_want_quit)
					.setPositiveButton(getString(R.string.yes),
							dialogClickListener)
					.setNegativeButton(getString(R.string.no),
							dialogClickListener).show();
		} else if (Utils.getSubMenu() == 1) {
			Utils.setSubMenu(0);
			super.onBackPressed();
		}
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_doctor_homepage,
					container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			btnPatients = (Button) view
					.findViewById(R.id.btn_DoctorHomePage_Patients);
			btnAlarmingSymptons = (Button) view
					.findViewById(R.id.btn_DoctorHomePage_AlarmingSymptons);
			btnProfile = (Button) view
					.findViewById(R.id.btn_DoctorHomePage_ProfileData);
			btnLogOut = (Button) view
					.findViewById(R.id.btn_DoctorHomePage_LogOut);

			btnPatients.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Utils.setSubMenu(1);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.replace(R.id.container,
							new PlaceholderFragmentPatients()).commit();
				}
			});

			btnAlarmingSymptons.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Utils.setSubMenu(1);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.replace(R.id.container,
							new PlaceholderFragmentAlarmingSymptons()).commit();
				}
			});

			btnProfile.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent profileData = new Intent(getView().getContext(),
							DoctorProfileData.class);
					startActivity(profileData);
				}
			});

			btnLogOut.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().onBackPressed();
				}
			});
		}
	}

	public static class PlaceholderFragmentPatients extends ListFragment {

		private List<Long> arrayPatientIDs = new ArrayList<Long>();
		private Collection<Patient> colPatient = new ArrayList<Patient>();
		static private AdaptorPatient adapterPatients;

		public PlaceholderFragmentPatients() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_doctors_patients_list, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			etPatientName = (EditText) view
					.findViewById(R.id.et_DoctorsPacientsList_PatientName);
			btnSearch = (ImageButton) view
					.findViewById(R.id.btn_DoctorsPacientsList_Search);

			btnSearch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					closeKeyBoard();
					try {
						filterList();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			ListView listPatients = getListView();
			listPatients.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						Patient pati = adapterPatients.getItem(position);
						Long idPatient = pati.getId();
						Intent patientFile = new Intent(view.getContext(),
								DoctorPatientFile.class);
						patientFile.putExtra("idPatient", idPatient.toString());
						patientFile.putExtra("backType", "list");
						startActivity(patientFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			adapterPatients = new AdaptorPatient(getView().getContext(),
					R.layout.list_patients, new ArrayList<Patient>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterPatients);

			etPatientName.setText("");
			try {
				getDoctorPatientsIDs();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void closeKeyBoard() {
			InputMethodManager inputManager = (InputMethodManager) getView()
					.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getView().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

		public void getAllPatients() {
			final PatientSvcApi svcPatients = PatientSvc
					.getOrShowLogin(getView().getContext());

			CallableTask.invoke(new Callable<Collection<Patient>>() {
				@Override
				public Collection<Patient> call() throws Exception {
					return svcPatients.getPatientList();
				}
			}, new TaskCallback<Collection<Patient>>() {

				@Override
				public void success(Collection<Patient> result) {
					// OAuth 2.0 grant was successful and we can talk to the
					// server
					int i;
					colPatient.clear();
					for (Patient p : result) {
						for (i = 0; i < arrayPatientIDs.size(); i++) {
							if (p.getId() == arrayPatientIDs.get(i)) {
								colPatient.add(p);
							}
						}
					}

					fillList();
				}

				@Override
				public void error(Exception e) {
					Toast.makeText(getView().getContext(), "No Patients",
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		public void getDoctorPatientsIDs() {
			final DoctorPatientsSvcApi svcDoctorPatient = DoctorPatientsSvc
					.getOrShowLogin(getView().getContext());

			CallableTask.invoke(new Callable<Collection<DoctorPatients>>() {
				@Override
				public Collection<DoctorPatients> call() throws Exception {
					return svcDoctorPatient.getDoctorpatientsList();
				}
			}, new TaskCallback<Collection<DoctorPatients>>() {

				@Override
				public void success(Collection<DoctorPatients> result) {
					// OAuth 2.0 grant was successful and we can talk to the
					// server
					arrayPatientIDs.clear();
					for (DoctorPatients dp : result) {
						if (dp.getDoctor() == Utils.getIdDoctor()) {
							arrayPatientIDs.add(dp.getPatient());
						}
					}
					getAllPatients();
				}

				@Override
				public void error(Exception e) {
					Toast.makeText(getView().getContext(), "No Patients IDs",
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		public void fillList() {
			try {
				adapterPatients.clear();
				for (Patient p : colPatient) {
					adapterPatients.add(p);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void filterList() {
			try {
				adapterPatients.clear();
				for (Patient p : colPatient) {
					if (p.getFirstname()
							.toLowerCase()
							.contains(
									etPatientName.getText().toString()
											.toLowerCase())
							|| p.getLastname().contains(
									etPatientName.getText().toString())) {
						adapterPatients.add(p);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static class PlaceholderFragmentAlarmingSymptons extends
			ListFragment {

		static private AdaptorAlarmingSymptons adapterAlarming;
		static Long idCheckIn;

		public PlaceholderFragmentAlarmingSymptons() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_doctor_listtemplate, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			tvTitle = (TextView) view
					.findViewById(R.id.tv_DoctorListTemplate_Title);

			tvTitle.setText(R.string.alarming_symptons);

			ListView listCheckIn = getListView();
			listCheckIn.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						AlarmingSymptons alar = adapterAlarming
								.getItem(position);
						idCheckIn = alar.getCheckin();
						getPatientID();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			adapterAlarming = new AdaptorAlarmingSymptons(getView()
					.getContext(), R.layout.list_alarming_symptons,
					new ArrayList<AlarmingSymptons>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterAlarming);
		}

		@Override
		public void onResume() {
			super.onResume();
			fillAlarmingList();
		}

		public void fillAlarmingList() {
			try {
				final AlarmingSymptonsSvcApi svcAlarming = AlarmingSymptonsSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(
						new Callable<Collection<AlarmingSymptons>>() {
							@Override
							public Collection<AlarmingSymptons> call()
									throws Exception {
								return svcAlarming.getAlarmingSymptonsList();
							}
						}, new TaskCallback<Collection<AlarmingSymptons>>() {
							@Override
							public void success(
									Collection<AlarmingSymptons> result) {
								adapterAlarming.clear();
								for (AlarmingSymptons alarm : result) {
									adapterAlarming.add(alarm);
								}
							}

							@Override
							public void error(Exception e) {
								Toast.makeText(getView().getContext(),
										"No AlarmingSymptons Data",
										Toast.LENGTH_SHORT).show();
							}
						});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void getPatientID() {
			try {
				final CheckInSvcApi svcCheckIn = CheckInSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<CheckIn>() {
					@Override
					public CheckIn call() throws Exception {
						return svcCheckIn.getCheckInById(idCheckIn);
					}
				}, new TaskCallback<CheckIn>() {
					@Override
					public void success(CheckIn result) {
						Intent checkQuestions = new Intent(getView()
								.getContext(), CheckInQuestions.class);
						Long idPatient = result.getPatient();
						checkQuestions.putExtra("type", "data");
						checkQuestions.putExtra("idCheckIn",
								idCheckIn.toString());
						checkQuestions.putExtra("idPatient",
								idPatient.toString());
						checkQuestions.putExtra("userType", "doctor-sympton");
						startActivity(checkQuestions);
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No PatientID",
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
