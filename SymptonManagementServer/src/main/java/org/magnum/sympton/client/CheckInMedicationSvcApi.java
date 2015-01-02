package org.magnum.sympton.client;

import java.util.Collection;

import org.magnum.sympton.repository.CheckInMedication;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface CheckInMedicationSvcApi {
	public static final String CHECKIN_PARAMETER = "checkin";
	public static final String MEDICATION_PARAMETER = "medication";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String CHECKINMEDICATION_SVC_PATH = "/checkinmedication";

	// The path to search videos by title
	public static final String CHECKINMEDICATION_SEARCH_PATH = CHECKINMEDICATION_SVC_PATH
			+ "/search/findCheckinmedicationByCheckinAndMedication";
	
	public static final String CHECKINMEDICATION_CHECKIN_SEARCH_PATH = CHECKINMEDICATION_SVC_PATH
			+ "/search/findCheckinmedicationByCheckin";
	

	@GET(CHECKINMEDICATION_SVC_PATH)
	public Collection<CheckInMedication> getCheckInMedicationList();

	@POST(CHECKINMEDICATION_SVC_PATH)
	public Void addCheckInMedication(@Body CheckInMedication checkmed);
	
	@POST(CHECKINMEDICATION_SVC_PATH)
	public CheckInMedication setCheckInMedication(@Body CheckInMedication checkmed);

	@GET(CHECKINMEDICATION_SEARCH_PATH)
	public Collection<CheckInMedication> findCheckinmedicationByCheckinAndMedication(
			@Query(CHECKIN_PARAMETER) long checkin,
			@Query(MEDICATION_PARAMETER) long medication);
	
	@GET(CHECKINMEDICATION_CHECKIN_SEARCH_PATH)
	public Collection<CheckInMedication> findCheckinmedicationByCheckin(
			@Query(CHECKIN_PARAMETER) long checkin);
}
