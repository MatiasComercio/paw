package ar.edu.itba.paw.models.users;

import ar.edu.itba.paw.models.Grade;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "student")
@DiscriminatorValue("STUDENT")
@AttributeOverride(name = "dni", column = @Column(name = "dni", unique = true))
public class Student extends User {
	// +++xcheck: +++xquestion
	/*
	What I wanted to do is that a subclass would have another field as a primary key
	regarding the superclass, but after I rethought the problem,
	I guess Hibernate was right about don't allow me to do it.
	I think if I'd do this way, I would be against the concept of inheritance.
	So I better redesigned my database.
	 */
	@Column(unique = true, updatable = false)
	private int docket;

	@OneToMany
	private List<Grade> grades;

	private Student(final Builder builder) {
		super(builder);
		this.docket = builder.docket;
		this.grades = builder.grades;
	}

	protected Student() {
		// just for Hibernate
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

		return docket == student.docket;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + docket;
		return result;
	}

	@Override
	public String toString() {
		return "Student{" +
				"docket=" + docket +
				"} " + super.toString();
	}

	public static class Builder extends User.Builder<Student, Builder> {

		private int docket;
		private List<Grade> grades;

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
