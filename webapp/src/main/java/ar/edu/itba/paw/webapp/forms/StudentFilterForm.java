package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Size;

public class StudentFilterForm {

	private Integer docket;
	@Size(max=50)
	private String firstName;
	@Size(max=50)
	private String lastName;

	public Integer getDocket() {
		return docket;
	}

	public void setDocket(final Integer docket) {
		this.docket = docket;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName.trim();
	}

	public void empty() {
		docket = null;
		firstName = null;
		lastName = null;
	}
}
