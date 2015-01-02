package org.magnum.sympton.client;

import java.util.Collection;

import org.magnum.sympton.repository.AlertMedication;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface AlertMedicationSvcApi {
	public static final String PATIENT_PARAMETER = "patient";
	public static final String DOCTOR_PARAMETER = "doctor";
	public static final String MEDICATION_PARAMETER = "medication";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String ALERTMEDICATION_SVC_PATH = "/alertmedication";

	// The path to search videos by title
	public static final String ALERTMEDICATION_SEARCH_PATH = ALERTMEDICATION_SVC_PATH
			+ "/search/findAlertmedicationByPatient";
	
	public static final String ALERTMEDICATION_ASC_SEARCH_PATH = ALERTMEDICATION_SVC_PATH
			+ "/search/findAlertmedicationByPatientOrderByDateAsc";
	
	public static final String ALERTMEDICATION_DESC_SEARCH_PATH = ALERTMEDICATION_SVC_PATH
			+ "/search/findAlertmedicationByPatientOrderByDateDesc";

	@GET(ALERTMEDICATION_SVC_PATH)
	public Collection<AlertMedication> getAlertMedicationList();

	@POST(ALERTMEDICATION_SVC_PATH)
	public Void addAlertMedication(@Body AlertMedication almed);

	@GET(ALERTMEDICATION_ASC_SEARCH_PATH)
	public Collection<AlertMedication> findAlertmedicationByPatientOrderByDateAsc(
			@Query(PATIENT_PARAMETER) long patient);
	
	@GET(ALERTMEDICATION_DESC_SEARCH_PATH)
	public Collection<AlertMedication> findAlertmedicationByPatientOrderByDateDesc(
			@Query(PATIENT_PARAMETER) long patient);
	
	@GET(ALERTMEDICATION_SEARCH_PATH)
	public Collection<AlertMedication> findAlertmedicationByPatient(
			@Query(PATIENT_PARAMETER) long patient);
}
