package ar.edu.itba.paw.models.users;

import java.time.LocalDate;

/* +++xcheck: Should this be an abstract class? */
public abstract class User {
	private final int dni;

	private String firstName;
	private String lastName;
	private Genre genre;
	private LocalDate birthday;
	private String email;
//	private final Address address; +++x TODO: have to implement this class afterwards

	protected User(final Builder builder) {
		this.dni = builder.dni;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.genre = builder.genre;
		this.birthday = builder.birthday;
		this.email = builder.createEmail();
	}

	private int getDni() {
		return dni;
	}

	private String getFirstName() {
		return firstName;
	}

	private String getLastName() {
		return lastName;
	}

	private Genre getGenre() {
		return genre;
	}

	private LocalDate getBirthday() {
		return birthday;
	}

	private String getEmail() {
		return email;
	}

	protected static abstract class Builder<V extends User, T extends Builder<V,T>> {
		private static final String EMAIL_DOMAIN = "@bait.edu.ar";

		private final int dni;

		private String firstName = "";
		private String lastName = "";
		private Genre genre = null;
		private LocalDate birthday = null;

		public Builder(final int dni) {
			/* +++xvalidate */
			this.dni = dni;
		}

		public Builder firstName(final String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(final String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder genre(final Genre genre) {
			this.genre = genre;
			return this;
		}

		public Builder birthday(final LocalDate birthday) {
			this.birthday = birthday;
			return this;
		}

		/* Each subclass should implement how a user should be build */
		public abstract V build();


		private String createEmail() {
			if (firstName == null) {
				return "student" + this.dni + EMAIL_DOMAIN;
			}

			String email = null;
			boolean found = false;
			for (int i = 1; i < firstName.length() && !found ; i++) {
				email =  firstName.substring(0, i) + lastName + EMAIL_DOMAIN;
				if (!exists(email)) {
					found = true;
				}
			}
			/* This is in case all email trials failed */
			/* this, for sure, does not exists as includes the dni, which is unique */
			if (email == null) {
				email = firstName.charAt(0) + dni + lastName + EMAIL_DOMAIN;
			}
			return email;
		}

		/* ++x TODO: implement real email comparator */
		private boolean exists(final String email) {
			return false;
		}
	}

	public enum Genre {
		MALE("male"),
		FEMALE("female");

		private final String genre;

		Genre(final String genre) {
			this.genre = genre;
		}

		@Override
		public String toString() {
			return genre;
		}
	}
}
