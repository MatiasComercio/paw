package ar.edu.itba.paw.models;

public class Authority {
	private final String authority;

	public Authority(final String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	@Override
	public String toString() {
		return authority;
	}
}
