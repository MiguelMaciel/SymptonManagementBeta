package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.Patient;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorPatient extends ArrayAdapter<Patient>{
	private List<Patient> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorPatient(Context context, int layoutResourceId,
			List<Patient> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		PatientHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new PatientHolder();
		holder.patients = items.get(position);
		holder.firstName = (TextView) row.findViewById(R.id.listPatients_FirstName);
		holder.lastName = (TextView) row.findViewById(R.id.listPatients_LastName);
				
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(PatientHolder holder) {
		holder.firstName.setText(holder.patients.getFirstname());
		holder.lastName.setText(holder.patients.getLastname());
	}

	public static class PatientHolder {
		Patient patients;
		TextView firstName;
		TextView lastName;
	}
}
