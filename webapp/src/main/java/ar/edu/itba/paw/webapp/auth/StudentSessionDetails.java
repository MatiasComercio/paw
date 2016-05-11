package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ar.edu.itba.paw.models.users.User.Genre;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adapter class: Adapts our Student model to be a Spring.User
 */
public class StudentSessionDetails extends User implements UserSessionDetails {
	private Student student;

	private StudentSessionDetails(final Builder builder) {
		super (
				String.valueOf(builder.student.getDni()),
				builder.password,
				builder.enabled,
				builder.accountNonExpired,
				builder.credentialsNonExpired,
				builder.accountNonLocked,
				builder.authorities
		);

		this.student = builder.student;
	}

	public int getDocket() {
		return student.getDocket();
	}

	public List<Grade> getGrades() {
		return student.getGrades();
	}

	@Override
	public int getDni() {
		return student.getDni();
	}

	@Override
	public int getId() {
		return getDocket();
	}

	@Override
	public String getFirstName() {
		return student.getFirstName();
	}

	public LocalDate getBirthday() {
		return student.getBirthday();
	}

	public Address getAddress() {
		return student.getAddress();
	}

	@Override
	public String getFullName() {
		return student.getFullName();
	}

	@Override
	public boolean hasAuthority(final String authority) {
		final StringBuilder builder =  new StringBuilder("ROLE_");
		return getAuthorities().contains(new SimpleGrantedAuthority(builder.append(authority).toString()));
	}

	public Genre getGenre() {
		return student.getGenre();
	}

	@Override
	public String getLastName() {
		return student.getLastName();
	}

	public String getEmail() {
		return student.getEmail();
	}

	public static class Builder {
		private final Set<GrantedAuthority> authorities;
		private final Student.Builder studentBuilder;

		/* For accessing Student Model fields */
		private Student student;

		/* For accessing Spring.User model fields */
		private String password = "pass";
		private boolean enabled = true;
		private boolean accountNonExpired = true;
		private boolean credentialsNonExpired = true;
		private boolean accountNonLocked = true;

		public Builder(final int docket, final int dni) {
			authorities = new HashSet<>();
			studentBuilder = new Student.Builder(docket, dni);
		}

		/* Own User builder fields */
		public Builder addGrade(final Grade grade) {
			studentBuilder.addGrade(grade);
			return this;
		}

		public Builder email(final String email) {
			studentBuilder.email(email);
			return this;
		}

		public Builder lastName(final String lastName) {
			studentBuilder.lastName(lastName);
			return this;
		}

		public Builder firstName(final String firstName) {
			studentBuilder.firstName(firstName);
			return this;
		}

		public Builder addGrades(final List<Grade> grades) {
			studentBuilder.addGrades(grades);
			return this;
		}

		public Builder address(final Address address) {
			studentBuilder.address(address);
			return this;
		}

		public Builder genre(final Genre genre) {
			studentBuilder.genre(genre);
			return this;
		}

		public Builder birthday(final LocalDate birthday) {
			studentBuilder.birthday(birthday);
			return this;
		}

		/* Spring.User builder fields */
		public Builder password(final String password) {
			this.password = password;
			return this;
		}

		public Builder enabled(final boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Builder accountNonExpired(final boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
			return this;
		}

		public Builder credentialsNonExpired(final boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
			return this;
		}

		public Builder accountNonLocked(final boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
			return this;
		}

		public Builder authority(final GrantedAuthority authority) {
			this.authorities.add(authority);
			return this;
		}

		public Builder authorities(final Collection <GrantedAuthority> authorities) {
			this.authorities.addAll(authorities);
			return this;
		}

		public StudentSessionDetails build() {
			student = studentBuilder.build();
			return new StudentSessionDetails(this);
		}
	}
}
