package miguelmaciel.capstone.patients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorCheckIn;
import miguelmaciel.capstone.repositorys.CheckIn;
import miguelmaciel.capstone.services.CheckInSvc;
import miguelmaciel.capstone.services.CheckInSvcApi;
import miguelmaciel.capstone.symptonmanagement.CheckInQuestions;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PatientCheckInList extends ActionBarActivity {
	// Template Form
	static RadioButton rbAsc, rbDesc;
	static TextView tvTitle;
	static String idPatient, userType;
	static private AdaptorCheckIn adapterCheckIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}
		setContentView(R.layout.activity_patient_check_in_list);

		idPatient = getIntent().getStringExtra("idPatient");
		userType = getIntent().getStringExtra("userType");

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends ListFragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_patient_check_in_list, container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			rbAsc = (RadioButton) view
					.findViewById(R.id.rb_PatientCheckInList_SortDateAsc);
			rbDesc = (RadioButton) view
					.findViewById(R.id.rb_PatientCheckInList_SortDateDesc);
			tvTitle = (TextView) view
					.findViewById(R.id.tv_PatientCheckInList_Title);

			tvTitle.setText(R.string.checkin_record);

			rbAsc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					fillCheckIn();
				}
			});

			rbDesc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					fillCheckIn();
				}
			});

			ListView listCheckIn = getListView();
			listCheckIn.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						CheckIn check = adapterCheckIn.getItem(position);
						Long idCheckIn = check.getId();
						Intent checkQuestions = new Intent(getView()
								.getContext(), CheckInQuestions.class);
						checkQuestions.putExtra("type", "data");
						checkQuestions.putExtra("userType", userType);
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

			// Fill the listview with the rules options
			adapterCheckIn = new AdaptorCheckIn(getView().getContext(),
					R.layout.list_patient_checkin, new ArrayList<CheckIn>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterCheckIn);
		}

		@Override
		public void onResume() {
			super.onResume();
			fillCheckIn();
		}

		public void fillCheckIn() {
			try {
				final CheckInSvcApi svcChecks = CheckInSvc
						.getOrShowLogin(getView().getContext());

				if (rbAsc.isChecked()) {
					CallableTask.invoke(new Callable<Collection<CheckIn>>() {
						@Override
						public Collection<CheckIn> call() throws Exception {
							return svcChecks
									.findCheckinByPatientAndStateOrderByDatecheckinAsc(
											Long.parseLong(idPatient), "1");
						}
					}, new TaskCallback<Collection<CheckIn>>() {
						@Override
						public void success(Collection<CheckIn> result) {
							// OAuth 2.0 grant was successful and we
							// can talk to the server
							adapterCheckIn.clear();
							for (CheckIn mc : result) {
								adapterCheckIn.add(mc);
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
											Long.parseLong(idPatient), "1");
						}
					}, new TaskCallback<Collection<CheckIn>>() {

						@Override
						public void success(Collection<CheckIn> result) {
							// OAuth 2.0 grant was successful and we
							// can talk to the server
							adapterCheckIn.clear();
							for (CheckIn mc : result) {
								adapterCheckIn.add(mc);
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
}
