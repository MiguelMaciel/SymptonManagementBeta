package miguelmaciel.capstone.symptonmanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorMedicine;
import miguelmaciel.capstone.repositorys.Medicine;
import miguelmaciel.capstone.services.MedicineSvc;
import miguelmaciel.capstone.services.MedicineSvcApi;
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
import android.widget.ListView;
import android.widget.Toast;

public class MedicineList extends ActionBarActivity {
	static String idPatient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}

		setContentView(R.layout.activity_medicine_list);

		idPatient = getIntent().getStringExtra("idPatient");

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends ListFragment {

		static private AdaptorMedicine adapterMedicines;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_medicine_list,
					container, false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			ListView listMedicine = getListView();
			listMedicine.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						Medicine med = adapterMedicines.getItem(position);
						Long idMedicine = med.getId();
						String medName = med.getName();

						Intent addPatientMedication = new Intent(view
								.getContext(), MedicationData.class);
						addPatientMedication.putExtra("type", "create");
						addPatientMedication.putExtra("idMedicine",
								idMedicine.toString());
						addPatientMedication.putExtra("idPatient", idPatient);
						addPatientMedication.putExtra("medName", medName);
						startActivity(addPatientMedication);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			adapterMedicines = new AdaptorMedicine(getView().getContext(),
					R.layout.list_medicine, new ArrayList<Medicine>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterMedicines);

		}

		public void onResume() {
			fillList();
			super.onResume();
		}

		public void fillList() {
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
						adapterMedicines.clear();
						for (Medicine med : result) {
							adapterMedicines.add(med);
						}
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
	}
}
