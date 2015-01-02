package miguelmaciel.capstone.repositorys;

import com.google.common.base.Objects;

public class Doctor {
	private long id;

	private String firstname;
	private String lastname;
	private String email;
	private String specialization;
	private String username;
	private String password;

	public Doctor() {
	}

	public Doctor(String firstname, String lastname, String email,
			String specialization, String username, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.specialization = specialization;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(firstname, lastname, email, specialization,
				username, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Doctor) {
			Doctor other = (Doctor) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(firstname, other.firstname)
					&& Objects.equal(lastname, other.lastname)
					&& Objects.equal(email, other.email)
					&& Objects.equal(specialization, other.specialization)
					&& Objects.equal(username, other.username)
					&& Objects.equal(password, other.password);
		} else {
			return false;
		}
	}

}
