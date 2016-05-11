package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class PasswordForm {

	/* +++xtodo: @Gonza: improve validations */
//	@NotBlank
	@Digits(integer=8, fraction=0)
	@NotNull
	private int dni;

	@NotBlank
	private String currentPassword;

	@NotBlank
	private String newPassword;

	@NotBlank
	private String repeatNewPassword;

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

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	public void setRepeatNewPassword(final String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}
}
