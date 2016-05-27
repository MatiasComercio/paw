package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;

//@Entity
//@Table(name = "inscription", schema = "public", catalog = "paw")
//@IdClass(InscriptionEntityPK.class)
public class InscriptionEntity {
	private int docket;
	private int courseId;
	private StudentEntity studentByDocket;
	private CourseEntity courseByCourseId;

	@Id
	@Column(name = "docket", nullable = false)
	public int getDocket() {
		return docket;
	}

	public void setDocket(final int docket) {
		this.docket = docket;
	}

	@Id
	@Column(name = "course_id", nullable = false)
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(final int courseId) {
		this.courseId = courseId;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final InscriptionEntity that = (InscriptionEntity) o;

		if (docket != that.docket) return false;
		if (courseId != that.courseId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = docket;
		result = 31 * result + courseId;
		return result;
	}

	@ManyToOne
	@JoinColumn(name = "docket", referencedColumnName = "docket", nullable = false)
	public StudentEntity getStudentByDocket() {
		return studentByDocket;
	}

	public void setStudentByDocket(final StudentEntity studentByDocket) {
		this.studentByDocket = studentByDocket;
	}

	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
	public CourseEntity getCourseByCourseId() {
		return courseByCourseId;
	}

	public void setCourseByCourseId(final CourseEntity courseByCourseId) {
		this.courseByCourseId = courseByCourseId;
	}
}
