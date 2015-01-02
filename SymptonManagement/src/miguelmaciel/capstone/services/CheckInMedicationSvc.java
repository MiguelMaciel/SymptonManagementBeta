package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class CheckInMedicationSvc {
	private static CheckInMedicationSvcApi checkMedSvc_;

	public static synchronized CheckInMedicationSvcApi getOrShowLogin(
			Context ctx) {
		if (checkMedSvc_ != null) {
			return checkMedSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized CheckInMedicationSvcApi init(String typeUser,
			String server, String user, String pass) {

		checkMedSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + CheckInMedicationSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
				// .setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(CheckInMedicationSvcApi.class);

		return checkMedSvc_;
	}

}
