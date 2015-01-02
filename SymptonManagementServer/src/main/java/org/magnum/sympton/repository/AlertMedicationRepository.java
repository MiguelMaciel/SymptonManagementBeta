package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.AlertMedicationSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = AlertMedicationSvcApi.ALERTMEDICATION_SVC_PATH)
public interface AlertMedicationRepository extends
		CrudRepository<AlertMedication, Long> {
	
	public Collection<AlertMedication> findAlertmedicationByPatientOrderByDateAsc(
			@Param(AlertMedicationSvcApi.PATIENT_PARAMETER) long patient);
	
	public Collection<AlertMedication> findAlertmedicationByPatientOrderByDateDesc(
			@Param(AlertMedicationSvcApi.PATIENT_PARAMETER) long patient);
	
	public Collection<AlertMedication> findAlertmedicationByPatient(
			@Param(AlertMedicationSvcApi.PATIENT_PARAMETER) long patient);
}
