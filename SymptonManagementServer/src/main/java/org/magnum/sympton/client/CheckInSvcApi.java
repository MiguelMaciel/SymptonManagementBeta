package org.magnum.sympton.client;

import java.util.Collection;

import org.magnum.sympton.repository.CheckIn;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CheckInSvcApi {
	public static final String PATIENT_PARAMETER = "patient";
	public static final String STATE_PARAMETER = "state";
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	public static final String CHECKIN_SVC_PATH = "/checkin";

	// The path to search videos by title
	public static final String CHECKIN_PATIENT_SEARCH_PATH = CHECKIN_SVC_PATH + "/search/findCheckinByPatient";
	
	public static final String MISSEDCHECKIN_PATIENT_ASC_SEARCH_PATH = CHECKIN_SVC_PATH + "/search/findCheckinByPatientAndStateOrderByDatecheckinAsc";
	public static final String MISSEDCHECKIN_PATIENT_DESC_SEARCH_PATH = CHECKIN_SVC_PATH + "/search/findCheckinByPatientAndStateOrderByDatecheckinDesc";
	
	@GET(CHECKIN_SVC_PATH)
	public Collection<CheckIn> getCheckInList();
	
	@GET(CHECKIN_SVC_PATH)
	public Collection<CheckIn> getCheckInListOrderByIdDesc();
	
	@GET(CHECKIN_SVC_PATH + "/{id}")
	public CheckIn getCheckInById(@Path("id") long id);

	@POST(CHECKIN_SVC_PATH)
	public CheckIn addCheckIn(@Body CheckIn ci);
	
	@POST(CHECKIN_SVC_PATH)
	public CheckIn setCheckIn(@Body CheckIn ci);
	
	@GET(CHECKIN_PATIENT_SEARCH_PATH)
	public Collection<CheckIn> findCheckinByPatient(@Query(PATIENT_PARAMETER) long patient);
	
	@GET(MISSEDCHECKIN_PATIENT_ASC_SEARCH_PATH)
	public Collection<CheckIn> findCheckinByPatientAndStateOrderByDatecheckinAsc(@Query(PATIENT_PARAMETER) long patient, @Query(STATE_PARAMETER) String state);
	
	@GET(MISSEDCHECKIN_PATIENT_DESC_SEARCH_PATH)
	public Collection<CheckIn> findCheckinByPatientAndStateOrderByDatecheckinDesc(@Query(PATIENT_PARAMETER) long patient, @Query(STATE_PARAMETER) String state);

	
}
