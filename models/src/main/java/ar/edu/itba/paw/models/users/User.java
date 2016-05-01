package ar.edu.itba.paw.models.users;



import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Role;

import java.time.LocalDate;
import java.util.*;

public abstract class User {
	private final int dni;
	private final String firstName;
	private final String lastName;
	private final Genre genre;
	private final LocalDate birthday;
	private final String email;
	private final Address address;
	private final Collection<Role> roles;

	protected User(final Builder builder) {
		this.dni = builder.dni;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.genre = builder.genre;
		this.birthday = builder.birthday;
		this.email = builder.email;
		this.address = builder.address;
		this.roles = builder.roles;
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

		return dni == user.dni;

	}

	@Override
	public int hashCode() {
		return dni;
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

	public Collection<Role> getRoles() {
		return Collections.unmodifiableCollection(roles);
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
		private Collection<Role> roles = null;

		public Builder(final int dni) {
			this.dni = dni;
			this.thisBuilder = thisBuilder();
			this.roles = new HashSet<>();
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

		public T role(final Role role) {
			if (role != null) {
				this.roles.add(role);
			}
			return thisBuilder;
		}

		public T roles(final Collection<Role> roles) {
			if (roles != null) {
				this.roles.addAll(roles);
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
