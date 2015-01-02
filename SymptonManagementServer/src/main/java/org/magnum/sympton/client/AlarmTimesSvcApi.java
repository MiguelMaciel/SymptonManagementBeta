package org.magnum.sympton.client;

import java.util.Collection;

import org.magnum.sympton.repository.AlarmTimes;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface AlarmTimesSvcApi {
	public static final String PATIENT_PARAMETER = "patient";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String ALARMTIMES_SVC_PATH = "/alarmtimes";

	// The path to search videos by title
	public static final String ALARMTIMES_PATIENT_SVC_PATH = ALARMTIMES_SVC_PATH
			+ "/search/findAlarmtimesByPatient";

	@GET(ALARMTIMES_SVC_PATH)
	public Collection<AlarmTimes> getAlarmTimesList();

	@POST(ALARMTIMES_SVC_PATH)
	public Void addAlarmTimes(@Body AlarmTimes at);

	@GET(ALARMTIMES_PATIENT_SVC_PATH)
	public Collection<AlarmTimes> findAlarmtimesByPatient(
			@Query(PATIENT_PARAMETER) long patient);

}
