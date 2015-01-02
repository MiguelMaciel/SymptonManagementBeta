package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class CheckInSvc {
	private static CheckInSvcApi checkSvc_;

	public static synchronized CheckInSvcApi getOrShowLogin(Context ctx) {
		if (checkSvc_ != null) {
			return checkSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized CheckInSvcApi init(String typeUser,
			String server, String user, String pass) {

		checkSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + CheckInSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
				// .setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(CheckInSvcApi.class);

		return checkSvc_;
	}

}
