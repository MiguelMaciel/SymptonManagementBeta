package miguelmaciel.capstone.repositorys;

import com.google.common.base.Objects;

public class DoctorPatients {
	private long doctorpatientid;
	private long doctor;
	private long patient;

	public DoctorPatients() {
	}

	public DoctorPatients(long doctor, long patient) {
		super();
		this.doctor = doctor;
		this.patient = patient;
	}	
	
	public long getDoctorpatientid() {
		return doctorpatientid;
	}

	public void setDoctorpatientid(long doctorpatientid) {
		this.doctorpatientid = doctorpatientid;
	}

	public long getDoctor() {
		return doctor;
	}

	public void setDoctor(long doctor) {
		this.doctor = doctor;
	}

	public long getPatient() {
		return patient;
	}

	public void setPatient(long patient) {
		this.patient = patient;
	}
	
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(doctor, patient);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DoctorPatients) {
			DoctorPatients other = (DoctorPatients) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(doctor, other.doctor)
					&& Objects.equal(patient, other.patient);
		} else {
			return false;
		}
	}
}
