package ar.edu.itba.paw.models.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

//@Entity
//@Table(name = "authority")
public class AuthorityEntity {
	private String authority;
	private Collection<RoleAuthoritiesEntity> roleAuthoritiesByAuthority;

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

		final AuthorityEntity that = (AuthorityEntity) o;

		if (authority != null ? !authority.equals(that.authority) : that.authority != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return authority != null ? authority.hashCode() : 0;
	}

	@OneToMany(mappedBy = "authorityByAuthority")
	public Collection<RoleAuthoritiesEntity> getRoleAuthoritiesByAuthority() {
		return roleAuthoritiesByAuthority;
	}

	public void setRoleAuthoritiesByAuthority(final Collection<RoleAuthoritiesEntity> roleAuthoritiesByAuthority) {
		this.roleAuthoritiesByAuthority = roleAuthoritiesByAuthority;
	}
}
