package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.MedicationSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

	@RepositoryRestResource(path = MedicationSvcApi.MEDICATION_SVC_PATH)
	public interface MedicationRepository extends CrudRepository<Medication, Long> {

		// Find all videos with a matching title (e.g., Video.name)
		public Collection<Medication> findMedicationByDoctorAndPatient(
				@Param(MedicationSvcApi.DOCTOR_PARAMETER) long doctor,
				@Param(MedicationSvcApi.PATIENT_PARAMETER) long patient);
		
		public Collection<Medication> findMedicationByPatient(
				@Param(MedicationSvcApi.PATIENT_PARAMETER) long patient);
		
		public Collection<Medication> findMedicationByPatientAndActive(
				@Param(MedicationSvcApi.PATIENT_PARAMETER) long patient,
				@Param(MedicationSvcApi.ACTIVE_PARAMETER) String active);
}
