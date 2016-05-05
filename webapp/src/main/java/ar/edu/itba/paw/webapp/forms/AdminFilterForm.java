package ar.edu.itba.paw.webapp.forms;

public class AdminFilterForm {
	private Integer dni;
	private String firstName;
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
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public void empty() {
		dni = null;
		firstName = null;
		lastName = null;
	}
}
