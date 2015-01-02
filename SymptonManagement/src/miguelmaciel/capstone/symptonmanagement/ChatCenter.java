package miguelmaciel.capstone.symptonmanagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;

import miguelmaciel.capstone.adapters.AdaptorChat;
import miguelmaciel.capstone.repositorys.Chat;
import miguelmaciel.capstone.services.ChatSvc;
import miguelmaciel.capstone.services.ChatSvcApi;
import miguelmaciel.capstone.utils.CallableTask;
import miguelmaciel.capstone.utils.TaskCallback;
import miguelmaciel.capstone.utils.Utils;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatCenter extends ActionBarActivity {
	static EditText etMessage;
	static Button btnSend;
	static String idDoctor, idPatient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}

		setContentView(R.layout.activity_chat);

		idDoctor = getIntent().getStringExtra("idDoctor");
		idPatient = getIntent().getStringExtra("idPatient");

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends ListFragment {

		static private AdaptorChat adapterChat;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_chat, container,
					false);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onViewCreated(view, savedInstanceState);
			etMessage = (EditText) view.findViewById(R.id.etChatMessage);
			btnSend = (Button) view.findViewById(R.id.btnChatSend);

			btnSend.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (etMessage.getText().toString().equals("")) {
						Toast.makeText(getView().getContext(),
								R.string.please_insert_a_message_,
								Toast.LENGTH_LONG).show();
					} else {
						sendMessage();
					}
				}
			});

			adapterChat = new AdaptorChat(getView().getContext(),
					R.layout.list_chat, new ArrayList<Chat>());
			getListView().setHeaderDividersEnabled(true);
			setListAdapter(adapterChat);
		}

		@Override
		public void onResume() {
			fillChat();
			super.onResume();
		}

		public void closeKeyBoard() {
			InputMethodManager inputManager = (InputMethodManager) getView()
					.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getView().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

		public void sendMessage() {
			try {
				final ChatSvcApi svcChat = ChatSvc.getOrShowLogin(getView()
						.getContext());

				CallableTask.invoke(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						String date = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(new Date());
						Chat chat = new Chat(Long.parseLong(idDoctor), Long
								.parseLong(idPatient), Utils.getNameUser(),
								date, etMessage.getText().toString());
						return svcChat.addChat(chat);
					}
				}, new TaskCallback<Void>() {

					@Override
					public void success(Void result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						closeKeyBoard();
						fillChat();
						etMessage.setText("");
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(),
								R.string.error_sending_message,
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void fillChat() {
			try {
				final ChatSvcApi svcChat = ChatSvc.getOrShowLogin(getView()
						.getContext());

				CallableTask.invoke(new Callable<Collection<Chat>>() {
					@Override
					public Collection<Chat> call() throws Exception {
						return svcChat.findChatByDoctorAndPatient(
								Long.parseLong(idDoctor),
								Long.parseLong(idPatient));
					}
				}, new TaskCallback<Collection<Chat>>() {

					@Override
					public void success(Collection<Chat> result) {
						// OAuth 2.0 grant was successful and we
						// can talk to the server
						adapterChat.clear();
						for (Chat c : result) {
							adapterChat.add(c);
						}
					}

					@Override
					public void error(Exception e) {
						Toast.makeText(getView().getContext(), "No Chat",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
