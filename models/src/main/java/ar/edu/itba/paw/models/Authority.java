package ar.edu.itba.paw.models;

public class Authority {
	private final String authority;
	private final String roleAuthority;

	public Authority(final String authority) {
		this.authority = authority;
		this.roleAuthority = "ROLE_" + authority;
	}

	public String getAuthority() {
		return authority;
	}

	public String getRoleAuthority() {
		return roleAuthority;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Authority)) return false;

		final Authority authority1 = (Authority) o;

		return authority != null ? authority.equals(authority1.authority) : authority1.authority == null;

	}

	@Override
	public int hashCode() {
		return authority != null ? authority.hashCode() : 0;
	}

	@Override
	public String toString() {
		return authority;
	}
}
