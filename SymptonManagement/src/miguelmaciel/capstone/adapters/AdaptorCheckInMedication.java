package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.CheckInMedication;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class AdaptorCheckInMedication extends ArrayAdapter<CheckInMedication> {
	private List<CheckInMedication> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorCheckInMedication(Context context, int layoutResourceId,
			List<CheckInMedication> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CheckInMedicationHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new CheckInMedicationHolder();
		holder.checkMeds = items.get(position);
		holder.medName = (TextView) row.findViewById(R.id.listCheckInMedication_MedicationName);
		holder.date = (TextView) row.findViewById(R.id.listCheckInMedication_Date);
		holder.time = (TextView) row.findViewById(R.id.listCheckInMedication_Time);
		holder.rbNo = (RadioButton) row.findViewById(R.id.listCheckInMedication_No);
		holder.rbYes = (RadioButton) row.findViewById(R.id.listCheckInMedication_Yes);
		
		//Set Position List
		holder.checkMeds.setPosition(position);
		
		holder.rbNo.setTag(holder.checkMeds);
		holder.rbYes.setTag(holder.checkMeds);
		
		row.setTag(holder);

		setupItem(holder);
		return row;
	}

	private void setupItem(CheckInMedicationHolder holder) {
		holder.medName.setText(holder.checkMeds.getMedicineName());
		holder.date.setText(holder.checkMeds.getDate());
		holder.time.setText(holder.checkMeds.getTime());
		if(holder.checkMeds.getTakestate().equals("0")){
			holder.rbNo.setChecked(true);
		}else if(holder.checkMeds.getTakestate().equals("1")){
			holder.rbYes.setChecked(true);
		}
	}

	public static class CheckInMedicationHolder {
		CheckInMedication checkMeds;
		TextView medName;
		RadioButton rbYes;
		RadioButton rbNo;
		TextView date;
		TextView time;
	}

}
