package ar.edu.itba.paw.models;

import java.util.Set;
/* +++x TODO: should improve this class. Extend or sth from User. A student 'is-a' user --> inheritance */
public class Student {
	private final int docket;
	private final int dni;

	private Student(final Builder builder) {
		this.docket = builder.docket;
		this.dni = builder.dni;
	}

	private int getDocket() {
		return docket;
	}

	private int getDni() {
		return dni;
	}

	public static class Builder {
		private final int docket;
		private final int dni;

		public Builder(final int docket, final int dni) {
			this.docket = docket;
			this.dni = dni;
		}

		public Student build() {
			return new Student(this);
		}
	}
}
