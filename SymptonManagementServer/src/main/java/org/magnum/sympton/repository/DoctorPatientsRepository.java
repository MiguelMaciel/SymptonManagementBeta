package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.DoctorPatientsSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = DoctorPatientsSvcApi.DOCTORPATIENTS_SVC_PATH)
public interface DoctorPatientsRepository extends CrudRepository<DoctorPatients, Long> {

	// Find all videos with a matching title (e.g., Video.name)
	public Collection<DoctorPatients> findDistinctDoctorpatientsByPatient(
			// The @Param annotation tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "title" variable used to
			// search for Videos
			@Param(DoctorPatientsSvcApi.PATIENT_PARAMETER) long patient);
	
	public Collection<DoctorPatients> findDistinctDoctorpatientsByDoctor(
			// The @Param annotation tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "title" variable used to
			// search for Videos
			@Param(DoctorPatientsSvcApi.DOCTOR_PARAMETER) long doctor);
	
}