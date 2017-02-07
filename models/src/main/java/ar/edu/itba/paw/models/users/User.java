package ar.edu.itba.paw.models.users;


import ar.edu.itba.paw.models.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.InheritanceType.JOINED;

@Entity
@Table(name = "users")
@Inheritance(strategy=JOINED)

public class User implements Serializable {

	public static final String DEFAULT_PASSWORD = "pass";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq_seq")
	@SequenceGenerator(sequenceName = "users_id_seq_seq", name = "users_id_seq_seq", allocationSize = 1)
	@Column(name = "id_seq")
	private Integer id_seq;

	@Column(name = "dni", unique = true, nullable = false, updatable = false)
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
	@Column(name = "email", nullable = false, length = 100, unique = true, updatable = false)
	private String email;

	// Unidirectional Mapping
	@OneToOne(cascade = CascadeType.ALL/*, fetch = FetchType.LAZY*/)
	@JoinColumn(name = "dni", referencedColumnName = "dni", insertable = false, updatable = false)
	private Address address;

	@Basic
	@Column(name = "password", length = 100)
	private String password;

	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "role", nullable = false)
	private RoleClass role;

	@OneToMany(cascade= CascadeType.ALL, mappedBy = "sender")
	private List<Procedure> procedures;

	protected User() {
		// Just for Hibernate, we love you too!
	}

	protected User(final Builder builder) {
		this.id_seq = builder.id_seq;
		this.dni = builder.dni;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.genre = builder.genre;
		this.birthday = builder.birthday;
		this.email = builder.email;
		this.address = builder.address;
		this.password = builder.password;
		this.role = new RoleClass(builder.role, null);
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getFirstName() {
		return firstName == null ? "" : firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName == null ? "" : lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email == null ? "" : email;
	}

	// +++ximprove:
	public void setEmail(final String email) {
		if (email != null) {
			this.email = email;
		}
	}

	/**
	 * Address will not be null if the Controller requests all the user's information.
	 *
	 * @return The user's address, if any; null otherwise.
	 */
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Role getRole() {
		return role == null ? null : role.getRole();
	}

	public void setRole(RoleClass role) {
		this.role = role;
	}

	public RoleClass getRoleClass(){return role;}

	public List<Procedure> getProcedures() {
		return procedures;
	}

	public void setProcedures(List<Procedure> procedures) {
		this.procedures = procedures;
	}

	public void setRole(final Role role) {
		this.role = role == null ? null : new RoleClass(role, null);
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

	public Integer getId_seq() {
		return id_seq;
	}

	public void setId_seq(final Integer id_seq) {
		this.id_seq = id_seq;
	}

	public void resetPassword() {
		this.password = DEFAULT_PASSWORD;
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

		Genre(final String genre) {
			this.genre = genre;
		}

		public static String getGenre(final String genre) {
			if (genre == null) {
				return null;
			}
			final String filterLowerCase = genre.toLowerCase();

			Genre returnGenre = map.get(filterLowerCase);

			if (returnGenre != null) {
				return returnGenre.name();
			}
			return null;
		}

		public String getString() {
			return genre;
		}

		@Override
		public String toString() {
			return genre;
		}
	}

	public static abstract class Builder<V extends User, T extends Builder<V,T>> {
		private final int dni;
		private final Role role;

		private T thisBuilder;
		private Integer id_seq;
		private String firstName = null;
		private String lastName = null;
		private Genre genre = Genre.N;
		private LocalDate birthday = null;
		private String email = null;
		private Address address = null;
		private String password = "pass";

		public Builder(final int dni, final Role role) {
			this.dni = dni;
			this.role = role;
			this.thisBuilder = thisBuilder();
		}

		/* Each subclass should implement how a user should be build */
		public abstract V build();

		/* Each subclass should implement this method to return it's own builder */
		public abstract T thisBuilder();

		public T id_seq(final Integer id_seq) {
			this.id_seq = id_seq;
			return thisBuilder;
		}

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
			if (password != null && !Objects.equals(password, "")) {
				this.password = password;
			}
			return thisBuilder;
		}
	}
}
