package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "student", schema = "public", catalog = "paw")
public class StudentEntity {
	private int docket;
	private int dni;
	private Collection<GradeEntity> gradesByDocket;
	private Collection<InscriptionEntity> inscriptionsByDocket;
	private UsersEntity usersByDni;

	@Id
	@Column(name = "docket", nullable = false)
	public int getDocket() {
		return docket;
	}

	public void setDocket(final int docket) {
		this.docket = docket;
	}

	@Basic
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

		final StudentEntity that = (StudentEntity) o;

		if (docket != that.docket) return false;
		if (dni != that.dni) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = docket;
		result = 31 * result + dni;
		return result;
	}

	@OneToMany(mappedBy = "studentByDocket")
	public Collection<GradeEntity> getGradesByDocket() {
		return gradesByDocket;
	}

	public void setGradesByDocket(final Collection<GradeEntity> gradesByDocket) {
		this.gradesByDocket = gradesByDocket;
	}

	@OneToMany(mappedBy = "studentByDocket")
	public Collection<InscriptionEntity> getInscriptionsByDocket() {
		return inscriptionsByDocket;
	}

	public void setInscriptionsByDocket(final Collection<InscriptionEntity> inscriptionsByDocket) {
		this.inscriptionsByDocket = inscriptionsByDocket;
	}

	@ManyToOne
	@JoinColumn(name = "dni", referencedColumnName = "dni", nullable = false)
	public UsersEntity getUsersByDni() {
		return usersByDni;
	}

	public void setUsersByDni(final UsersEntity usersByDni) {
		this.usersByDni = usersByDni;
	}
}
