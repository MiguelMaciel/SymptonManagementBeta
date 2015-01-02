package miguelmaciel.capstone.services;

import java.util.Collection;

import miguelmaciel.capstone.repositorys.Doctor;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface DoctorSvcApi {

	public static final String PASSWORD_PARAMETER = "password";
	public static final String USERNAME_PARAMETER = "username";
	public static final String FIRST_NAME_PARAMETER = "firstname";	
	public static final String LAST_NAME_PARAMETER = "lastname";
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	// The path where we expect the VideoSvc to live
	public static final String DOCTOR_SVC_PATH = "/doctor";

	// The path to search videos by title
	public static final String DOCTOR_NAME_SEARCH_PATH = DOCTOR_SVC_PATH + "/search/findDistinctDoctorByLastnameOrFirstname";
	
	@GET(DOCTOR_SVC_PATH)
	public Collection<Doctor> getDoctorList();	
	
	@GET(DOCTOR_SVC_PATH + "/{id}")
	public Doctor getDoctorById(@Path("id") long id);
	
	/*@PUT(DOCTOR_SVC_PATH + "/{id}")
	public Doctor updateDoctorById(@Path("id") long id);*/
	
	@POST(DOCTOR_SVC_PATH)
	public Void addDoctor(@Body Doctor d);
	
	@POST(DOCTOR_SVC_PATH)
	public Doctor setDoctor(@Body Doctor d);
	
	@GET(DOCTOR_NAME_SEARCH_PATH)
	public Collection<Doctor> findDistinctDoctorByLastnameOrFirstname(@Query(LAST_NAME_PARAMETER) String lastname, @Query(FIRST_NAME_PARAMETER) String firstname);
	
}
