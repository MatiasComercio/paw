package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "users", schema = "public", catalog = "paw")
public class UsersEntity {
	private int dni;
	private String firstName;
	private String lastName;
	private String genre;
	private LocalDate birthday;
	private String email;
	private String password;
	private String role;
	private AddressEntity addressByDni;
	private AdminEntity adminByDni;
	private Collection<StudentEntity> studentsByDni;
	private RoleEntity roleByRole;

	@Id
	@Column(name = "dni", nullable = false)
	public int getDni() {
		return dni;
	}

	public void setDni(final int dni) {
		this.dni = dni;
	}

	@Basic
	@Column(name = "first_name", nullable = false, length = 50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	@Basic
	@Column(name = "last_name", nullable = false, length = 50)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	@Basic
	@Column(name = "genre", nullable = true, length = -1)
	public String getGenre() {
		return genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	@Basic
	@Column(name = "birthday", nullable = true)
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(final LocalDate birthday) {
		this.birthday = birthday;
	}

	@Basic
	@Column(name = "email", nullable = false, length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "password", nullable = true, length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "role", nullable = false, length = 50)
	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final UsersEntity that = (UsersEntity) o;

		if (dni != that.dni) return false;
		if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
		if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
		if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
		if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		if (password != null ? !password.equals(that.password) : that.password != null) return false;
		if (role != null ? !role.equals(that.role) : that.role != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dni;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (genre != null ? genre.hashCode() : 0);
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (role != null ? role.hashCode() : 0);
		return result;
	}

	@OneToOne(mappedBy = "usersByDni")
	public AddressEntity getAddressByDni() {
		return addressByDni;
	}

	public void setAddressByDni(final AddressEntity addressByDni) {
		this.addressByDni = addressByDni;
	}

	@OneToOne(mappedBy = "usersByDni")
	public AdminEntity getAdminByDni() {
		return adminByDni;
	}

	public void setAdminByDni(final AdminEntity adminByDni) {
		this.adminByDni = adminByDni;
	}

	@OneToMany(mappedBy = "usersByDni")
	public Collection<StudentEntity> getStudentsByDni() {
		return studentsByDni;
	}

	public void setStudentsByDni(final Collection<StudentEntity> studentsByDni) {
		this.studentsByDni = studentsByDni;
	}

	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "role", nullable = false)
	public RoleEntity getRoleByRole() {
		return roleByRole;
	}

	public void setRoleByRole(final RoleEntity roleByRole) {
		this.roleByRole = roleByRole;
	}
}
