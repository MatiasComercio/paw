package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

// +++xcheck if setters can be removed
public class GradePK implements Serializable {
	private int docket;
	private int courseId;
	private BigDecimal grade;
	private Timestamp modified;

	@Column(name = "docket", nullable = false)
	@Id
	public int getDocket() {
		return docket;
	}

	public void setDocket(final int docket) {
		this.docket = docket;
	}

	@Column(name = "course_id", nullable = false)
	@Id
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(final int courseId) {
		this.courseId = courseId;
	}

	@Column(name = "grade", nullable = false, precision = 2)
	@Id
	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(final BigDecimal grade) {
		this.grade = grade;
	}

	@Column(name = "modified", nullable = false)
	@Id
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

		final GradePK that = (GradePK) o;

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
}
