package miguelmaciel.capstone.symptonmanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.doctors.DoctorHomepage;
import miguelmaciel.capstone.patients.PatientHomepage;
import miguelmaciel.capstone.repositorys.Doctor;
import miguelmaciel.capstone.repositorys.Patient;
import miguelmaciel.capstone.services.AlarmingSymptonsSvc;
import miguelmaciel.capstone.services.AlertMedicationSvc;
import miguelmaciel.capstone.services.ChatSvc;
import miguelmaciel.capstone.services.CheckInMedicationSvc;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.DoctorPatientsSvc;
import miguelmaciel.capstone.services.DoctorSvc;
import miguelmaciel.capstone.services.DoctorSvcApi;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicineSvc;
import miguelmaciel.capstone.services.PatientSvc;
import miguelmaciel.capstone.services.PatientSvcApi;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Authentication extends ActionBarActivity {
	static EditText etUser, etPass;
	static Button btnEnter;
	static Spinner spinUser;
	static final int USERTYPE_PATIENT = 0;
	static final int USERTYPE_DOCTOR = 1;
	static int idUserType;
	static List<Integer> idTypeUser = new ArrayList<Integer>();
	static List<String> descTypeUser = new ArrayList<String>();
	private static SharedPreferences prefs;
	static String server, user, pass, name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}

		setContentView(R.layout.activity_main);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public void onBackPressed() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// AuthenticationActivity.super.onBackPressed();
					// moveTaskToBack(true);
					finish();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					break;
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Authentication.this);
		builder.setMessage("Sure you want quit Sympton Management?")
				.setPositiveButton(getString(R.string.yes), dialogClickListener)
				.setNegativeButton(getString(R.string.no), dialogClickListener)
				.show();
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends Fragment implements
			OnItemSelectedListener {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_authentication,
					container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			etUser = (EditText) view
					.findViewById(R.id.et_Authentication_UserName);
			etPass = (EditText) view
					.findViewById(R.id.et_Authentication_PassWord);
			btnEnter = (Button) view
					.findViewById(R.id.btn_Authentication_Enter);
			spinUser = (Spinner) view
					.findViewById(R.id.spin_Authentication_UserType);

			loadSpinnerUser();

			btnEnter.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					loginIntoSystem();
				}
			});
		}

		private void loadSpinnerUser() {
			descTypeUser.clear();
			idTypeUser.clear();

			idTypeUser.add(USERTYPE_PATIENT);
			descTypeUser.add("Patient");
			idTypeUser.add(USERTYPE_DOCTOR);
			descTypeUser.add("Doctor");

			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
					this.getActivity(), android.R.layout.simple_spinner_item,
					descTypeUser);
			spinnerArrayAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinUser.setAdapter(spinnerArrayAdapter);
			spinUser.setOnItemSelectedListener(this);
		}

		private void loginIntoSystem() {
			final String username = etUser.getText().toString();
			final String password = etPass.getText().toString();

			if (username.length() == 0) {
				Toast.makeText(getView().getContext(), "Insert UserName",
						Toast.LENGTH_SHORT).show();
			} else if (password.length() == 0) {
				Toast.makeText(getView().getContext(), "Insert PassWord",
						Toast.LENGTH_SHORT).show();
			} else {
				try {
					// Enter System
					if (idUserType == USERTYPE_DOCTOR) {
						final DoctorSvcApi svcDoctor = DoctorSvc.init("doctor",
								"https://10.0.2.2:8443", username, password);
						CallableTask.invoke(new Callable<Collection<Doctor>>() {

							@Override
							public Collection<Doctor> call() throws Exception {
								return svcDoctor.getDoctorList();
							}
						}, new TaskCallback<Collection<Doctor>>() {

							@Override
							public void success(Collection<Doctor> result) {
								// OAuth 2.0 grant was successful and we
								// can talk to the server
								int checkExist = 0;
								for (Doctor d : result) {
									if (d.getUsername().equals(username)
											&& d.getPassword().equals(password)) {
										// Start here all OAuth Instanciations
										Editor editor = prefs.edit();
										editor.putString("server",
												"https://10.0.2.2:8443");
										editor.putString("DoctorUser",
												d.getUsername());
										editor.putString("DoctorPass",
												d.getPassword());
										editor.putLong("idDoctor", d.getId());
										editor.commit();

										setServerDataDoctor();

										checkExist = 1;
										Utils.setNameUser(d.getFirstname());

										Intent homeDoctor = new Intent(
												getView().getContext(),
												DoctorHomepage.class);
										startActivity(homeDoctor);
									}
								}
								if (checkExist == 0) {
									Toast.makeText(
											getView().getContext(),
											R.string.login_failed_check_your_internet_connection_and_credentials_,
											Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void error(Exception e) {
								Log.e(Authentication.class.getName(),
										"Error logging in via OAuth.", e);

								Toast.makeText(
										getView().getContext(),
										R.string.login_failed_check_your_internet_connection_and_credentials_,
										Toast.LENGTH_SHORT).show();
							}
						});

					} else if (idUserType == USERTYPE_PATIENT) {
						final PatientSvcApi svcPatient = PatientSvc.init(
								"patient", "https://10.0.2.2:8443", username,
								password);

						CallableTask.invoke(
								new Callable<Collection<Patient>>() {

									@Override
									public Collection<Patient> call()
											throws Exception {
										return svcPatient.getPatientList();
									}
								}, new TaskCallback<Collection<Patient>>() {

									@Override
									public void success(
											Collection<Patient> result) {
										// OAuth 2.0 grant was successful and we
										// can talk to the server
										int checkExist = 0;
										for (Patient p : result) {
											if (p.getUsername()
													.equals(username)
													&& p.getPassword().equals(
															password)) {
												Editor editor = prefs.edit();
												editor.putString("server",
														"https://10.0.2.2:8443");
												editor.putString("PatientUser",
														p.getUsername());
												editor.putString("PatientPass",
														p.getPassword());
												editor.putLong("idPatient",
														p.getId());
												editor.commit();

												setServerDataPatient();

												Utils.setNameUser(p
														.getFirstname());
												checkExist = 1;
												Intent homePatient = new Intent(
														getView().getContext(),
														PatientHomepage.class);
												startActivity(homePatient);
											}
										}

										if (checkExist == 0) {
											Toast.makeText(
													getView().getContext(),
													R.string.login_failed_check_your_internet_connection_and_credentials_,
													Toast.LENGTH_SHORT).show();
										}
									}

									@Override
									public void error(Exception e) {
										Log.e(Authentication.class.getName(),
												"Error logging in via OAuth.",
												e);
										Toast.makeText(
												getView().getContext(),
												R.string.login_failed_check_your_internet_connection_and_credentials_,
												Toast.LENGTH_SHORT).show();
									}
								});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void setServerDataDoctor() {
			server = prefs.getString("server", "https://10.0.2.2:8443");
			user = prefs.getString("DoctorUser", "doc1");
			pass = prefs.getString("DoctorPass", "pass");

			Utils.setServer(server);
			Utils.setDoctorUser(user);
			Utils.setDoctorPass(pass);
			Utils.setIdDoctor(prefs.getLong("idDoctor", 1));

			DoctorSvc.init("doctor", server, user, pass);
			PatientSvc.init("doctor", server, user, pass);
			DoctorPatientsSvc.init("doctor", server, user, pass);
			AlarmingSymptonsSvc.init("doctor", server, user, pass);
			CheckInSvc.init("doctor", server, user, pass);
			AlertMedicationSvc.init("doctor", server, user, pass);
			MedicineSvc.init("doctor", server, user, pass);
			MedicationSvc.init("doctor", server, user, pass);
			ChatSvc.init("doctor", server, user, pass);
			CheckInMedicationSvc.init("doctor", server, user, pass);
		}

		public void setServerDataPatient() {
			server = prefs.getString("server", "https://10.0.2.2:8443");
			user = prefs.getString("PatientUser", "pat1");
			pass = prefs.getString("PatientPass", "pass");

			Utils.setServer(server);
			Utils.setPatientUser(user);
			Utils.setPatientPass(pass);
			Utils.setIdPatient(prefs.getLong("idPatient", 1));

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

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			parent.getItemAtPosition(position);
			switch (parent.getId()) {
			case R.id.spin_Authentication_UserType:
				int posicao = spinUser.getSelectedItemPosition();
				idUserType = idTypeUser.get(posicao);
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	}

}
