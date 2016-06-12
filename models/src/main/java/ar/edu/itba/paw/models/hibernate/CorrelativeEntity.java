package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;

//@Entity
//@Table(name = "correlative")
//@IdClass(CorrelativeEntityPK.class)
public class CorrelativeEntity {
	private int courseId;
	private int correlativeId;
	private CourseEntity courseByCourseId;
	private CourseEntity courseByCorrelativeId;

	@Id
	@Column(name = "course_id", nullable = false)
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(final int courseId) {
		this.courseId = courseId;
	}

	@Id
	@Column(name = "correlative_id", nullable = false)
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

		final CorrelativeEntity that = (CorrelativeEntity) o;

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

	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
	public CourseEntity getCourseByCourseId() {
		return courseByCourseId;
	}

	public void setCourseByCourseId(final CourseEntity courseByCourseId) {
		this.courseByCourseId = courseByCourseId;
	}

	@ManyToOne
	@JoinColumn(name = "correlative_id", referencedColumnName = "id", nullable = false)
	public CourseEntity getCourseByCorrelativeId() {
		return courseByCorrelativeId;
	}

	public void setCourseByCorrelativeId(final CourseEntity courseByCorrelativeId) {
		this.courseByCorrelativeId = courseByCorrelativeId;
	}
}
