package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.AlertMedication;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorAlertMedication extends ArrayAdapter<AlertMedication>{
	private List<AlertMedication> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorAlertMedication(Context context, int layoutResourceId,
			List<AlertMedication> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AlertMedicationHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new AlertMedicationHolder();
		holder.alerts = items.get(position);
		holder.message = (TextView) row.findViewById(R.id.listAlertMedication_Message);
		holder.date = (TextView) row.findViewById(R.id.listAlertMedication_Date);
				
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(AlertMedicationHolder holder) {
		holder.message.setText(holder.alerts.getMessage());
		holder.date.setText(holder.alerts.getDate());
	}

	public static class AlertMedicationHolder {
		AlertMedication alerts;
		TextView message;
		TextView date;
	}
}
