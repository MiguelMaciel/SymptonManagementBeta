package miguelmaciel.capstone.adapters;

import java.util.List;

import miguelmaciel.capstone.repositorys.Chat;
import miguelmaciel.capstone.symptonmanagement.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptorChat extends ArrayAdapter<Chat> {
	private List<Chat> items;
	private int layoutResourceId;
	private Context context;
	
	public AdaptorChat(Context context, int layoutResourceId,
			List<Chat> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ChatHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new ChatHolder();
		holder.chats = items.get(position);
		holder.sendFrom = (TextView) row.findViewById(R.id.listChat_From);
		holder.date = (TextView) row.findViewById(R.id.listChat_Date);
		holder.message = (TextView) row.findViewById(R.id.listChat_Message);
		
		row.setTag(holder);
		setupItem(holder);
		return row;
	}

	private void setupItem(ChatHolder holder) {
		holder.sendFrom.setText(holder.chats.getSendfrom());
		holder.date.setText(holder.chats.getDate());
		holder.message.setText(holder.chats.getMessage());
	}

	public static class ChatHolder {
		Chat chats;
		TextView sendFrom;
		TextView date;
		TextView message;
	}
	
	
}
