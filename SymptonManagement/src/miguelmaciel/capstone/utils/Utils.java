package miguelmaciel.capstone.utils;

public class Utils {
	public static Long idPatient;
	public static Long idDoctor;
	public static String server = "https://10.0.2.2:8443";
	public static int subMenu;	
	public static String PatientUser;
	public static String PatientPass;
	public static String DoctorUser;
	public static String DoctorPass;

	public static String NameUser;
	public static Long getIdPatient() {
		return idPatient;
	}
	public static void setIdPatient(Long idPatient) {
		Utils.idPatient = idPatient;
	}
	public static Long getIdDoctor() {
		return idDoctor;
	}
	public static void setIdDoctor(Long idDoctor) {
		Utils.idDoctor = idDoctor;
	}
	public static String getServer() {
		return server;
	}
	public static void setServer(String server) {
		Utils.server = server;
	}
	public static int getSubMenu() {
		return subMenu;
	}
	public static void setSubMenu(int subMenu) {
		Utils.subMenu = subMenu;
	}
	public static String getPatientUser() {
		return PatientUser;
	}
	public static void setPatientUser(String patientUser) {
		PatientUser = patientUser;
	}
	public static String getPatientPass() {
		return PatientPass;
	}
	public static void setPatientPass(String patientPass) {
		PatientPass = patientPass;
	}
	public static String getDoctorUser() {
		return DoctorUser;
	}
	public static void setDoctorUser(String doctorUser) {
		DoctorUser = doctorUser;
	}
	public static String getDoctorPass() {
		return DoctorPass;
	}
	public static void setDoctorPass(String doctorPass) {
		DoctorPass = doctorPass;
	}
	public static String getNameUser() {
		return NameUser;
	}
	public static void setNameUser(String nameUser) {
		NameUser = nameUser;
	}
	
	


	
}
