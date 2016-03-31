package ar.edu.itba.paw.models.users;

/* +++x TODO: should improve this class. Extend or sth from User. A student 'is-a' user --> inheritance */
public class Student extends User {
	private final int docket;

	private Student(final Builder builder) {
		super(builder);
		this.docket = builder.docket;
	}

	private int getDocket() {
		return docket;
	}

	public static class Builder extends User.Builder<Builder> {
		private final int docket;

		public Builder(final int docket, final int dni) {
			super(dni, firstName, lastName, genre, birthday);
			this.docket = docket;
		}

		public Student build() {
			return new Student(this);
		}
	}
}
