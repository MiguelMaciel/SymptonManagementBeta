package miguelmaciel.capstone.repositorys;

import com.google.common.base.Objects;

public class CheckInMedication {
	private long checkinmedicationid;
	private long checkin;
	private long medication;
	private String takestate;
	private String date;
	private String time;
	private String medicineName;
	private Integer position;

	

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public long getCheckinmedicationid() {
		return checkinmedicationid;
	}

	public void setCheckinmedicationid(long checkinmedicationid) {
		this.checkinmedicationid = checkinmedicationid;
	}

	public long getCheckin() {
		return checkin;
	}

	public void setCheckin(long checkin) {
		this.checkin = checkin;
	}

	public long getMedication() {
		return medication;
	}

	public void setMedication(long medication) {
		this.medication = medication;
	}

	public String getTakestate() {
		return takestate;
	}

	public void setTakestate(String takestate) {
		this.takestate = takestate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public CheckInMedication(long checkin, long medication, String takestate,
			String date, String time) {
		super();
		this.checkin = checkin;
		this.medication = medication;
		this.takestate = takestate;
		this.date = date;
		this.time = time;
	}

	public CheckInMedication() {
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(checkin, medication, takestate, date, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckInMedication) {
			CheckInMedication other = (CheckInMedication) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(checkin, other.checkin)
					&& Objects.equal(medication, other.medication)
					&& Objects.equal(takestate, other.takestate)
					&& Objects.equal(date, other.date)
					&& Objects.equal(time, other.time);
		} else {
			return false;
		}
	}
}
