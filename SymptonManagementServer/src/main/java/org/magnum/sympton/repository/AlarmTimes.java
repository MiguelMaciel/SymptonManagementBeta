package org.magnum.sympton.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "alarmtimes")
public class AlarmTimes implements Serializable {

	@Id
	@Column(name = "patient")
	private long patient;

	private String alarmtimes;

	public AlarmTimes(long patient, String alarmtimes) {
		super();
		this.patient = patient;
		this.alarmtimes = alarmtimes;
	}

	public AlarmTimes() {
	}

	public long getPatient() {
		return patient;
	}

	public void setPatient(long patient) {
		this.patient = patient;
	}

	public String getAlarmtimes() {
		return alarmtimes;
	}

	public void setAlarmtimes(String alarmtimes) {
		this.alarmtimes = alarmtimes;
	}
	
	
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(patient, alarmtimes);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AlarmTimes) {
			AlarmTimes other = (AlarmTimes) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(patient, other.patient)
					&& Objects.equal(alarmtimes, other.alarmtimes);
		} else {
			return false;
		}
	}
	
	
	
}
