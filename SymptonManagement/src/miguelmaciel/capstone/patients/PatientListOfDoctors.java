package miguelmaciel.capstone.patients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorDoctor;
import miguelmaciel.capstone.repositorys.Doctor;
import miguelmaciel.capstone.repositorys.DoctorPatients;
import miguelmaciel.capstone.services.DoctorPatientsSvc;
import miguelmaciel.capstone.services.DoctorPatientsSvcApi;
import miguelmaciel.capstone.services.DoctorSvc;
import miguelmaciel.capstone.services.DoctorSvcApi;
import miguelmaciel.capstone.symptonmanagement.ChatCenter;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatientListOfDoctors extends ActionBarActivity {
	static TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}

		setContentView(R.layout.activity_patient_list_of_doctors);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends ListFragment {

		private List<Long> arrayDoctorIDs = new ArrayList<Long>();
		private Collection<Doctor> colDoctor = new ArrayList<Doctor>();
		static private AdaptorDoctor adapterDoctors;

		public PlaceholderFragment() {
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

			tvTitle.setText(R.string.list_of_doctors);

			ListView listCheckIn = getListView();
			listCheckIn.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						Doctor doci = adapterDoctors.getItem(position);
						final Long idDoctor = doci.getId();

						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case DialogInterface.BUTTON_POSITIVE:
									Intent chatDoctor = new Intent(getView()
											.getContext(), ChatCenter.class);
									chatDoctor.putExtra("idDoctor",
											idDoctor.toString());
									chatDoctor.putExtra("idPatient", Utils
											.getIdPatient().toString());
									startActivity(chatDoctor);
									break;
								case DialogInterface.BUTTON_NEGATIVE:
									// No button clicked
									break;
								}
							}
						};
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getView().getContext());
						builder.setMessage("Start Chat?")
								.setPositiveButton(getString(R.string.yes),
										dialogClickListener)
								.setNegativeButton(getString(R.string.no),
										dialogClickListener).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			adapterDoctors = new AdaptorDoctor(getView().getContext(),
					R.layout.list_doctors_of_pacient, new ArrayList<Doctor>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterDoctors);

			getDoctorPatientsIDs();
		}

		public void getDoctorPatientsIDs() {
			try {
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
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						arrayDoctorIDs.clear();
						for (DoctorPatients dp : result) {
							if (dp.getPatient() == Utils.getIdPatient()) {
								arrayDoctorIDs.add(dp.getDoctor());
							}
						}
						getAllDoctors();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								"No Doctors IDs", Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void getAllDoctors() {
			try {
				final DoctorSvcApi svcDoctors = DoctorSvc
						.getOrShowLogin(getView().getContext());

				CallableTask.invoke(new Callable<Collection<Doctor>>() {
					@Override
					public Collection<Doctor> call() throws Exception {
						return svcDoctors.getDoctorList();
					}
				}, new TaskCallback<Collection<Doctor>>() {

					@Override
					public void success(Collection<Doctor> result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						int i;
						colDoctor.clear();
						for (Doctor d : result) {
							for (i = 0; i < arrayDoctorIDs.size(); i++) {
								if (d.getId() == arrayDoctorIDs.get(i)) {
									colDoctor.add(d);
								}
							}
						}
						fillList();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No Doctors",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void fillList() {
			adapterDoctors.clear();
			for (Doctor d : colDoctor) {
				adapterDoctors.add(d);
			}
		}
	}
}
