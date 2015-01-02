package miguelmaciel.capstone.services;

import java.util.Collection;

import miguelmaciel.capstone.repositorys.Medicine;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MedicineSvcApi {
	public static final String NAME_PARAMETER = "name";
	public static final String COMPOSITION_PARAMETER = "composition";
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	public static final String MEDICINE_SVC_PATH = "/medicine";

	// The path to search videos by title
	public static final String NAME_SEARCH_PATH = MEDICINE_SVC_PATH + "/search/findMedicineByName";
	public static final String COMPOSITION_SEARCH_PATH = MEDICINE_SVC_PATH + "/search/findMedicineByComposition";
	
	
	@GET(MEDICINE_SVC_PATH)
	public Collection<Medicine> getMedicineList();
	
	@POST(MEDICINE_SVC_PATH)
	public Void addMedicine(@Body Medicine m);
	
	@GET(NAME_SEARCH_PATH)
	public Collection<Medicine> findMedicineByName(@Query(NAME_PARAMETER) String name);
	
	@GET(COMPOSITION_SEARCH_PATH)
	public Collection<Medicine> findMedicineByComposition(@Query(COMPOSITION_PARAMETER) String composition);
	
}
