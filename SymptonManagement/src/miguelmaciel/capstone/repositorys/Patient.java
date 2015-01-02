package miguelmaciel.capstone.repositorys;

import com.google.common.base.Objects;


/**
 * A simple object to represent a video and its URL for viewing.
 * 
 * @author jules
 * 
 */
public class Patient {

	private long id;

	private String firstname;
	private String lastname;
	private String dateofbirth;
	private String email;
	private String username;
	private String password;


	public Patient() {
	}

	public Patient(String firstname, String lastname, String dateofbirth,
			String email, String username, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateofbirth = dateofbirth;
		this.email = email;
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

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * Two Videos will generate the same hashcode if they have exactly the same
	 * values for their name, url, and duration.
	 * 
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(firstname, lastname, dateofbirth, email,
				username, password);
	}

	/**
	 * Two Videos are considered equal if they have exactly the same values for
	 * their name, url, and duration.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Patient) {
			Patient other = (Patient) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(firstname, other.firstname)
					&& Objects.equal(lastname, other.lastname)
					&& Objects.equal(dateofbirth, other.dateofbirth)
					&& Objects.equal(email, other.email)
					&& Objects.equal(username, other.username)
					&& Objects.equal(password, other.password);
		} else {
			return false;
		}
	}

}
