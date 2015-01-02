package miguelmaciel.capstone.services;

import java.util.Collection;

import miguelmaciel.capstone.repositorys.DoctorPatients;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface DoctorPatientsSvcApi {
	public static final String PATIENT_PARAMETER = "patient";
	public static final String DOCTOR_PARAMETER = "doctor";
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	public static final String DOCTORPATIENTS_SVC_PATH = "/doctorpatients";

	// The path to search videos by title
	public static final String DOCTORPATIENTS_DOCTOR_SEARCH_PATH = DOCTORPATIENTS_SVC_PATH + "/search/findDistinctDoctorpatientsByPatient";
	public static final String DOCTORPATIENTS_PATIENT_SEARCH_PATH = DOCTORPATIENTS_SVC_PATH + "/search/findDistinctDoctorpatientsByDoctor";
	
	
	@GET(DOCTORPATIENTS_SVC_PATH)
	public Collection<DoctorPatients> getDoctorpatientsList();
	
	@POST(DOCTORPATIENTS_SVC_PATH)
	public Void addDoctorpatients(@Body DoctorPatients dp);
	
	@GET(DOCTORPATIENTS_DOCTOR_SEARCH_PATH)
	public Collection<DoctorPatients> findDistinctDoctorpatientsByPatient(@Query(PATIENT_PARAMETER) long patient);
	
	@GET(DOCTORPATIENTS_PATIENT_SEARCH_PATH)
	public Collection<DoctorPatients> findDistinctDoctorpatientsByDoctor(@Query(DOCTOR_PARAMETER) long doctor);
	
}
