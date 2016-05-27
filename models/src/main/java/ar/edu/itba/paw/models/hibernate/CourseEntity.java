package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;
import java.util.Collection;

//@Entity
//@Table(name = "course", schema = "public", catalog = "paw")
public class CourseEntity {
	private int id;
	private String name;
	private int credits;
	private int semester;
	private Collection<CorrelativeEntity> correlativesById;
	private Collection<CorrelativeEntity> correlativesById_0;
	private Collection<GradeEntity> gradesById;
	private Collection<InscriptionEntity> inscriptionsById;

	@Id
	@Column(name = "id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "credits", nullable = false)
	public int getCredits() {
		return credits;
	}

	public void setCredits(final int credits) {
		this.credits = credits;
	}

	@Basic
	@Column(name = "semester", nullable = false)
	public int getSemester() {
		return semester;
	}

	public void setSemester(final int semester) {
		this.semester = semester;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final CourseEntity that = (CourseEntity) o;

		if (id != that.id) return false;
		if (credits != that.credits) return false;
		if (semester != that.semester) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + credits;
		result = 31 * result + semester;
		return result;
	}

	@OneToMany(mappedBy = "courseByCourseId")
	public Collection<CorrelativeEntity> getCorrelativesById() {
		return correlativesById;
	}

	public void setCorrelativesById(final Collection<CorrelativeEntity> correlativesById) {
		this.correlativesById = correlativesById;
	}

	@OneToMany(mappedBy = "courseByCorrelativeId")
	public Collection<CorrelativeEntity> getCorrelativesById_0() {
		return correlativesById_0;
	}

	public void setCorrelativesById_0(final Collection<CorrelativeEntity> correlativesById_0) {
		this.correlativesById_0 = correlativesById_0;
	}

	@OneToMany(mappedBy = "courseByCourseId")
	public Collection<GradeEntity> getGradesById() {
		return gradesById;
	}

	public void setGradesById(final Collection<GradeEntity> gradesById) {
		this.gradesById = gradesById;
	}

	@OneToMany(mappedBy = "courseByCourseId")
	public Collection<InscriptionEntity> getInscriptionsById() {
		return inscriptionsById;
	}

	public void setInscriptionsById(final Collection<InscriptionEntity> inscriptionsById) {
		this.inscriptionsById = inscriptionsById;
	}
}
