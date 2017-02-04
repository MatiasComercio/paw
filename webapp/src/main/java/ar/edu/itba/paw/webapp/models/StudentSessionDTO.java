package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Used for returning info about the logged in user with role STUDENT
 *
 * Inheritance from UserSessionDTO was not used
 * due to JAXB returning 'type' field with 'studentSessionDTO' value in the JSON response.
 * XmlTransient annotation served useful to remove that field but it also ignored the docket field.
 *
 * Composition failed to work with modelMapper
 */
public class StudentSessionDTO {
	private Integer dni;
	private String firstName;
	private String lastName;
	private User.Genre genre;
	private LocalDate birthday;
	private String email;
	private Role role;
	private AddressDTO address;
	private int docket;
	private List<String> authorities;

	public StudentSessionDTO() {
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public User.Genre getGenre() {
		return genre;
	}

	public void setGenre(User.Genre genre) {
		this.genre = genre;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public int getDocket() {
		return docket;
	}

	public void setDocket(int docket) {
		this.docket = docket;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
}