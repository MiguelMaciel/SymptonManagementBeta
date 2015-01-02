package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.CheckIn;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorMissedCheckIn extends ArrayAdapter<CheckIn> {
	private List<CheckIn> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorMissedCheckIn(Context context, int layoutResourceId,
			List<CheckIn> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CheckInHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new CheckInHolder();
		holder.checks = items.get(position);
		holder.date = (TextView) row.findViewById(R.id.listPatientMissedCheckIn_Date);
				
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(CheckInHolder holder) {
		holder.date.setText(holder.checks.getDatecheckin());
	}

	public static class CheckInHolder {
		CheckIn checks;
		TextView date;
	}
	
	
}
