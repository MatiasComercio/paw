package ar.edu.itba.paw.models;

public enum Role {
	STUDENT("ROLE_STUDENT"), ADMIN("ROLE_ADMIN");

	private String string;

	Role(final String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}
}
