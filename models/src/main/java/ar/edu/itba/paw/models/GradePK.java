package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// +++xcheck if setters can be removed
public class GradePK implements Serializable {
	@Column(name = "docket", nullable = false)
	@Id
	private int studentDocket;

	@Column(name = "course_id", nullable = false)
	@Id
	private int courseId;

	@Column(name = "grade", nullable = false, precision = 2)
	@Id
	private BigDecimal grade;

	@Column(name = "modified", nullable = false)
	@Id
	private LocalDateTime modified;

	public GradePK() {
		// Just for Hibernate
	}

	public int getDocket() {
		return studentDocket;
	}

	public void setDocket(final int studentDocket) {
		this.studentDocket = studentDocket;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(final int courseId) {
		this.courseId = courseId;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(final BigDecimal grade) {
		this.grade = grade;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(final LocalDateTime modified) {
		this.modified = modified;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final GradePK that = (GradePK) o;

		if (studentDocket != that.studentDocket) return false;
		if (courseId != that.courseId) return false;
		if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
		if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = studentDocket;
		result = 31 * result + courseId;
		result = 31 * result + (grade != null ? grade.hashCode() : 0);
		result = 31 * result + (modified != null ? modified.hashCode() : 0);
		return result;
	}
}
