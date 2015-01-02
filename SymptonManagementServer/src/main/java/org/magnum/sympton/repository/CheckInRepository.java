package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.CheckInSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = CheckInSvcApi.CHECKIN_SVC_PATH)
public interface CheckInRepository extends CrudRepository<CheckIn, Long> {

	// Find all videos with a matching title (e.g., Video.name)
	public Collection<CheckIn> findCheckinByPatient(
			// The @Param annotation tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "title" variable used to
			// search for Videos
			@Param(CheckInSvcApi.PATIENT_PARAMETER) long patient);

	public Collection<CheckIn> findCheckinByPatientAndStateOrderByDatecheckinAsc(
			@Param(CheckInSvcApi.PATIENT_PARAMETER) long patient,
			@Param(CheckInSvcApi.STATE_PARAMETER) String state);

	public Collection<CheckIn> findCheckinByPatientAndStateOrderByDatecheckinDesc(
			@Param(CheckInSvcApi.PATIENT_PARAMETER) long patient,
			@Param(CheckInSvcApi.STATE_PARAMETER) String state);
	
}
