package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "grade")
@IdClass(GradeEntityPK.class)
public class GradeEntity {
	private int docket;
	private int courseId;
	private BigDecimal grade;
	private Timestamp modified;
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

	@Id
	@Column(name = "grade", nullable = false, precision = 2)
	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(final BigDecimal grade) {
		this.grade = grade;
	}

	@Id
	@Column(name = "modified", nullable = false)
	public Timestamp getModified() {
		return modified;
	}

	public void setModified(final Timestamp modified) {
		this.modified = modified;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final GradeEntity that = (GradeEntity) o;

		if (docket != that.docket) return false;
		if (courseId != that.courseId) return false;
		if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
		if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = docket;
		result = 31 * result + courseId;
		result = 31 * result + (grade != null ? grade.hashCode() : 0);
		result = 31 * result + (modified != null ? modified.hashCode() : 0);
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
