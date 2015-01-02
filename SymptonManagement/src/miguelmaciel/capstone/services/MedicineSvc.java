package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class MedicineSvc {
	private static MedicineSvcApi medicineSvc_;

	public static synchronized MedicineSvcApi getOrShowLogin(Context ctx) {
		if (medicineSvc_ != null) {
			return medicineSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized MedicineSvcApi init(String typeUser,
			String server, String user, String pass) {

		medicineSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + MedicineSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
				// .setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(MedicineSvcApi.class);

		return medicineSvc_;
	}
}
