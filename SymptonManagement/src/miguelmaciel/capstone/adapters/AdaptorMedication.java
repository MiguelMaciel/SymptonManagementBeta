package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.Medication;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorMedication extends ArrayAdapter<Medication>{
	private List<Medication> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorMedication(Context context, int layoutResourceId,
			List<Medication> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MedicationHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new MedicationHolder();
		holder.medications = items.get(position);
		holder.name = (TextView) row.findViewById(R.id.listPatientMedication_Name);
		holder.startdate = (TextView) row.findViewById(R.id.listPatientMedication_StartDate);
		holder.enddate = (TextView) row.findViewById(R.id.listPatientMedication_EndDate);
		holder.dose = (TextView) row.findViewById(R.id.listPatientMedication_Dose);
				
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(MedicationHolder holder) {
		holder.name.setText(holder.medications.getMedName());
		holder.startdate.setText(holder.medications.getStartdate());
		holder.enddate.setText(holder.medications.getEnddate());
		holder.dose.setText(holder.medications.getDose());
	}

	public static class MedicationHolder {
		Medication medications;
		TextView name;
		TextView startdate;
		TextView enddate;
		TextView dose;
	}
}
