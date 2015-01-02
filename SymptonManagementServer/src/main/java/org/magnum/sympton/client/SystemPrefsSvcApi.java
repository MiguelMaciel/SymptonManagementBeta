package org.magnum.sympton.client;

import java.util.Collection;

import org.magnum.sympton.repository.SystemPrefs;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface SystemPrefsSvcApi {
	public static final String PATIENT_PARAMETER = "patient";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String SYSTEMPREFS_SVC_PATH = "/systemprefs";

	// The path to search videos by title
	public static final String SYSTEMPREFS_PATIENT_SVC_PATH = SYSTEMPREFS_SVC_PATH
			+ "/search/findSystemprefsByPatient";

	@GET(SYSTEMPREFS_SVC_PATH)
	public Collection<SystemPrefs> getSystemPrefsList();

	@POST(SYSTEMPREFS_SVC_PATH)
	public Void addSystemPrefs(@Body SystemPrefs sp);

	@GET(SYSTEMPREFS_PATIENT_SVC_PATH)
	public Collection<SystemPrefs> findSystemprefsByPatient(
			@Query(PATIENT_PARAMETER) long patient);
}
