package miguelmaciel.capstone.symptonmanagement;

import java.util.Collection;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.repositorys.CheckIn;
import miguelmaciel.capstone.repositorys.CheckInMedication;
import miguelmaciel.capstone.repositorys.Medication;
import miguelmaciel.capstone.services.CheckInMedicationSvc;
import miguelmaciel.capstone.services.CheckInMedicationSvcApi;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.CheckInSvcApi;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicationSvcApi;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class CheckInQuestions extends ActionBarActivity {
	static RadioButton rbQ1Control, rbQ1Moderate, rbQ1Severe, rbQ2No, rbQ2Some,
			rbQ2Yes, rbQ3Yes, rbQ3No;
	static Button btnNext;
	static Long idCheckIn, idPatient;
	static String throatLevel, eatLevel, takeMeds;

	static String typeOfCheckIn, userType;
	static Collection<Medication> colMed;
	static Collection<CheckInMedication> colCheckInMed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}
		setContentView(R.layout.activity_check_in_questions);

		idCheckIn = Long.parseLong(getIntent().getStringExtra("idCheckIn"));
		idPatient = Long.parseLong(getIntent().getStringExtra("idPatient"));
		typeOfCheckIn = getIntent().getStringExtra("type");

		if (typeOfCheckIn.equals("data")) {
			userType = getIntent().getStringExtra("userType");
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
					R.layout.fragment_check_in_questions, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			rbQ1Control = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q1_Controlled);
			rbQ1Moderate = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q1_Moderate);
			rbQ1Severe = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q1_Severe);
			rbQ2No = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q2_No);
			rbQ2Some = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q2_Some);
			rbQ2Yes = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q2_Yes);
			rbQ3Yes = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q3_Yes);
			rbQ3No = (RadioButton) view
					.findViewById(R.id.rb_CheckInQuestions_Q3_No);
			btnNext = (Button) view
					.findViewById(R.id.btn_CheckInQuestions_Next);

			btnNext.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						if (typeOfCheckIn.equals("data")) {
							checkOptions();

							if (rbQ3No.isChecked()) {
								Intent messageCheckIn = new Intent(getView()
										.getContext(), CheckInMessage.class);
								messageCheckIn.putExtra("type", typeOfCheckIn);
								messageCheckIn.putExtra("userType", userType);
								messageCheckIn.putExtra("idCheckIn",
										idCheckIn.toString());
								messageCheckIn.putExtra("throat", throatLevel);
								messageCheckIn.putExtra("eat", eatLevel);
								messageCheckIn.putExtra("meds", takeMeds);
								messageCheckIn.putExtra("idPatient",
										idPatient.toString());
								startActivity(messageCheckIn);
							} else if (rbQ3Yes.isChecked()) {
								// Navigate to CheckInMedication
								Intent medicationList = new Intent(getView()
										.getContext(),
										CheckInMedicationActivity.class);
								medicationList.putExtra("type", typeOfCheckIn);
								medicationList.putExtra("userType", userType);
								medicationList.putExtra("idCheckIn",
										idCheckIn.toString());
								medicationList.putExtra("throat", throatLevel);
								medicationList.putExtra("eat", eatLevel);
								medicationList.putExtra("meds", takeMeds);
								medicationList.putExtra("idPatient",
										idPatient.toString());
								startActivity(medicationList);
							}
						} else if (typeOfCheckIn.equals("create")) {
							if (!rbQ1Control.isChecked()
									&& !rbQ1Moderate.isChecked()
									&& !rbQ1Severe.isChecked()) {
								Toast.makeText(getView().getContext(),
										R.string.select_throat_pain_level_,
										Toast.LENGTH_LONG).show();
							}
							if (!rbQ2No.isChecked() && !rbQ2Some.isChecked()
									&& !rbQ2Yes.isChecked()) {
								Toast.makeText(
										getView().getContext(),
										R.string.select_stop_eat_drink_question,
										Toast.LENGTH_LONG).show();
							}
							if (!rbQ3No.isChecked() && !rbQ3Yes.isChecked()) {
								Toast.makeText(getView().getContext(),
										R.string.did_you_take_meds_,
										Toast.LENGTH_LONG).show();
							}

							checkOptions();

							if (rbQ3No.isChecked()) {
								Intent messageCheckIn = new Intent(getView()
										.getContext(), CheckInMessage.class);
								if (typeOfCheckIn.equals("data")) {
									messageCheckIn.putExtra("type",
											typeOfCheckIn);
								} else if (typeOfCheckIn.equals("create")) {
									messageCheckIn.putExtra("type",
											typeOfCheckIn);
								}
								messageCheckIn.putExtra("idCheckIn",
										idCheckIn.toString());
								messageCheckIn.putExtra("throat", throatLevel);
								messageCheckIn.putExtra("eat", eatLevel);
								messageCheckIn.putExtra("meds", takeMeds);
								messageCheckIn.putExtra("idPatient",
										idPatient.toString());
								startActivity(messageCheckIn);
							} else if (rbQ3Yes.isChecked()) {
								getPatientMedications();
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			if (typeOfCheckIn.equals("data")) {
				disableUI();
				getCheckInData();
			} else if (typeOfCheckIn.equals("create")) {
				enableUI();
			}

		}

		public void enableUI() {
			rbQ1Control.setEnabled(true);
			rbQ1Moderate.setEnabled(true);
			rbQ1Severe.setEnabled(true);
			rbQ2No.setEnabled(true);
			rbQ2Some.setEnabled(true);
			rbQ2Yes.setEnabled(true);
			rbQ3No.setEnabled(true);
			rbQ3Yes.setEnabled(true);
		}

		public void disableUI() {
			rbQ1Control.setEnabled(false);
			rbQ1Moderate.setEnabled(false);
			rbQ1Severe.setEnabled(false);
			rbQ2No.setEnabled(false);
			rbQ2Some.setEnabled(false);
			rbQ2Yes.setEnabled(false);
			rbQ3No.setEnabled(false);
			rbQ3Yes.setEnabled(false);
		}

		public void checkOptions() {
			if (rbQ1Control.isChecked()) {
				throatLevel = "0";
			} else if (rbQ1Moderate.isChecked()) {
				throatLevel = "1";
			} else if (rbQ1Severe.isChecked()) {
				throatLevel = "2";
			}

			if (rbQ2No.isChecked()) {
				eatLevel = "0";
			} else if (rbQ2Some.isChecked()) {
				eatLevel = "1";
			} else if (rbQ2Yes.isChecked()) {
				eatLevel = "2";
			}

			if (rbQ3No.isChecked()) {
				takeMeds = "0";
			} else if (rbQ3Yes.isChecked()) {
				takeMeds = "1";
			}
		}

		public void getPatientMedications() {
			try {
				final MedicationSvcApi svcMedications = MedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Collection<Medication>>() {
					@Override
					public Collection<Medication> call() throws Exception {
						return svcMedications.findMedicationByPatientAndActive(
								Utils.getIdPatient(), "1");
					}
				}, new TaskCallback<Collection<Medication>>() {
					@Override
					public void success(Collection<Medication> result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						colMed = result;
						// If have medications associated
						if (result.size() > 0) {
							getExistingCheckInMedicationList();
						} else {// dont have any meds
							Toast.makeText(
									getView().getContext(),
									R.string.don_t_have_any_medication_associated_,
									Toast.LENGTH_LONG).show();
						}
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No Patient Medication", Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void getExistingCheckInMedicationList() {
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
								colCheckInMed = result;
								createCheckInMedicationTask();
							}

							@Override
							public void error(Exception e) {
								Toast.makeText(getView().getContext(),
										"No CheckIn Medication Data",
										Toast.LENGTH_SHORT).show();
							}
						});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void createCheckInMedicationTask() {
			try {
				final CheckInMedicationSvcApi svcCheckMedications = CheckInMedicationSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						int exist = 0;
						for (Medication med : colMed) {
							for (CheckInMedication check : colCheckInMed) {
								if (check.getMedication() == med.getId()
										&& check.getCheckin() == idCheckIn) {
									exist = exist + 1;
								} else {
									exist = exist + 0;
								}
							}
							if (exist == 0) {
								CheckInMedication addCheckMed = new CheckInMedication(
										idCheckIn, med.getId(), "0", "", "");
								svcCheckMedications
										.addCheckInMedication(addCheckMed);
							} else {
								exist = 0;
							}
						}
						return null;
					}
				}, new TaskCallback<Void>() {

					@Override
					public void success(Void result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						Intent medicationList = new Intent(getView()
								.getContext(), CheckInMedicationActivity.class);
						medicationList.putExtra("idCheckIn",
								idCheckIn.toString());
						medicationList.putExtra("type", typeOfCheckIn);
						medicationList.putExtra("throat", throatLevel);
						medicationList.putExtra("eat", eatLevel);
						medicationList.putExtra("meds", takeMeds);
						medicationList.putExtra("idPatient",
								idPatient.toString());
						startActivity(medicationList);
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No Meds Added",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void getCheckInData() {
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
						String throat = result.getThroatlevel();
						String eat = result.getPainlevel();
						String take = result.getTakemed();

						if (throat.equals("0")) {
							rbQ1Control.setChecked(true);
						} else if (throat.equals("1")) {
							rbQ1Moderate.setChecked(true);
						} else if (throat.equals("2")) {
							rbQ1Severe.setChecked(true);
						}

						if (eat.equals("0")) {
							rbQ2No.setChecked(true);
						} else if (eat.equals("1")) {
							rbQ2Some.setChecked(true);
						} else if (eat.equals("2")) {
							rbQ2Yes.setChecked(true);
						}

						if (take.equals("0")) {
							rbQ3No.setChecked(true);
						} else if (take.equals("1")) {
							rbQ3Yes.setChecked(true);
						}
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No AlarmingSymptons Data", Toast.LENGTH_SHORT)
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
