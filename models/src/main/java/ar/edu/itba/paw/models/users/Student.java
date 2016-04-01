package ar.edu.itba.paw.models.users;

public class Student extends User {
	private final int docket;

	private Student(final Builder builder) {
		super(builder);
		this.docket = builder.docket;
	}

	private int getDocket() {
		return docket;
	}

	public static class Builder extends User.Builder<Student, Builder> {

		private final int docket;

		public Builder(final int docket, final int dni) {
			super(dni);
			this.docket = docket;
		}

		@Override
		public Student build() {
			return new Student(this);
		}

	}
}
