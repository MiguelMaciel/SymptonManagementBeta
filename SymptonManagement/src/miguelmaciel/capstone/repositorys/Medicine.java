package miguelmaciel.capstone.repositorys;

import com.google.common.base.Objects;

public class Medicine {
	private long id;
	private String name;
	private String composition;

	public Medicine() {
	}

	public Medicine(String name, String composition) {
		super();
		this.name = name;
		this.composition = composition;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComposition() {
		return composition;
	}

	public void setComposition(String composition) {
		this.composition = composition;
	}

	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(name, composition);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Medicine) {
			Medicine other = (Medicine) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(name, other.name)
					&& Objects.equal(composition, other.composition);
		} else {
			return false;
		}
	}

}
