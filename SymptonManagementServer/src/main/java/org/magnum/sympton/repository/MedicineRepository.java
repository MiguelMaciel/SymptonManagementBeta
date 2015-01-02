package org.magnum.sympton.repository;

import java.util.Collection;

import org.magnum.sympton.client.MedicineSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = MedicineSvcApi.MEDICINE_SVC_PATH)
public interface MedicineRepository extends CrudRepository<Medicine, Long> {
	
	public Collection<Medicine> findMedicineByName(
			@Param(MedicineSvcApi.NAME_PARAMETER) String name);
	
	public Collection<Medicine> findMedicineByComposition(
			@Param(MedicineSvcApi.COMPOSITION_PARAMETER) String composition);

}
