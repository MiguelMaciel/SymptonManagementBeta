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
@Table(name = "doctorpatients")
//@IdClass (value = DoctorPatientsId.class)
public class DoctorPatients implements Serializable {

	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long doctorpatientid;
	
	//@Id
	@Column(name = "doctor")
	private long doctor;

	//@Id
	@Column(name = "patient")
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

	/*public class DoctorPatientsId implements Serializable {
		private long doctor;
		private long patient;
		
		 public DoctorPatientsId(){
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
			if (obj instanceof DoctorPatientsId) {
				DoctorPatientsId other = (DoctorPatientsId) obj;
				// Google Guava provides great utilities for equals too!
				return Objects.equal(doctor, other.doctor)
						&& Objects.equal(patient, other.patient);
			} else {
				return false;
			}
		}
	}*/

}
