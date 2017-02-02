package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;

import java.time.LocalDate;

/**
 * Used for returning info about the logged in user with role ADMIN
 */
public class UserSessionDTO {

	private Integer dni;
	private String firstName;
	private String lastName;
	private User.Genre genre;
	private LocalDate birthday;
	private String email;
	private Role role;
	private AddressDTO address;

	public UserSessionDTO() {
	}

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

	public User.Genre getGenre() {
		return genre;
	}

	public void setGenre(final User.Genre genre) {
		this.genre = genre;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(final LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(final AddressDTO address) {
		this.address = address;
	}
}
