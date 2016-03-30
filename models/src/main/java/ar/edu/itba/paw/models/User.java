package ar.edu.itba.paw.models;

import java.time.LocalDate;

public class User {
	private final int dni;
	private final String firstName;
	private final String lastName;
	private final Genre genre;
	private final LocalDate birthday;
	private final String email;
//	private final Address address; +++x TODO: have to implement this class afterwards

	private User(final Builder builder) {
		this.dni = builder.dni;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.genre = builder.genre;
		this.birthday = builder.birthday;
		this.email = builder.email;
	}

	public static class Builder {
		private final int dni;
		private final String firstName;
		private final String lastName;
		private final Genre genre;
		private final LocalDate birthday;
		private final String email;

		public Builder(final int dni, final String firstName, final String lastName, final Genre genre, final LocalDate birthday) {
			/* +++xvalidate */
			this.dni = dni;
			this.firstName = firstName;
			this.lastName = lastName;
			this.genre = genre;
			this.birthday = birthday;

			this.email = createEmail();
		}


		public User build() {
			return new User(this);
		}


		private String createEmail() {
			String email = null;
			boolean found = false;
			for (int i = 1; i < firstName.length() && !found ; i++) {
				email =  firstName.substring(0, i) + lastName + "@bait.edu.ar";
				if (!exists(email)) {
					found = true;
				}
			}
			/* This is in case all email trials failed */
			/* this, for sure, does not exists as includes the dni, which is unique */
			if (email == null) {
				email = firstName.charAt(0) + dni + lastName + "@bait.edu.ar";
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
