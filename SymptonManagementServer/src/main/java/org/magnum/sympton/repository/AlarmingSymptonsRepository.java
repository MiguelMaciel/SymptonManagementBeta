package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.AlarmingSymptonsSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = AlarmingSymptonsSvcApi.ALARMINGSYMPTONS_SVC_PATH)
public interface AlarmingSymptonsRepository extends
		CrudRepository<AlarmingSymptons, Long> {

	// Find all videos with a matching title (e.g., Video.name)
	public Collection<AlarmingSymptons> findAlarmingsymptonsByCheckin(
	// The @Param annotation tells Spring Data Rest which HTTP request
	// parameter it should use to fill in the "title" variable used to
	// search for Videos
			@Param(AlarmingSymptonsSvcApi.CHECKIN_PARAMETER) long checkin);

}
