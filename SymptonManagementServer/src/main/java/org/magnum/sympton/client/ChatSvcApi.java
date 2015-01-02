package org.magnum.sympton.client;

import java.util.Collection;

import org.magnum.sympton.repository.Chat;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ChatSvcApi {
	public static final String PATIENT_PARAMETER = "patient";
	public static final String DOCTOR_PARAMETER = "doctor";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String CHAT_SVC_PATH = "/chat";

	// The path to search videos by title
	public static final String CHAT_SEARCH_PATH = CHAT_SVC_PATH
			+ "/search/findChatByDoctorAndPatient";

	@GET(CHAT_SVC_PATH)
	public Collection<Chat> getChatList();

	@POST(CHAT_SVC_PATH)
	public Void addChat(@Body Chat c);

	@GET(CHAT_SEARCH_PATH)
	public Collection<Chat> findChatByDoctorAndPatient(
			@Query(DOCTOR_PARAMETER) long doctor,
			@Query(PATIENT_PARAMETER) long patient);

}
