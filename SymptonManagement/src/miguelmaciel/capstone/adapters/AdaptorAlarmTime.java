package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdaptorAlarmTime extends ArrayAdapter<AlarmTimes> {
	private List<AlarmTimes> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorAlarmTime(Context context, int layoutResourceId,
			List<AlarmTimes> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AlarmTimesHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new AlarmTimesHolder();
		holder.alarmTimes = items.get(position);
		holder.removeButton = (ImageButton) row.findViewById(R.id.btnButtonRemoveAlarmTime);
		holder.removeButton.setTag(holder.alarmTimes);

		holder.time = (TextView) row.findViewById(R.id.lblAlarmTime);
		
		row.setTag(holder);

		setupItem(holder);
		return row;
	}

	private void setupItem(AlarmTimesHolder holder) {
		holder.time.setText(holder.alarmTimes.getAlarmTime());
	}

	public static class AlarmTimesHolder {
		AlarmTimes alarmTimes;
		TextView time;
		ImageButton removeButton;
	}
	
	
}
