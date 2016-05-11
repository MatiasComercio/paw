package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class ResetPasswordForm {
	@Digits(integer=8, fraction=0)
	@NotNull
	private int dni;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;

	public int getDni() {
		return dni;
	}

	public void setDni(final int dni) {
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
		dni = 0;
		firstName = null;
		lastName = null;
	}
}
