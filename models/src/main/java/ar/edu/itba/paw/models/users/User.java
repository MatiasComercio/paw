package ar.edu.itba.paw.models.users;



import ar.edu.itba.paw.models.Address;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class User {
	private final int dni;
	private final String firstName;
	private final String lastName;
	private final Genre genre;
	private final LocalDate birthday;
	private final String email;
	private final Address address;

	protected User(final Builder builder) {
		this.dni = builder.dni;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.genre = builder.genre;
		this.birthday = builder.birthday;
		this.email = builder.email;
		this.address = builder.address;
	}

	public int getDni() {
		return dni;
	}

	public String getFirstName() {
		return firstName == null ? "" : firstName;
	}

	public String getLastName() {
		return lastName == null ? "" : lastName;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public String getGenre() {
		return genre == null ? "" : genre.toString();
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email == null ? "" : email;
	}

	/**
	 * Address will not be null if the Controller requests all the user's information.
	 *
	 * @return The user's address, if any; null otherwise.
	 */
	public Address getAddress() {
		return address;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;

		final User user = (User) o;

		if (dni != user.dni) return false;
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
		if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
		if (genre != user.genre) return false;
		if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
		if (email != null ? !email.equals(user.email) : user.email != null) return false;
		return address != null ? address.equals(user.address) : user.address == null;

	}

	@Override
	public int hashCode() {
		int result = dni;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (genre != null ? genre.hashCode() : 0);
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"dni=" + dni +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", genre=" + genre +
				", birthday=" + birthday +
				", email='" + email + '\'' +
				'}';
	}

	public static abstract class Builder<V extends User, T extends Builder<V,T>> {
		private int dni;
		private T thisBuilder;

		private String firstName = null;
		private String lastName = null;
		private Genre genre = null;
		private LocalDate birthday = null;
		private String email = null;
		private Address address = null;

		public Builder(final int dni) {
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

		public T address(final Address address) {
			if (address != null) {
				this.address = address;
			}
			return thisBuilder;
		}
	}

	public enum Genre {
		M("Male"),
		F("Female");
		/* Every time we add a value to the Enum, we have to add the map
		a lowerCase representation of the toString of the Genre
		 */
		private static final Map<String, Genre> map = new HashMap<>();
		static {
			map.put("male", M);
			map.put("female", F);
		}

		private final String genre;

		public static String getGenre(final String genre) {
			if(genre == null) {
				return null;
			}
			final String filterLowerCase = genre.toLowerCase();

			Genre returnGenre =  map.get(filterLowerCase);

			if(returnGenre != null) {
				return returnGenre.name();
			}
			return null;
		}

		Genre(final String genre) {
			this.genre = genre;
		}

		@Override
		public String toString() {
			return genre;
		}
	}
}
