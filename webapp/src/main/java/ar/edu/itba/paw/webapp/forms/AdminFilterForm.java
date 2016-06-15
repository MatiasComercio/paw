package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Size;

public class AdminFilterForm {
	private Integer dni;
	@Size(max=50)
	private String firstName;
	@Size(max=50)
	private String lastName;

	public Integer getDni() {
		return dni;
	}

	public void setDni(final Integer dni) {
		this.dni = dni;
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
		dni = null;
		firstName = null;
		lastName = null;
	}
}
