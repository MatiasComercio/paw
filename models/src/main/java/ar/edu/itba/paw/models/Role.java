package ar.edu.itba.paw.models;

public enum Role {
	STUDENT("STUDENT"), ADMIN("ADMIN");

	private String string;

	Role(final String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}
}
