package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.AlarmTimesSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

	@RepositoryRestResource(path = AlarmTimesSvcApi.ALARMTIMES_SVC_PATH)
	public interface AlarmTimesRepository extends CrudRepository<AlarmTimes, Long> {

		// Find all videos with a matching title (e.g., Video.name)
		public Collection<AlarmTimes> findAlarmtimesByPatient(
				// The @Param annotation tells Spring Data Rest which HTTP request
				// parameter it should use to fill in the "title" variable used to
				// search for Videos
				@Param(AlarmTimesSvcApi.ALARMTIMES_PATIENT_SVC_PATH) long patient);

	}
