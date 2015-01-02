package org.magnum.sympton.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "systemprefs")
public class SystemPrefs implements Serializable {

	@Id
	@Column(name = "patient")
	private long patient;

	private String typenotification;

	public SystemPrefs(long patient, String typenotification) {
		super();
		this.patient = patient;
		this.typenotification = typenotification;
	}

	public SystemPrefs() {
	}

	public long getPatient() {
		return patient;
	}

	public void setPatient(long patient) {
		this.patient = patient;
	}

	public String getTypenotification() {
		return typenotification;
	}

	public void setTypenotification(String typenotification) {
		this.typenotification = typenotification;
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(patient, typenotification);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SystemPrefs) {
			SystemPrefs other = (SystemPrefs) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(patient, other.patient)
					&& Objects.equal(typenotification, other.typenotification);
		} else {
			return false;
		}
	}

}
