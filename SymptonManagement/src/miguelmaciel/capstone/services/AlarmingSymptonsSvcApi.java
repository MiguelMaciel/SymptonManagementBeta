package miguelmaciel.capstone.services;

import java.util.Collection;

import miguelmaciel.capstone.repositorys.AlarmingSymptons;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface AlarmingSymptonsSvcApi {
	public static final String CHECKIN_PARAMETER = "checkin";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String ALARMINGSYMPTONS_SVC_PATH = "/alarmingsymptons";

	// The path to search videos by title
	public static final String ALARMINGSYMPTONS_CHECKIN_SEARCH_PATH = ALARMINGSYMPTONS_SVC_PATH
			+ "/search/findAlarmingsymptonsByCheckin";

	@GET(ALARMINGSYMPTONS_SVC_PATH)
	public Collection<AlarmingSymptons> getAlarmingSymptonsList();

	@POST(ALARMINGSYMPTONS_SVC_PATH)
	public Void addAlarmingSymptons(@Body AlarmingSymptons as);

	@GET(ALARMINGSYMPTONS_CHECKIN_SEARCH_PATH)
	public Collection<AlarmingSymptons> findAlarmingsymptonsByCheckin(
			@Query(CHECKIN_PARAMETER) long checkin);
}
