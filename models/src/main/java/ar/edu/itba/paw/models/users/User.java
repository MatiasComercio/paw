package ar.edu.itba.paw.models.users;



import java.time.LocalDate;

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
		String email = builder.email == null ? builder.createEmail() : builder.email;
		this.email = email.toLowerCase();
	}

	public int getDni() {
		return dni;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getGenre() {
		return genre == null ? "" : genre.toString();
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email;
	}

	protected static abstract class Builder<V extends User, T extends Builder<V,T>> {
		private static final String EMAIL_DOMAIN = "@bait.edu.ar";

		private final int dni;
		private final T thisBuilder;

		private String firstName = null;
		private String lastName = null;
		private Genre genre = null;
		private LocalDate birthday = null;
		private String email = null;

		public Builder(final int dni) {
			/* +++xvalidate */
			this.dni = dni;
			this.thisBuilder = thisBuilder();
		}

		/* Each subclass should implement how a user should be build */
		public abstract V build();

		/* Each subclass should implement this method to return it's own builder */
		public abstract T thisBuilder();

		public T firstName(final String firstName) {
			if (firstName != null) {
				this.firstName = firstName;
			}
			return thisBuilder;
		}

		public T lastName(final String lastName) {
			if (lastName != null) {
				this.lastName = lastName;
			}
			return thisBuilder;
		}

		public T genre(final Genre genre) {
			if (genre != null) {
				this.genre = genre;
			}
			return thisBuilder;
		}

		public T birthday(final LocalDate birthday) {
			if (birthday != null) {
				this.birthday = birthday;
			}
			return thisBuilder;
		}

		public T email(final String email) {
			if (email != null) {
				this.email = email;
			}
			return thisBuilder;
		}

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
		M("Male"),
		F("Female");

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
