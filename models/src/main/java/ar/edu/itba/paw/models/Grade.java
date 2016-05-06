package ar.edu.itba.paw.models;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class Grade {
	private final int studentDocket;
	private final String studentFirstName;
	private final String studentLastName;
	private final int courseId;
	private final String courseName;
	private final BigDecimal grade;
	private final Timestamp modified;

	private Grade(final Builder builder) {
		this.studentDocket = builder.studentDocket;
		this.studentFirstName = builder.studentFirstName;
		this.studentLastName = builder.studentLastName;
		this.courseId = builder.courseId;
		this.courseName = builder.courseName;
		this.grade = builder.grade;
		this.modified = builder.modified;
	}

	public int getStudentDocket() {
		return studentDocket;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public Timestamp getModified() { return modified; }


	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Grade)) return false;

		final Grade grade1 = (Grade) o;

		if (studentDocket != grade1.studentDocket) return false;
		if (courseId != grade1.courseId) return false;
		if (!grade.equals(grade1.grade)) return false;
		return modified.equals(grade1.modified);

	}

	@Override
	public int hashCode() {
		int result = studentDocket;
		result = 31 * result + courseId;

        //TODO: grade and modified could be null
        if (grade != null)
            result = 31 * result + grade.hashCode();
		if (modified != null)
            result = 31 * result + modified.hashCode();
		return result;
	}

	public static class Builder {
		private final int studentDocket;
		private final int courseId;
		private final BigDecimal grade;

		private String studentFirstName = "";
		private String studentLastName = "";
		private String courseName = "";
		private Timestamp modified;

		public Builder(final int studentDocket, final int courseId, final BigDecimal grade) {
			this.studentDocket = studentDocket;
			this.courseId = courseId;
			this.grade = grade;
		}

		public Builder studentFirstName(final String studentFirstName) {
			this.studentFirstName = studentFirstName;
			return this;
		}

		public Builder studentLastName(final String studentLastName) {
			this.studentLastName = studentLastName;
			return this;
		}

		public Builder courseName(final String courseName) {
			this.courseName = courseName;
			return this;
		}

		public Builder modified(final Timestamp modified) {
			this.modified = modified;
			return this;
		}

		public Grade build() {
			return new Grade(this);
		}
	}
}
