package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.Doctor;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorDoctor extends ArrayAdapter<Doctor>{
	private List<Doctor> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorDoctor(Context context, int layoutResourceId,
			List<Doctor> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		DoctorHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new DoctorHolder();
		holder.doctors = items.get(position);
		holder.firstName = (TextView) row.findViewById(R.id.listDoctorsOfPatient_FirstName);
		holder.lastName = (TextView) row.findViewById(R.id.listDoctorsOfPatient_LastName);
		holder.specialization = (TextView) row.findViewById(R.id.listDoctorsOfPatient_Specialization);
				
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(DoctorHolder holder) {
		holder.firstName.setText(holder.doctors.getFirstname());
		holder.lastName.setText(holder.doctors.getLastname());
		holder.specialization.setText(holder.doctors.getSpecialization());
	}

	public static class DoctorHolder {
		Doctor doctors;
		TextView firstName;
		TextView lastName;
		TextView specialization;
	}
}
