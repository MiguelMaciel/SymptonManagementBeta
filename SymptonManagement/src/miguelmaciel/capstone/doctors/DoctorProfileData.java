package miguelmaciel.capstone.doctors;

import java.util.concurrent.Callable;

import miguelmaciel.capstone.repositorys.Doctor;
import miguelmaciel.capstone.services.DoctorSvc;
import miguelmaciel.capstone.services.DoctorSvcApi;
import miguelmaciel.capstone.symptonmanagement.R;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorProfileData extends ActionBarActivity {
	static Menu menuX;
	static EditText etFirstName, etLastName, etEmail, etSpecialization, etUser,
			etPass;
	private static Doctor doc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_profile_data);

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
			View rootView = inflater.inflate(
					R.layout.fragment_doctor_profile_data, container, false);
			setHasOptionsMenu(true);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			etFirstName = (EditText) view
					.findViewById(R.id.et_DoctorProfileData_FirstName);
			etLastName = (EditText) view
					.findViewById(R.id.et_DoctorProfileData_LastName);
			etEmail = (EditText) view
					.findViewById(R.id.et_DoctorProfileData_Email);
			etSpecialization = (EditText) view
					.findViewById(R.id.et_DoctorProfileData_Specialization);
			etUser = (EditText) view
					.findViewById(R.id.et_DoctorProfileData_UserName);
			etPass = (EditText) view
					.findViewById(R.id.et_DoctorProfileData_PassWord);
			disableTexts();
			filterList();
		}

		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			menuX = menu;
			inflater.inflate(R.menu.doctor_profile_data, menu);
			menuX.getItem(1).setVisible(false);
			menuX.getItem(2).setVisible(false);

			super.onCreateOptionsMenu(menu, inflater);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_DoctorProfileData_Edit) {
				menuX.getItem(0).setVisible(false);
				menuX.getItem(1).setVisible(true);
				menuX.getItem(2).setVisible(true);
				enableTexts();
			} else if (id == R.id.action_DoctorProfileData_Cancel) {
				resetMenus();
				disableTexts();
				filterList();
			} else if (id == R.id.action_DoctorProfileData_Save) {
				try {
					updateDoctorProfile();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return super.onOptionsItemSelected(item);
		}

		public void updateDoctorProfile() {
			try {
				final DoctorSvcApi svcDoctors = DoctorSvc.getOrShowLogin(getView()
						.getContext());

				CallableTask.invoke(new Callable<Doctor>() {
					@Override
					public Doctor call() throws Exception {
						doc.setFirstname(etFirstName.getText().toString());
						doc.setLastname(etLastName.getText().toString());
						doc.setEmail(etEmail.getText().toString());
						doc.setSpecialization(etSpecialization.getText().toString());
						doc.setUsername(etUser.getText().toString());
						doc.setPassword(etPass.getText().toString());

						return svcDoctors.setDoctor(doc);
					}
				}, new TaskCallback<Doctor>() {

					@Override
					public void success(Doctor result) {
						Toast.makeText(getView().getContext(),
								R.string.successfuly_edited, Toast.LENGTH_LONG).show();
						getActivity().onBackPressed();
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No Doctor",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void resetMenus() {
			menuX.getItem(0).setVisible(true);
			menuX.getItem(1).setVisible(false);
			menuX.getItem(2).setVisible(false);
		}

		public void disableTexts() {
			etFirstName.setEnabled(false);
			etLastName.setEnabled(false);
			etEmail.setEnabled(false);
			etSpecialization.setEnabled(false);
			etUser.setEnabled(false);
			etPass.setEnabled(false);
		}

		public void enableTexts() {
			etFirstName.setEnabled(true);
			etLastName.setEnabled(true);
			etEmail.setEnabled(true);
			etSpecialization.setEnabled(true);
			etUser.setEnabled(true);
			etPass.setEnabled(true);
		}

		public void filterList() {
			try {
				final DoctorSvcApi svcDoctors = DoctorSvc.getOrShowLogin(getView()
						.getContext());

				CallableTask.invoke(new Callable<Doctor>() {
					@Override
					public Doctor call() throws Exception {
						return svcDoctors.getDoctorById(Utils.getIdDoctor());
					}
				}, new TaskCallback<Doctor>() {

					@Override
					public void success(Doctor result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						etFirstName.setText(result.getFirstname());
						etLastName.setText(result.getLastname());
						etEmail.setText(result.getEmail());
						etSpecialization.setText(result.getSpecialization());
						etUser.setText(result.getUsername());
						etPass.setText(result.getPassword());
						doc = result;
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No Doctor",
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
