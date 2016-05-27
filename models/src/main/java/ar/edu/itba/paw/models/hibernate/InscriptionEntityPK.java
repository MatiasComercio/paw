package ar.edu.itba.paw.models.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class InscriptionEntityPK implements Serializable {
	private int docket;
	private int courseId;

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

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final InscriptionEntityPK that = (InscriptionEntityPK) o;

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
}
