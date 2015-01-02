package miguelmaciel.capstone.patients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorAlertMedication;
import miguelmaciel.capstone.adapters.AdaptorMedication;
import miguelmaciel.capstone.adapters.AdaptorMissedCheckIn;
import miguelmaciel.capstone.repositorys.AlertMedication;
import miguelmaciel.capstone.repositorys.CheckIn;
import miguelmaciel.capstone.repositorys.Medication;
import miguelmaciel.capstone.repositorys.Medicine;
import miguelmaciel.capstone.services.AlertMedicationSvc;
import miguelmaciel.capstone.services.AlertMedicationSvcApi;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.CheckInSvcApi;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicationSvcApi;
import miguelmaciel.capstone.services.MedicineSvc;
import miguelmaciel.capstone.services.MedicineSvcApi;
import miguelmaciel.capstone.symptonmanagement.CheckInActivity;
import miguelmaciel.capstone.symptonmanagement.MedicationData;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PatientHomepage extends ActionBarActivity {
	// Patient Form
	static Button btnMissed, btnAlerts, btnMeds, btnRecord, btnProfile,
			btnLogOut;

	// Template Form
	static RadioButton rbAsc, rbDesc;
	static TextView tvTitle;

	static private AdaptorAlertMedication adapterAlerts;
	static private AdaptorMissedCheckIn adapterMissedCheckIns;

	// private static SharedPreferences prefs;
	static String server, user, pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}

		setContentView(R.layout.activity_patient_homepage);

		/*
		 * prefs = PreferenceManager.getDefaultSharedPreferences(this); server =
		 * prefs.getString("server", "https://10.0.2.2:8443"); user =
		 * prefs.getString("PatientUser", "pat1"); pass =
		 * prefs.getString("PatientPass", "pass"); Utils.setServer(server);
		 * Utils.setPatientUser(user); Utils.setPatientPass(pass);
		 */

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
						// No button clicked
						break;
					}
				}
			};
			AlertDialog.Builder builder = new AlertDialog.Builder(
					PatientHomepage.this);
			builder.setMessage(R.string.sure_you_want_logout)
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
			View rootView = inflater.inflate(
					R.layout.fragment_patient_homepage, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			btnMissed = (Button) view
					.findViewById(R.id.btn_PatientHomePage_MissedCheckIns);
			btnAlerts = (Button) view
					.findViewById(R.id.btn_PatientHomePage_Alerts);
			btnMeds = (Button) view
					.findViewById(R.id.btn_PatientHomePage_Medication);
			btnRecord = (Button) view
					.findViewById(R.id.btn_PatientHomePage_CheckInRecord);
			btnProfile = (Button) view
					.findViewById(R.id.btn_PatientHomePage_ProfileData);
			btnLogOut = (Button) view
					.findViewById(R.id.btn_PatientHomePage_LogOut);

			btnMissed.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Utils.setSubMenu(1);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.replace(R.id.container,
							new PlaceholderFragmentMissedCheckIns()).commit();
				}
			});

			btnAlerts.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Utils.setSubMenu(1);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.replace(R.id.container,
							new PlaceholderFragmentAlerts()).commit();
				}
			});

			btnMeds.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Utils.setSubMenu(1);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.replace(R.id.container,
							new PlaceholderFragmentMedication()).commit();
				}
			});

			btnRecord.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent fillCheckIns = new Intent(getView().getContext(),
							PatientCheckInList.class);
					fillCheckIns.putExtra("idPatient", Utils.getIdPatient()
							.toString());
					fillCheckIns.putExtra("userType", "patient");
					startActivity(fillCheckIns);

					/*
					 * Utils.setSubMenu(1); FragmentManager fragmentManager =
					 * getFragmentManager(); FragmentTransaction
					 * fragmentTransaction = fragmentManager
					 * .beginTransaction();
					 * fragmentTransaction.addToBackStack(null);
					 * fragmentTransaction.replace(R.id.container, new
					 * PlaceholderFragmentCheckInRecord()).commit();
					 */
				}
			});

			btnProfile.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent profileData = new Intent(getView().getContext(),
							PatientProfileData.class);
					startActivity(profileData);
				}
			});

			btnLogOut.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getActivity().onBackPressed();
				}
			});
		}
	}

	public static class PlaceholderFragmentMissedCheckIns extends ListFragment {

		public PlaceholderFragmentMissedCheckIns() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_patient_listsorttemplate, container,
					false);
			return rootView;
		}

		@Override
		public void onResume() {
			super.onResume();
			fillMissingCheckIn();
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			rbAsc = (RadioButton) view
					.findViewById(R.id.rb_PatientSortListTemplate_SortDateAsc);
			rbDesc = (RadioButton) view
					.findViewById(R.id.rb_PatientSortListTemplate_SortDateDesc);
			tvTitle = (TextView) view
					.findViewById(R.id.tv_PatientSortListTemplate_Title);

			adapterMissedCheckIns = new AdaptorMissedCheckIn(getView()
					.getContext(), R.layout.list_patient_missed_checkin,
					new ArrayList<CheckIn>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterMissedCheckIns);

			tvTitle.setText(R.string.missed_checkins);

			rbAsc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					fillMissingCheckIn();
				}
			});

			rbDesc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					fillMissingCheckIn();
				}
			});

			ListView listMissigCheckIns = getListView();
			listMissigCheckIns
					.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							try {
								NotificationManager notificationManager = (NotificationManager) getActivity()
										.getSystemService(
												Context.NOTIFICATION_SERVICE);
								notificationManager.cancelAll();

								CheckIn check = adapterMissedCheckIns
										.getItem(position);
								Long idCheckIn = check.getId();
								Intent intent = new Intent(getView()
										.getContext(), CheckInActivity.class);
								intent.putExtra("type", "create");
								intent.putExtra("idCheckIn",
										idCheckIn.toString());
								intent.putExtra("idPatient", Utils
										.getIdPatient().toString());
								startActivity(intent);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

		public void fillMissingCheckIn() {
			try {
				final CheckInSvcApi svcChecks = CheckInSvc
						.getOrShowLogin(getView().getContext());

				if (rbAsc.isChecked()) {
					CallableTask.invoke(new Callable<Collection<CheckIn>>() {
						@Override
						public Collection<CheckIn> call() throws Exception {
							return svcChecks
									.findCheckinByPatientAndStateOrderByDatecheckinAsc(
											Utils.getIdPatient(), "0");
						}
					}, new TaskCallback<Collection<CheckIn>>() {
						@Override
						public void success(Collection<CheckIn> result) {
							// OAuth 2.0 grant was successful and we
							// can talk to the server
							adapterMissedCheckIns.clear();
							for (CheckIn mc : result) {
								adapterMissedCheckIns.add(mc);
							}
						}

						@Override
						public void error(Exception e) {
							Toast.makeText(getView().getContext(), "No Missed",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else if (rbDesc.isChecked()) {
					CallableTask.invoke(new Callable<Collection<CheckIn>>() {
						@Override
						public Collection<CheckIn> call() throws Exception {
							return svcChecks
									.findCheckinByPatientAndStateOrderByDatecheckinDesc(
											Utils.getIdPatient(), "0");
						}
					}, new TaskCallback<Collection<CheckIn>>() {

						@Override
						public void success(Collection<CheckIn> result) {
							// OAuth 2.0 grant was successful and we
							// can talk to the server
							adapterMissedCheckIns.clear();
							for (CheckIn mc : result) {
								adapterMissedCheckIns.add(mc);
							}
						}

						@Override
						public void error(Exception e) {
							Toast.makeText(getView().getContext(), "No Missed",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class PlaceholderFragmentAlerts extends ListFragment {

		public PlaceholderFragmentAlerts() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_patient_listsorttemplate, container,
					false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			rbAsc = (RadioButton) view
					.findViewById(R.id.rb_PatientSortListTemplate_SortDateAsc);
			rbDesc = (RadioButton) view
					.findViewById(R.id.rb_PatientSortListTemplate_SortDateDesc);
			tvTitle = (TextView) view
					.findViewById(R.id.tv_PatientSortListTemplate_Title);

			adapterAlerts = new AdaptorAlertMedication(getView().getContext(),
					R.layout.list_medication_alerts,
					new ArrayList<AlertMedication>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterAlerts);

			tvTitle.setText(R.string.alerts);

			rbAsc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					fillAlertMedication();
				}
			});

			rbDesc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					fillAlertMedication();
				}
			});

			ListView listMedAlerts = getListView();
			listMedAlerts.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						AlertMedication alert = adapterAlerts.getItem(position);
						Long idPatientMedication = alert.getMedication();
						Intent medData = new Intent(view.getContext(),
								MedicationData.class);
						medData.putExtra("type", "patient");
						medData.putExtra("idPatientMedication",
								idPatientMedication.toString());
						startActivity(medData);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		}

		@Override
		public void onResume() {
			super.onResume();
			fillAlertMedication();
		}

		public void fillAlertMedication() {
			try {
				final AlertMedicationSvcApi svcAlerts = AlertMedicationSvc
						.getOrShowLogin(getView().getContext());

				if (rbAsc.isChecked()) {
					CallableTask.invoke(
							new Callable<Collection<AlertMedication>>() {
								@Override
								public Collection<AlertMedication> call()
										throws Exception {
									return svcAlerts
											.findAlertmedicationByPatientOrderByDateAsc(Utils
													.getIdPatient());
								}
							}, new TaskCallback<Collection<AlertMedication>>() {

								@Override
								public void success(
										Collection<AlertMedication> result) {
									// OAuth 2.0 grant was successful and we
									// can talk to the server
									adapterAlerts.clear();
									for (AlertMedication am : result) {
										adapterAlerts.add(am);
									}
								}

								@Override
								public void error(Exception e) {
									Toast.makeText(getView().getContext(),
											"No Alerts", Toast.LENGTH_SHORT)
											.show();
								}
							});
				} else if (rbDesc.isChecked()) {
					CallableTask.invoke(
							new Callable<Collection<AlertMedication>>() {
								@Override
								public Collection<AlertMedication> call()
										throws Exception {
									return svcAlerts
											.findAlertmedicationByPatientOrderByDateDesc(Utils
													.getIdPatient());
								}
							}, new TaskCallback<Collection<AlertMedication>>() {

								@Override
								public void success(
										Collection<AlertMedication> result) {
									// OAuth 2.0 grant was successful and we
									// can talk to the server
									adapterAlerts.clear();
									for (AlertMedication am : result) {
										adapterAlerts.add(am);
									}
								}

								@Override
								public void error(Exception e) {
									Toast.makeText(getView().getContext(),
											"No Alerts", Toast.LENGTH_SHORT)
											.show();
								}
							});
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class PlaceholderFragmentMedication extends ListFragment {

		static private AdaptorMedication adapterMedications;
		private Collection<Medicine> medNames;

		public PlaceholderFragmentMedication() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_patient_listtemplate, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			tvTitle = (TextView) view
					.findViewById(R.id.tv_PatientListTemplate_Title);

			tvTitle.setText(R.string.medication);

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
								addPatientMedication
										.putExtra("type", "patient");
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

			adapterMedications = new AdaptorMedication(getView().getContext(),
					R.layout.list_patient_medication,
					new ArrayList<Medication>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterMedications);

			getMedicineNames();
		}

		@Override
		public void onResume() {
			fillMedicationList();
			super.onResume();
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
						return svcMedication.findMedicationByPatient(Utils
								.getIdPatient());
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

	/*public static class PlaceholderFragmentCheckInRecord extends ListFragment {

		public PlaceholderFragmentCheckInRecord() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_patient_listsorttemplate, container,
					false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			rbAsc = (RadioButton) view
					.findViewById(R.id.rb_PatientSortListTemplate_SortDateAsc);
			rbDesc = (RadioButton) view
					.findViewById(R.id.rb_PatientSortListTemplate_SortDateDesc);
			tvTitle = (TextView) view
					.findViewById(R.id.tv_PatientSortListTemplate_Title);

			tvTitle.setText(R.string.checkin_record);

			rbAsc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

				}
			});

			rbDesc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// Teste enquanto nao ha graph
					Intent fillCheckIns = new Intent(getView().getContext(),
							PatientCheckInList.class);
					fillCheckIns.putExtra("idPatient", Utils.getIdPatient()
							.toString());
					fillCheckIns.putExtra("userType", "patient");
					startActivity(fillCheckIns);
				}
			});
		}
	}*/

}
