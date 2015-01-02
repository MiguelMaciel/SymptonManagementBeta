package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class AlarmingSymptonsSvc {
	private static AlarmingSymptonsSvcApi alarmingSvc_;

	public static synchronized AlarmingSymptonsSvcApi getOrShowLogin(Context ctx) {
		if (alarmingSvc_ != null) {
			return alarmingSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized AlarmingSymptonsSvcApi init(String typeUser,
			String server, String user, String pass) {

		alarmingSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + AlarmingSymptonsSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
				// .setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(AlarmingSymptonsSvcApi.class);

		return alarmingSvc_;
	}
}
