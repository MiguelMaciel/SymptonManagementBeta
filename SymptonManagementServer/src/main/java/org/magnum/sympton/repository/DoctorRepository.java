package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.DoctorSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = DoctorSvcApi.DOCTOR_SVC_PATH)
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

	// Find all videos with a matching title (e.g., Video.name)
	public Collection<Doctor> findDistinctDoctorByLastnameOrFirstname(
			// The @Param annotation tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "title" variable used to
			// search for Videos
			@Param(DoctorSvcApi.LAST_NAME_PARAMETER) String lastname,
			@Param(DoctorSvcApi.FIRST_NAME_PARAMETER) String firstname);
	
	//@Query(value = "SELECT specialization FROM doctor WHERE iddoctor = 1")
	//public String findSpecialization();
}
