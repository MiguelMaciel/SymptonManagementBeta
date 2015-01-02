package org.magnum.sympton.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "checkin")
public class CheckIn implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idcheckin")
	private long id;

	private String datecheckin;
	private String state;
	private String throatlevel;
	private String painlevel;
	private String takemed;
	private String observations;
	private long patient;

	public CheckIn() {
	}

	public CheckIn(String datecheckin, String state, String throatlevel,
			String painlevel, String takemed, String observations, long patient) {
		super();
		this.datecheckin = datecheckin;
		this.state = state;
		this.throatlevel = throatlevel;
		this.painlevel = painlevel;
		this.takemed = takemed;
		this.observations = observations;
		this.patient = patient;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDatecheckin() {
		return datecheckin;
	}

	public void setDatecheckin(String datecheckin) {
		this.datecheckin = datecheckin;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getThroatlevel() {
		return throatlevel;
	}

	public void setThroatlevel(String throatlevel) {
		this.throatlevel = throatlevel;
	}

	public String getPainlevel() {
		return painlevel;
	}

	public void setPainlevel(String painlevel) {
		this.painlevel = painlevel;
	}

	public String getTakemed() {
		return takemed;
	}

	public void setTakemed(String takemed) {
		this.takemed = takemed;
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
		return Objects.hashCode(datecheckin, state, throatlevel, painlevel,
				takemed, observations, patient);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckIn) {
			CheckIn other = (CheckIn) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(datecheckin, other.datecheckin)
					&& Objects.equal(state, other.state)
					&& Objects.equal(throatlevel, other.throatlevel)
					&& Objects.equal(painlevel, other.painlevel)
					&& Objects.equal(takemed, other.takemed)
					&& Objects.equal(observations, other.observations)
					&& Objects.equal(patient, other.patient);
		} else {
			return false;
		}
	}

}
