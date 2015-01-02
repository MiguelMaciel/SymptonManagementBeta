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

public class AdaptorCheckIn  extends ArrayAdapter<CheckIn> {
	private List<CheckIn> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorCheckIn(Context context, int layoutResourceId,
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
		holder.checkIn = items.get(position);
		holder.checkDate = (TextView) row.findViewById(R.id.listPatientCheckIn_Date);
		holder.checkThroat = (TextView) row.findViewById(R.id.listPatientCheckIn_ThroatLevel);
		holder.checkPain = (TextView) row.findViewById(R.id.listPatientCheckIn_PainLevel);
		holder.checkToke = (TextView) row.findViewById(R.id.listPatientCheckIn_TokeMed);
		
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(CheckInHolder holder) {
		holder.checkDate.setText(holder.checkIn.getDatecheckin());
		
		String throat = holder.checkIn.getThroatlevel();
		if(throat.equals("0")){
			holder.checkThroat.setText(R.string.controlled);
		}else if(throat.equals("1")){
			holder.checkThroat.setText(R.string.moderate);
		}else if(throat.equals("2")){
			holder.checkThroat.setText(R.string.severe);
		}
		
		String pain = holder.checkIn.getPainlevel();
		if(pain.equals("0")){
			holder.checkPain.setText(R.string.no);
		}else if(pain.equals("1")){
			holder.checkPain.setText(R.string.some);
		}else if(pain.equals("2")){
			holder.checkPain.setText(R.string.yes);
		}
		
		String toke = holder.checkIn.getTakemed();
		if(toke.equals("0")){
			holder.checkToke.setText(R.string.no);
		}else if(toke.equals("1")){
			holder.checkToke.setText(R.string.yes);
		}
	}

	public static class CheckInHolder {
		CheckIn checkIn;
		TextView checkDate;
		TextView checkThroat;
		TextView checkPain;
		TextView checkToke;
	}
	
}

