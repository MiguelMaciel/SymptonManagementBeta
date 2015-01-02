package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.PatientSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * An interface for a repository that can store Video
 * objects and allow them to be searched by title.
 * 
 * @author jules
 *
 */
// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the VideoRepository through a controller and map it to the 
// "/video" path. This automatically enables you to do the following:
//
// 1. List all videos by sending a GET request to /video 
// 2. Add a video by sending a POST request to /video with the JSON for a video
// 3. Get a specific video by sending a GET request to /video/{videoId}
//    (e.g., /video/1 would return the JSON for the video with id=1)
// 4. Send search requests to our findByXYZ methods to /video/search/findByXYZ
//    (e.g., /video/search/findByName?title=Foo)
//
@RepositoryRestResource(path = PatientSvcApi.PATIENT_SVC_PATH)
public interface PatientRepository extends CrudRepository<Patient, Long>{

	// Find all videos with a matching title (e.g., Video.name)
	public Collection<Patient> findDistinctPatientByLastnameOrFirstname(
			// The @Param annotation tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "title" variable used to
			// search for Videos
			@Param(PatientSvcApi.LAST_NAME_PARAMETER) String lastname,
			@Param(PatientSvcApi.FIRST_NAME_PARAMETER) String firstname);
	
	/*
	 * See: http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html 
	 * for more examples of writing query methods
	 */
	
}
