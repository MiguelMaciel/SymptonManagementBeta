package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class ChatSvc {

	private static ChatSvcApi chatSvc_;

	public static synchronized ChatSvcApi getOrShowLogin(Context ctx) {
		if (chatSvc_ != null) {
			return chatSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized ChatSvcApi init(String typeUser, String server, String user,
			String pass) {

		chatSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + ChatSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
			//	.setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(
						new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(ChatSvcApi.class);

		return chatSvc_;
	}
	
}
