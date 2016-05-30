package ar.edu.itba.paw.models.users;

import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.Role;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student extends User {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_docket_seq")
	@SequenceGenerator(sequenceName = "student_docket_seq", name = "student_docket_seq", allocationSize = 1)
	@Column(unique = true, insertable = false, updatable = false)
	private int docket;

	// TODO +++xcheck if grades are being delete on cascade
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "docket", referencedColumnName = "docket")
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
			super(dni, Role.STUDENT);
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
