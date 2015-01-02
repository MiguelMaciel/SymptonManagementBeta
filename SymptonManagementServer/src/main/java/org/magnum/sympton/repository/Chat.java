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
@Table(name = "chat")
public class Chat implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long chatid;

	@Column(name = "doctor")
	private long doctor;

	@Column(name = "patient")
	private long patient;

	private String sendfrom;
	private String date;
	private String message;

	public Chat() {
	}

	public Chat(long doctor, long patient, String sendfrom, String date,
			String message) {
		super();
		this.doctor = doctor;
		this.patient = patient;
		this.sendfrom = sendfrom;
		this.date = date;
		this.message = message;
	}

	public long getChatid() {
		return chatid;
	}

	public void setChatid(long chatid) {
		this.chatid = chatid;
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

	public String getSendfrom() {
		return sendfrom;
	}

	public void setSendfrom(String sendfrom) {
		this.sendfrom = sendfrom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(doctor, patient, sendfrom, date, message);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chat) {
			Chat other = (Chat) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(doctor, other.doctor)
					&& Objects.equal(patient, other.patient)
					&& Objects.equal(sendfrom, other.sendfrom)
					&& Objects.equal(date, other.date)
					&& Objects.equal(message, other.message);
		} else {
			return false;
		}
	}
}
