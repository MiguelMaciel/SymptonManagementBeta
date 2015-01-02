package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.ChatSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

	@RepositoryRestResource(path = ChatSvcApi.CHAT_SVC_PATH)
	public interface ChatRepository extends CrudRepository<Chat, Long> {

		// Find all videos with a matching title (e.g., Video.name)
		public Collection<Chat> findChatByDoctorAndPatient(
				// The @Param annotation tells Spring Data Rest which HTTP request
				// parameter it should use to fill in the "title" variable used to
				// search for Videos
				@Param(ChatSvcApi.DOCTOR_PARAMETER) long doctor, 
				@Param(ChatSvcApi.PATIENT_PARAMETER) long patient);
		
	}