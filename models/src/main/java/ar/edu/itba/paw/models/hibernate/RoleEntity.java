package ar.edu.itba.paw.models.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

//@Entity
//@Table(name = "role", schema = "public", catalog = "paw")
public class RoleEntity {
	private String role;
	private Collection<RoleAuthoritiesEntity> roleAuthoritiesByRole;
	private Collection<UsersEntity> usersesByRole;

	@Id
	@Column(name = "role", nullable = false, length = 50)
	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final RoleEntity that = (RoleEntity) o;

		if (role != null ? !role.equals(that.role) : that.role != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return role != null ? role.hashCode() : 0;
	}

	@OneToMany(mappedBy = "roleByRole")
	public Collection<RoleAuthoritiesEntity> getRoleAuthoritiesByRole() {
		return roleAuthoritiesByRole;
	}

	public void setRoleAuthoritiesByRole(final Collection<RoleAuthoritiesEntity> roleAuthoritiesByRole) {
		this.roleAuthoritiesByRole = roleAuthoritiesByRole;
	}

	@OneToMany(mappedBy = "roleByRole")
	public Collection<UsersEntity> getUsersesByRole() {
		return usersesByRole;
	}

	public void setUsersesByRole(final Collection<UsersEntity> usersesByRole) {
		this.usersesByRole = usersesByRole;
	}
}
