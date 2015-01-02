package org.magnum.mobilecloud.integration.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.magnum.mobilecloud.video.TestData;
import org.magnum.sympton.client.DoctorPatientsSvcApi;
import org.magnum.sympton.client.DoctorSvcApi;
import org.magnum.sympton.client.MedicineSvcApi;
import org.magnum.sympton.client.PatientSvcApi;
import org.magnum.sympton.client.SecuredRestBuilder;
import org.magnum.sympton.repository.Doctor;
import org.magnum.sympton.repository.DoctorPatients;
import org.magnum.sympton.repository.Medicine;
import org.magnum.sympton.repository.Patient;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

public class setupDefaultData {
	private final String USERNAME = "doc1";
	private final String PASSWORD = "pass";
	private final String CLIENT_ID = "doctor";
	private final String TEST_URL = "https://localhost:8443";

	private DoctorSvcApi doctorService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + DoctorSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(DoctorSvcApi.class);

	private PatientSvcApi patientService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + PatientSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(PatientSvcApi.class);

	private DoctorPatientsSvcApi doctorPatientService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + DoctorPatientsSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(DoctorPatientsSvcApi.class);

	private MedicineSvcApi medicineService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + MedicineSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(MedicineSvcApi.class);

	private Doctor doc1 = TestData.doctor1();
	private Doctor doc2 = TestData.doctor2();
	private Doctor doc3 = TestData.doctor3();
	private Patient pat1 = TestData.patient1();
	private Patient pat2 = TestData.patient2();
	private Patient pat3 = TestData.patient3();
	private Patient pat4 = TestData.patient4();
	private DoctorPatients doc1Pat1 = TestData.doc1Pat1();
	private DoctorPatients doc1Pat2 = TestData.doc1Pat2();
	private DoctorPatients doc1Pat3 = TestData.doc1Pat3();
	private DoctorPatients doc1Pat4 = TestData.doc1Pat4();
	private DoctorPatients doc2Pat1 = TestData.doc2Pat1();
	private DoctorPatients doc2Pat2 = TestData.doc2Pat2();
	private DoctorPatients doc2Pat3 = TestData.doc2Pat3();
	private DoctorPatients doc3Pat1 = TestData.doc3Pat1();
	private DoctorPatients doc3Pat2 = TestData.doc3Pat2();
	private Medicine med1 = TestData.addMed1();
	private Medicine med2 = TestData.addMed2();
	private Medicine med3 = TestData.addMed3();
	private Medicine med4 = TestData.addMed4();
	private Medicine med5 = TestData.addMed5();
	private Medicine med6 = TestData.addMed6();
	private Medicine med7 = TestData.addMed7();

	@Test
	public void createPatient1() throws Exception {
		// Add the video
		patientService.addPatient(pat1);

		// We should get back the video that we added above
		Collection<Patient> patients = patientService.getPatientList();
		assertTrue(patients.contains(pat1));
	}

	@Test
	public void createPatient2() throws Exception {
		// Add the video
		patientService.addPatient(pat2);

		// We should get back the video that we added above
		Collection<Patient> patients = patientService.getPatientList();
		assertTrue(patients.contains(pat2));
	}

	@Test
	public void createPatient3() throws Exception {
		// Add the video
		patientService.addPatient(pat3);

		// We should get back the video that we added above
		Collection<Patient> patients = patientService.getPatientList();
		assertTrue(patients.contains(pat3));
	}

	@Test
	public void createPatient4() throws Exception {
		// Add the video
		patientService.addPatient(pat4);

		// We should get back the video that we added above
		Collection<Patient> patients = patientService.getPatientList();
		assertTrue(patients.contains(pat4));
	}

	@Test
	public void createDoctor1() throws Exception {
		// Add the video
		doctorService.addDoctor(doc1);

		// We should get back the video that we added above
		Collection<Doctor> doctors = doctorService.getDoctorList();
		assertTrue(doctors.contains(doc1));
	}

	@Test
	public void createDoctor2() throws Exception {
		// Add the video
		doctorService.addDoctor(doc2);

		// We should get back the video that we added above
		Collection<Doctor> doctors = doctorService.getDoctorList();
		assertTrue(doctors.contains(doc2));
	}

	@Test
	public void createDoctor3() throws Exception {
		// Add the video
		doctorService.addDoctor(doc3);

		// We should get back the video that we added above
		Collection<Doctor> doctors = doctorService.getDoctorList();
		assertTrue(doctors.contains(doc3));
	}

	@Test
	public void createAssociation1() throws Exception {
		doctorPatientService.addDoctorpatients(doc1Pat1);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc1Pat1));
	}

	@Test
	public void createAssociation2() throws Exception {
		doctorPatientService.addDoctorpatients(doc1Pat2);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc1Pat2));
	}

	@Test
	public void createAssociation3() throws Exception {
		doctorPatientService.addDoctorpatients(doc1Pat3);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc1Pat3));
	}

	@Test
	public void createAssociation4() throws Exception {
		doctorPatientService.addDoctorpatients(doc1Pat4);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc1Pat4));
	}

	@Test
	public void createAssociation5() throws Exception {
		doctorPatientService.addDoctorpatients(doc2Pat1);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc2Pat1));
	}

	@Test
	public void createAssociation6() throws Exception {
		doctorPatientService.addDoctorpatients(doc2Pat2);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc2Pat2));
	}

	@Test
	public void createAssociation7() throws Exception {
		doctorPatientService.addDoctorpatients(doc2Pat3);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc2Pat3));
	}

	@Test
	public void createAssociation8() throws Exception {
		doctorPatientService.addDoctorpatients(doc3Pat1);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc3Pat1));
	}

	@Test
	public void createAssociation9() throws Exception {
		doctorPatientService.addDoctorpatients(doc3Pat2);

		// We should get back the video that we added above
		Collection<DoctorPatients> docPat = doctorPatientService
				.getDoctorpatientsList();
		assertTrue(docPat.contains(doc3Pat2));
	}

	@Test
	public void createMedicine1() throws Exception {
		medicineService.addMedicine(med1);

		// We should get back the video that we added above
		Collection<Medicine> med = medicineService.getMedicineList();
		assertTrue(med.contains(med1));
	}

	@Test
	public void createMedicine2() throws Exception {
		medicineService.addMedicine(med2);

		// We should get back the video that we added above
		Collection<Medicine> med = medicineService.getMedicineList();
		assertTrue(med.contains(med2));
	}

	@Test
	public void createMedicine3() throws Exception {
		medicineService.addMedicine(med3);

		// We should get back the video that we added above
		Collection<Medicine> med = medicineService.getMedicineList();
		assertTrue(med.contains(med3));
	}

	@Test
	public void createMedicine4() throws Exception {
		medicineService.addMedicine(med4);

		// We should get back the video that we added above
		Collection<Medicine> med = medicineService.getMedicineList();
		assertTrue(med.contains(med4));
	}

	@Test
	public void createMedicine5() throws Exception {
		medicineService.addMedicine(med5);

		// We should get back the video that we added above
		Collection<Medicine> med = medicineService.getMedicineList();
		assertTrue(med.contains(med5));
	}

	@Test
	public void createMedicine6() throws Exception {
		medicineService.addMedicine(med6);

		// We should get back the video that we added above
		Collection<Medicine> med = medicineService.getMedicineList();
		assertTrue(med.contains(med6));
	}

	@Test
	public void createMedicine7() throws Exception {
		medicineService.addMedicine(med7);

		// We should get back the video that we added above
		Collection<Medicine> med = medicineService.getMedicineList();
		assertTrue(med.contains(med7));
	}
}
