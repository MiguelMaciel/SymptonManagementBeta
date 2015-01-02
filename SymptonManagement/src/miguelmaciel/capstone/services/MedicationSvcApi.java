package miguelmaciel.capstone.services;

import java.util.Collection;

import miguelmaciel.capstone.repositorys.Medication;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MedicationSvcApi {
	public static final String PATIENT_PARAMETER = "patient";
	public static final String DOCTOR_PARAMETER = "doctor";
	public static final String MEDICINE_PARAMETER = "medicine";
	public static final String ACTIVE_PARAMETER = "active";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String MEDICATION_SVC_PATH = "/medication";

	// The path to search videos by title
	public static final String MEDICATION_SEARCH_PATH = MEDICATION_SVC_PATH
			+ "/search/findMedicationByDoctorAndPatient";
	
	public static final String MEDICATION_PATIENT_SEARCH_PATH = MEDICATION_SVC_PATH
			+ "/search/findMedicationByPatient";
	
	public static final String MEDICATION_ACTIVE_PATIENT_SEARCH_PATH = MEDICATION_SVC_PATH
			+ "/search/findMedicationByPatientAndActive";

	@GET(MEDICATION_SVC_PATH)
	public Collection<Medication> getMedicationList();
	
	@GET(MEDICATION_SVC_PATH + "/{id}")
	public Medication getMedicationById(@Path("id") long id);

	@POST(MEDICATION_SVC_PATH)
	public Void addMedication(@Body Medication med);
	
	@POST(MEDICATION_SVC_PATH)
	public Medication setMedication(@Body Medication med);

	@GET(MEDICATION_SEARCH_PATH)
	public Collection<Medication> findMedicationByDoctorAndPatient(
			@Query(DOCTOR_PARAMETER) long doctor,
			@Query(PATIENT_PARAMETER) long patient);
	
	@GET(MEDICATION_PATIENT_SEARCH_PATH)
	public Collection<Medication> findMedicationByPatient(
			@Query(PATIENT_PARAMETER) long patient);

	@GET(MEDICATION_ACTIVE_PATIENT_SEARCH_PATH)
	public Collection<Medication> findMedicationByPatientAndActive(
			@Query(PATIENT_PARAMETER) long patient, @Query(ACTIVE_PARAMETER) String active);
}
