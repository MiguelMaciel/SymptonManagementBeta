package miguelmaciel.capstone.symptonmanagement;

import miguelmaciel.capstone.services.AlarmingSymptonsSvc;
import miguelmaciel.capstone.services.AlertMedicationSvc;
import miguelmaciel.capstone.services.ChatSvc;
import miguelmaciel.capstone.services.CheckInMedicationSvc;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.DoctorPatientsSvc;
import miguelmaciel.capstone.services.DoctorSvc;
import miguelmaciel.capstone.services.MedicationSvc;
import miguelmaciel.capstone.services.MedicineSvc;
import miguelmaciel.capstone.services.PatientSvc;
import miguelmaciel.capstone.utils.Utils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CheckInActivity extends ActionBarActivity {
	static Button btnCheck;
	static Long idCheckIn, idPatient;
	static String typeOfCheckIn;
	private static SharedPreferences prefs;
	static String server, user, pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_check_in);

		idCheckIn = Long.parseLong(getIntent().getStringExtra("idCheckIn"));
		idPatient = Long.parseLong(getIntent().getStringExtra("idPatient"));
		typeOfCheckIn = getIntent().getStringExtra("type");

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setServerDataPatient();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void setServerDataPatient() {
		server = prefs.getString("server", "https://10.0.2.2:8443");
		user = prefs.getString("PatientUser", "pat1");
		pass = prefs.getString("PatientPass", "pass");

		Utils.setServer(server);
		Utils.setPatientUser(user);
		Utils.setPatientPass(pass);
		Utils.setIdPatient(idPatient);

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

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_check_in,
					container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			btnCheck = (Button) view.findViewById(R.id.btn_CheckIn);

			btnCheck.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Intent checkQuestions = new Intent(getView()
								.getContext(), CheckInQuestions.class);
						checkQuestions.putExtra("type", typeOfCheckIn);
						checkQuestions.putExtra("idCheckIn",
								idCheckIn.toString());
						checkQuestions.putExtra("idPatient",
								idPatient.toString());
						startActivity(checkQuestions);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

}
