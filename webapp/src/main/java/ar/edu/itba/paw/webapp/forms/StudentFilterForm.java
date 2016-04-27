package ar.edu.itba.paw.webapp.forms;

public class StudentFilterForm {

	private Integer docket;
	private String firstName;
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
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
}
