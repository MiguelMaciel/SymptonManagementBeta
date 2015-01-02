package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class DoctorSvc {
	//public static final String CLIENT_ID = "doctor";

	private static DoctorSvcApi doctorSvc_;

	public static synchronized DoctorSvcApi getOrShowLogin(Context ctx) {
		if (doctorSvc_ != null) {
			return doctorSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized DoctorSvcApi init(String typeUser, String server, String user,
			String pass) {

		doctorSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + DoctorSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
			//	.setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(
						new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(DoctorSvcApi.class);

		return doctorSvc_;
	}

}