package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.CheckInMedicationSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

	@RepositoryRestResource(path = CheckInMedicationSvcApi.CHECKINMEDICATION_SVC_PATH)
	public interface CheckInMedicationRepository extends CrudRepository<CheckInMedication, Long> {

		// Find all videos with a matching title (e.g., Video.name)
		public Collection<CheckInMedication> findCheckinmedicationByCheckinAndMedication(
				@Param(CheckInMedicationSvcApi.CHECKIN_PARAMETER) long checkin,
				@Param(CheckInMedicationSvcApi.MEDICATION_PARAMETER) long medication);

		public Collection<CheckInMedication> findCheckinmedicationByCheckin(
				@Param(CheckInMedicationSvcApi.CHECKIN_PARAMETER) long checkin);
		
	}