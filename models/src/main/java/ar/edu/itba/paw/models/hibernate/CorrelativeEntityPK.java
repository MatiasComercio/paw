package ar.edu.itba.paw.models.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CorrelativeEntityPK implements Serializable {
	private int courseId;
	private int correlativeId;

	@Column(name = "course_id", nullable = false)
	@Id
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(final int courseId) {
		this.courseId = courseId;
	}

	@Column(name = "correlative_id", nullable = false)
	@Id
	public int getCorrelativeId() {
		return correlativeId;
	}

	public void setCorrelativeId(final int correlativeId) {
		this.correlativeId = correlativeId;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final CorrelativeEntityPK that = (CorrelativeEntityPK) o;

		if (courseId != that.courseId) return false;
		if (correlativeId != that.correlativeId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = courseId;
		result = 31 * result + correlativeId;
		return result;
	}
}
