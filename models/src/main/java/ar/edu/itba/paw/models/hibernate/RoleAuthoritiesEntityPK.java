package ar.edu.itba.paw.models.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class RoleAuthoritiesEntityPK implements Serializable {
	private String role;
	private String authority;

	@Column(name = "role", nullable = false, length = 50)
	@Id
	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Column(name = "authority", nullable = false, length = 50)
	@Id
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final RoleAuthoritiesEntityPK that = (RoleAuthoritiesEntityPK) o;

		if (role != null ? !role.equals(that.role) : that.role != null) return false;
		if (authority != null ? !authority.equals(that.authority) : that.authority != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = role != null ? role.hashCode() : 0;
		result = 31 * result + (authority != null ? authority.hashCode() : 0);
		return result;
	}
}
