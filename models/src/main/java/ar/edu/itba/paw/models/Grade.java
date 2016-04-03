package ar.edu.itba.paw.models;

import java.math.BigDecimal;

public class Grade {
	private final int studentDocket;
	private final String studentFirstName;
	private final String studentLastName;
	private final int courseId;
	private final String courseName;
	private final BigDecimal grade;

	private Grade(final Builder builder) {
		this.studentDocket = builder.studentDocket;
		this.studentFirstName = builder.studentFirstName;
		this.studentLastName = builder.studentLastName;
		this.courseId = builder.courseId;
		this.courseName = builder.courseName;
		this.grade = builder.grade;
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

	public static class Builder {
		private final int studentDocket;
		private final int courseId;
		private final BigDecimal grade;

		private String studentFirstName = "";
		private String studentLastName = "";
		private String courseName = "";

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

		public Grade build() {
			return new Grade(this);
		}

	}
}
