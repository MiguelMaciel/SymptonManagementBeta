package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.Medicine;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorMedicine extends ArrayAdapter<Medicine>{
	private List<Medicine> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorMedicine(Context context, int layoutResourceId,
			List<Medicine> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MedicineHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new MedicineHolder();
		holder.medicines = items.get(position);
		holder.name = (TextView) row.findViewById(R.id.listMedicine_Name);
		holder.composition = (TextView) row.findViewById(R.id.listMedicine_Composition);
				
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(MedicineHolder holder) {
		holder.name.setText(holder.medicines.getName());
		holder.composition.setText(holder.medicines.getComposition());
	}

	public static class MedicineHolder {
		Medicine medicines;
		TextView name;
		TextView composition;
	}
}
