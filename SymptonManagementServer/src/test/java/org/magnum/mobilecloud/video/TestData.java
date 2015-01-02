package org.magnum.mobilecloud.video;

import org.magnum.sympton.repository.Doctor;
import org.magnum.sympton.repository.DoctorPatients;
import org.magnum.sympton.repository.Medicine;
import org.magnum.sympton.repository.Patient;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a utility class to aid in the construction of Video objects with
 * random names, urls, and durations. The class also provides a facility to
 * convert objects into JSON using Jackson, which is the format that the
 * VideoSvc controller is going to expect data in for integration testing.
 * 
 * @author jules
 * 
 */
public class TestData {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Construct and return a Video object with a rnadom name, url, and
	 * duration.
	 * 
	 * @return
	 */

	public static Patient patient1() {
		String firstname = "Miguel";
		String lastname = "Maciel";
		String dateofbirth = "07-08-1989";
		String email = "MiguelMaciel@gmail.com";
		String username = "pat1";
		String password = "pass";

		return new Patient(1, firstname, lastname, dateofbirth, email,
				username, password);
	}

	public static Patient patient2() {
		String firstname = "Daniela";
		String lastname = "Carvalho";
		String dateofbirth = "26-05-1983";
		String email = "Daniela@gmail.com";
		String username = "pat2";
		String password = "pass";

		return new Patient(2, firstname, lastname, dateofbirth, email,
				username, password);
	}

	public static Patient patient3() {
		String firstname = "John";
		String lastname = "Doe";
		String dateofbirth = "16-11-1985";
		String email = "JohnDoe@gmail.com";
		String username = "pat3";
		String password = "pass";

		return new Patient(3, firstname, lastname, dateofbirth, email,
				username, password);
	}

	public static Patient patient4() {
		String firstname = "Richard";
		String lastname = "Meir";
		String dateofbirth = "20-02-1970";
		String email = "Richard@gmail.com";
		String username = "pat4";
		String password = "pass";

		return new Patient(4, firstname, lastname, dateofbirth, email,
				username, password);
	}

	public static Doctor doctor1() {
		String firstname = "Dr Joao";
		String lastname = "Ferreira";
		String email = "MDJOao@gmail.com";
		String specialization = "Clinica Geral";
		String username = "doc1";
		String password = "pass";

		return new Doctor(1, firstname, lastname, email, specialization,
				username, password);
	}

	public static Doctor doctor2() {
		String firstname = "Dr Ana";
		String lastname = "Melo";
		String email = "MDAnaMelo@gmail.com";
		String specialization = "Tecnica Medica";
		String username = "doc2";
		String password = "pass";

		return new Doctor(2, firstname, lastname, email, specialization,
				username, password);
	}

	public static Doctor doctor3() {
		String firstname = "MD House";
		String lastname = "Doe";
		String email = "MDHouse@gmail.com";
		String specialization = "Boss";
		String username = "doc3";
		String password = "pass";

		return new Doctor(3, firstname, lastname, email, specialization,
				username, password);
	}

	public static DoctorPatients doc1Pat1() {
		return new DoctorPatients(1, 1);
	}

	public static DoctorPatients doc1Pat2() {
		return new DoctorPatients(1, 2);
	}

	public static DoctorPatients doc1Pat3() {
		return new DoctorPatients(1, 3);
	}

	public static DoctorPatients doc1Pat4() {
		return new DoctorPatients(1, 4);
	}

	public static DoctorPatients doc2Pat1() {
		return new DoctorPatients(2, 1);
	}

	public static DoctorPatients doc2Pat2() {
		return new DoctorPatients(2, 2);
	}

	public static DoctorPatients doc2Pat3() {
		return new DoctorPatients(2, 3);
	}

	public static DoctorPatients doc3Pat1() {
		return new DoctorPatients(3, 1);
	}

	public static DoctorPatients doc3Pat2() {
		return new DoctorPatients(3, 2);
	}

	public static Medicine addMed1() {
		return new Medicine("Lortab", "500 mg lortab");
	}

	public static Medicine addMed2() {
		return new Medicine("OxyContin", "1000 mg oxycontin");
	}

	public static Medicine addMed3() {
		return new Medicine("Paracetemol", "500mg paracetemol");
	}

	public static Medicine addMed4() {
		return new Medicine("AAAAA", "500mg AAAAA");
	}

	public static Medicine addMed5() {
		return new Medicine("BBBBB", "500mg BBBBBB");
	}

	public static Medicine addMed6() {
		return new Medicine("CCCCCC", "500mg CCCCCC");
	}

	public static Medicine addMed7() {
		return new Medicine("DDDDDDD", "500mg DDDDD");
	}

	/**
	 * Convert an object to JSON using Jackson's ObjectMapper
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception {
		return objectMapper.writeValueAsString(o);
	}
}
