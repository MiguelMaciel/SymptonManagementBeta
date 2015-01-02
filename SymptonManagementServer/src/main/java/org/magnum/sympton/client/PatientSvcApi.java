package org.magnum.sympton.client;

import java.util.Collection;

import org.magnum.sympton.repository.Patient;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * This interface defines an API for a VideoSvc. The
 * interface is used to provide a contract for client/server
 * interactions. The interface is annotated with Retrofit
 * annotations so that clients can automatically convert the
 * 
 * 
 * @author jules
 *
 */
public interface PatientSvcApi {
	
	public static final String PASSWORD_PARAMETER = "password";
	public static final String USERNAME_PARAMETER = "username";
	public static final String FIRST_NAME_PARAMETER = "firstname";	
	public static final String LAST_NAME_PARAMETER = "lastname";	
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	// The path where we expect the VideoSvc to live
	public static final String PATIENT_SVC_PATH = "/patient";
	//public static final String VIDEO_SVC_PATH = "/video";

	// The path to search videos by title
	public static final String PATIENT_NAME_SEARCH_PATH = PATIENT_SVC_PATH + "/search/findDistinctPatientByLastnameOrFirstname";
	//public static final String VIDEO_NAME_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findDistinctPatientByLastnameOrFirstname";
	
	@GET(PATIENT_SVC_PATH)
	public Collection<Patient> getPatientList();
	
	@GET(PATIENT_SVC_PATH + "/{id}")
	public Patient getPatientById(@Path("id") long id);
	
	@POST(PATIENT_SVC_PATH)
	public Void addPatient(@Body Patient p);
	
	@POST(PATIENT_SVC_PATH)
	public Patient setPatient(@Body Patient p);
	
	@GET(PATIENT_NAME_SEARCH_PATH)
	public Collection<Patient> findDistinctPatientByLastnameOrFirstname(@Query(LAST_NAME_PARAMETER) String lastname, @Query(FIRST_NAME_PARAMETER) String firstname);
	
}
