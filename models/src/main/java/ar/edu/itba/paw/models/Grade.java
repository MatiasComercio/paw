package ar.edu.itba.paw.models;

import java.math.BigDecimal;
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
		if (studentFirstName != null ? !studentFirstName.equals(grade1.studentFirstName) : grade1.studentFirstName != null)
			return false;
		if (studentLastName != null ? !studentLastName.equals(grade1.studentLastName) : grade1.studentLastName != null)
			return false;
		if (courseName != null ? !courseName.equals(grade1.courseName) : grade1.courseName != null) return false;
		return grade.equals(grade1.grade);
	}

	@Override
	public int hashCode() {
		int result = studentDocket;
		result = 31 * result + (studentFirstName != null ? studentFirstName.hashCode() : 0);
		result = 31 * result + (studentLastName != null ? studentLastName.hashCode() : 0);
		result = 31 * result + courseId;
		result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
		result = 31 * result + grade.hashCode();
		return result;
	}

	public static class Builder {
		private final int studentDocket;
		private final int courseId;
		private final BigDecimal grade;
		private final Timestamp modified;

		private String studentFirstName = "";
		private String studentLastName = "";
		private String courseName = "";

		public Builder(final int studentDocket, final int courseId, final BigDecimal grade, final Timestamp modified) {
			this.studentDocket = studentDocket;
			this.courseId = courseId;
			this.grade = grade;
			this.modified = modified;
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

		public Grade build() {
			return new Grade(this);
		}
	}
}
