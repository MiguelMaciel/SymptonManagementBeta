package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class MedicationSvc {
	private static MedicationSvcApi medicationSvc_;

	public static synchronized MedicationSvcApi getOrShowLogin(Context ctx) {
		if (medicationSvc_ != null) {
			return medicationSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized MedicationSvcApi init(String typeUser, String server, String user,
			String pass) {

		medicationSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + MedicationSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
			//	.setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(
						new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(MedicationSvcApi.class);

		return medicationSvc_;
	}
	
}
