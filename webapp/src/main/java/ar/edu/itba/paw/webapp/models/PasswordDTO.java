package ar.edu.itba.paw.webapp.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class PasswordDTO {
	private int dni;

	@NotBlank
	private String currentPassword;

	@NotBlank
	@Size(min=8, max=32)
	private String newPassword;

	public int getDni() {
		return dni;
	}

	public void setDni(final int dni) {
		this.dni = dni;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(final String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}
}
