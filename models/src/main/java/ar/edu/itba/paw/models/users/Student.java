package ar.edu.itba.paw.models.users;

import ar.edu.itba.paw.models.Grade;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Student extends User {
	private final int docket;
	private final List<Grade> grades;

	private Student(final Builder builder) {
		super(builder);
		this.docket = builder.docket;
		this.grades = builder.grades;
	}

	public int getDocket() {
		return docket;
	}

	public List<Grade> getGrades() {
		return Collections.unmodifiableList(grades);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Student)) return false;
		if (!super.equals(o)) return false;

		final Student student = (Student) o;

		if (docket != student.docket) return false;
		return grades != null ? grades.equals(student.grades) : student.grades == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + docket;
		result = 31 * result + (grades != null ? grades.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Student{" +
				"docket=" + docket +
				"} " + super.toString();
	}

	public static class Builder extends User.Builder<Student, Builder> {

		private final int docket;
		private final List<Grade> grades;

		public Builder(final int docket, final int dni) {
			super(dni);
			this.docket = docket;
			this.grades = new LinkedList<>();
		}

		public Builder addGrade(final Grade grade) {
			if (grade != null) {
				this.grades.add(grade);
			}

			return this;
		}


		public Builder addGrades(final List<Grade> grades) {
			if (grades != null) {
				this.grades.addAll(grades);
			}

			return this;
		}

		@Override
		public Student build() {
			return new Student(this);
		}

		@Override
		public Builder thisBuilder() {
			return this;
		}
	}
}