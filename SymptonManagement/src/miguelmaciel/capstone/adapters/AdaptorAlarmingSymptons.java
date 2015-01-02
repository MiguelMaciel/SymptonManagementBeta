package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.AlarmingSymptons;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorAlarmingSymptons extends ArrayAdapter<AlarmingSymptons>{
	private List<AlarmingSymptons> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorAlarmingSymptons(Context context, int layoutResourceId,
			List<AlarmingSymptons> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AlarmingSymptonsHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new AlarmingSymptonsHolder();
		holder.alarms = items.get(position);
		holder.sympton = (TextView) row.findViewById(R.id.listAlarmingSymptons_Sympton);
		holder.situation = (TextView) row.findViewById(R.id.listAlarmingSymptons_Situation);
		holder.date = (TextView) row.findViewById(R.id.listAlarmingSymptons_Date);
			
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(AlarmingSymptonsHolder holder) {
		holder.sympton.setText(holder.alarms.getSympton());
		holder.situation.setText(holder.alarms.getSituation());
		holder.date.setText(holder.alarms.getDate());
	}

	public static class AlarmingSymptonsHolder {
		AlarmingSymptons alarms;
		TextView sympton;
		TextView situation;
		TextView date;
	}
}
