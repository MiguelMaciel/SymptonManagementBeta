package miguelmaciel.capstone.repositorys;

import com.google.common.base.Objects;

public class AlertMedication {

	private long id;
	private String message;
	private String date;
	private long patient;
	private long doctor;
	private long medication;

	public AlertMedication() {
	}

	public AlertMedication(String message, String date, long patient,
			long doctor, long medication) {
		super();
		this.message = message;
		this.date = date;
		this.patient = patient;
		this.doctor = doctor;
		this.medication = medication;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getPatient() {
		return patient;
	}

	public void setPatient(long patient) {
		this.patient = patient;
	}

	public long getDoctor() {
		return doctor;
	}

	public void setDoctor(long doctor) {
		this.doctor = doctor;
	}

	public long getMedication() {
		return medication;
	}

	public void setMedication(long medication) {
		this.medication = medication;
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(message, date, patient, doctor, medication);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AlertMedication) {
			AlertMedication other = (AlertMedication) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(message, other.message)
					&& Objects.equal(date, other.date)
					&& Objects.equal(patient, other.patient)
					&& Objects.equal(doctor, other.doctor)
					&& Objects.equal(medication, other.medication);
		} else {
			return false;
		}
	}
}
