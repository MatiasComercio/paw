package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;

//@Entity
//@Table(name = "admin")
public class AdminEntity {
	private int dni;
	private UsersEntity usersByDni;

	@Id
	@Column(name = "dni", nullable = false)
	public int getDni() {
		return dni;
	}

	public void setDni(final int dni) {
		this.dni = dni;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final AdminEntity that = (AdminEntity) o;

		if (dni != that.dni) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return dni;
	}

	@OneToOne
	@JoinColumn(name = "dni", referencedColumnName = "dni", nullable = false)
	public UsersEntity getUsersByDni() {
		return usersByDni;
	}

	public void setUsersByDni(final UsersEntity usersByDni) {
		this.usersByDni = usersByDni;
	}
}
