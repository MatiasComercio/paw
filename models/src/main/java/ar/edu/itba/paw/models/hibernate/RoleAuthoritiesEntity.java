package ar.edu.itba.paw.models.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
//@Table(name = "role_authorities", schema = "public", catalog = "paw")
//@IdClass(RoleAuthoritiesEntityPK.class)
public class RoleAuthoritiesEntity {
	private String role;
	private String authority;
	private RoleEntity roleByRole;
	private AuthorityEntity authorityByAuthority;

	@Id
	@Column(name = "role", nullable = false, length = 50)
	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Id
	@Column(name = "authority", nullable = false, length = 50)
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

		final RoleAuthoritiesEntity that = (RoleAuthoritiesEntity) o;

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

	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "role", nullable = false)
	public RoleEntity getRoleByRole() {
		return roleByRole;
	}

	public void setRoleByRole(final RoleEntity roleByRole) {
		this.roleByRole = roleByRole;
	}

	@ManyToOne
	@JoinColumn(name = "authority", referencedColumnName = "authority", nullable = false)
	public AuthorityEntity getAuthorityByAuthority() {
		return authorityByAuthority;
	}

	public void setAuthorityByAuthority(final AuthorityEntity authorityByAuthority) {
		this.authorityByAuthority = authorityByAuthority;
	}
}
