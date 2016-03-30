package ar.edu.itba.paw.models;

import java.util.Set;

public class Student {
	private final int docket;
	private final int dni;

	private Student(final Builder builder) {
		this.docket = builder.docket;
		this.dni = builder.dni;
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
