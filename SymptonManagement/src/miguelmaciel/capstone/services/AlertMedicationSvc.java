package miguelmaciel.capstone.services;

import miguelmaciel.capstone.oauth.SecuredRestBuilder;
import miguelmaciel.capstone.symptonmanagement.Authentication;
import miguelmaciel.capstone.unsafe.EasyHttpClient;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class AlertMedicationSvc {
	private static AlertMedicationSvcApi alertMedicationSvc_;

	public static synchronized AlertMedicationSvcApi getOrShowLogin(Context ctx) {
		if (alertMedicationSvc_ != null) {
			return alertMedicationSvc_;
		} else {
			Intent i = new Intent(ctx, Authentication.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized AlertMedicationSvcApi init(String typeUser, String server, String user,
			String pass) {

		alertMedicationSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + AlertMedicationSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
			//	.setClientId(CLIENT_ID)
				.setClientId(typeUser)
				.setClient(
						new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(AlertMedicationSvcApi.class);

		return alertMedicationSvc_;
	}
}
