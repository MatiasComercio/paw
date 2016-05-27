package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "role")
public class RoleClass {

	@Id
	@Enumerated
	private Role role;

	@OneToMany
	@JoinColumn(name = "authority")
	private Collection<Authority> authorities;

	protected RoleClass() {
		// Just for Hibernate
	}

	public RoleClass(final Role role, final Collection<Authority> authorities) {
		this.role = role;
		this.authorities = authorities;
	}

	public Role getRole() {
		return role;
	}

	public Collection<Authority> getAuthorities() {
		return Collections.unmodifiableCollection(authorities);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof RoleClass)) return false;

		final RoleClass roleClass = (RoleClass) o;

		return role != null ? role.equals(roleClass.role) : roleClass.role == null;

	}

	@Override
	public int hashCode() {
		return role != null ? role.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "RoleClass{" +
				"role=" + role +
				'}';
	}
}
