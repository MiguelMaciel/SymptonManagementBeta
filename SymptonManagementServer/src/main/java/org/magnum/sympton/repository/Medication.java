package org.magnum.sympton.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = "medication")
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idmedication")
	private long id;

	private String startdate;
	private String enddate;
	private String dose;
	private String active;
	private String observations;
	private long patient;
	private long doctor;
	private long medicine;

	public Medication(String startdate, String enddate, String dose,
			String active, String observations, long patient, long doctor,
			long medicine) {
		super();
		this.startdate = startdate;
		this.enddate = enddate;
		this.dose = dose;
		this.active = active;
		this.observations = observations;
		this.patient = patient;
		this.doctor = doctor;
		this.medicine = medicine;
	}

	public Medication() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
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

	public long getMedicine() {
		return medicine;
	}

	public void setMedicine(long medicine) {
		this.medicine = medicine;
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(startdate, enddate, dose, active, observations,
				patient, doctor, medicine);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Medication) {
			Medication other = (Medication) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(startdate, other.startdate)
					&& Objects.equal(enddate, other.enddate)
					&& Objects.equal(dose, other.dose)
					&& Objects.equal(active, other.active)
					&& Objects.equal(observations, other.observations)
					&& Objects.equal(patient, other.patient)
					&& Objects.equal(doctor, other.doctor)
					&& Objects.equal(medicine, other.medicine);
		} else {
			return false;
		}
	}
}
