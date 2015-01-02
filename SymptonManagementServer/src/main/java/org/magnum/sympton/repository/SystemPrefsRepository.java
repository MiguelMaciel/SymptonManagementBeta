package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.SystemPrefsSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

	@RepositoryRestResource(path = SystemPrefsSvcApi.SYSTEMPREFS_SVC_PATH)
	public interface SystemPrefsRepository extends CrudRepository<SystemPrefs, Long> {

		// Find all videos with a matching title (e.g., Video.name)
		public Collection<SystemPrefs> findSystemprefsByPatient(
				// The @Param annotation tells Spring Data Rest which HTTP request
				// parameter it should use to fill in the "title" variable used to
				// search for Videos
				@Param(SystemPrefsSvcApi.SYSTEMPREFS_PATIENT_SVC_PATH) long patient);

	}