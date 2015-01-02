package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class DoctorPatientsSvc {
	private static DoctorPatientsSvcApi doctorPatientSvc_;

	public static synchronized DoctorPatientsSvcApi getOrShowLogin(Context ctx) {
		if (doctorPatientSvc_ != null) {
			return doctorPatientSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized DoctorPatientsSvcApi init(String typeUser, String server, String user,
			String pass) {

		doctorPatientSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + DoctorPatientsSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
			//	.setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(
						new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(DoctorPatientsSvcApi.class);

		return doctorPatientSvc_;
	}
}
