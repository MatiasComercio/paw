package ar.edu.itba.paw.models.users;



import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Authority;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.RoleClass;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.InheritanceType.JOINED;

@Entity
@Table(name = "users")
@Inheritance(strategy=JOINED)
@DiscriminatorColumn(name="role")
public abstract class User {
	@Id
	@Column(name = "dni", nullable = false)
	private int dni;

	@Basic
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Basic
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@Enumerated(value = EnumType.STRING)
	private Genre genre;

	@Basic
	@Column(name = "birthday")
	private LocalDate birthday;

	@Basic
	@Column(name = "email", nullable = false, length = 100, unique = true)
	private String email;

	@OneToOne
	@JoinColumn(name = "dni")
	private Address address;

	@Basic
	@Column(name = "password", length = 100)
	private String password;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role", referencedColumnName = "role", nullable = false)
	private RoleClass role;

	protected User() {
		// Just for Hibernate, we love you too!
	}

	protected User(final Builder builder) {
		this.dni = builder.dni;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.genre = builder.genre;
		this.birthday = builder.birthday;
		this.email = builder.email;
		this.address = builder.address;
		this.password = builder.password;
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

	public Genre getGenre() {
		return genre;
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

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role == null ? null : role.getRole();
	}

	public Collection<Authority> getAuthorities() {
		return role == null ? Collections.EMPTY_SET : role.getAuthorities();
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

	public static abstract class Builder<V extends User, T extends Builder<V,T>> {
		private int dni;
		private T thisBuilder;

		private String firstName = null;
		private String lastName = null;
		private Genre genre = Genre.N;
		private LocalDate birthday = null;
		private String email = null;
		private Address address = null;
		private String password = null;

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

		public T password(final String password) {
			if (password != null) {
				this.password = password;
			}
			return thisBuilder;
		}
	}

	public enum Genre {
		M("Male"),
		F("Female"),
		N("");
		/* Every time we add a value to the Enum, we have to add the map
		a lowerCase representation of the toString of the Genre
		 */
		private static final Map<String, Genre> map = new HashMap<>();
		static {
			map.put("male", M);
			map.put("female", F);
			map.put("", N);
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

		public String getString() {
			return genre;
		}

		@Override
		public String toString() {
			return genre;
		}
	}
}
