package ar.edu.itba.paw.models.users;

import ar.edu.itba.paw.models.Grade;

import java.util.LinkedList;
import java.util.List;

public class Student extends User {
	private final int docket;

	private Student(final Builder builder) {
		super(builder);
		this.docket = builder.docket;
	}

	public int getDocket() {
		return docket;
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
				grades.add(grade);
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
