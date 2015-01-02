package miguelmaciel.capstone.repositorys;

import com.google.common.base.Objects;

public class AlarmingSymptons {
	private long id;
	private String sympton;
	private String situation;
	private String date;
	private long checkin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSympton() {
		return sympton;
	}

	public void setSympton(String sympton) {
		this.sympton = sympton;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getCheckin() {
		return checkin;
	}

	public void setCheckin(long checkin) {
		this.checkin = checkin;
	}

	public AlarmingSymptons(String sympton, String situation, String date,
			long checkin) {
		super();
		this.sympton = sympton;
		this.situation = situation;
		this.date = date;
		this.checkin = checkin;
	}

	public AlarmingSymptons() {
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(sympton, situation, date, checkin);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AlarmingSymptons) {
			AlarmingSymptons other = (AlarmingSymptons) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(sympton, other.sympton)
					&& Objects.equal(situation, other.situation)
					&& Objects.equal(date, other.date)
					&& Objects.equal(checkin, other.checkin);
		} else {
			return false;
		}
	}

}
