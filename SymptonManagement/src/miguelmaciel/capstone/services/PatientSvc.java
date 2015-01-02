/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class PatientSvc {

	//public static final String CLIENT_ID = "doctor";

	private static PatientSvcApi patientSvc_;

	public static synchronized PatientSvcApi getOrShowLogin(Context ctx) {
		if (patientSvc_ != null) {
			return patientSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized PatientSvcApi init(String typeUser, String server, String user,
			String pass) {

		patientSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + PatientSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
			//	.setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(
						new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(PatientSvcApi.class);

		return patientSvc_;
	}
}
