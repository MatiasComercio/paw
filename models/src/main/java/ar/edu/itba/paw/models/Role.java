package ar.edu.itba.paw.models;

public enum Role {
	STUDENT("STUDENT"),
	ADMIN("ADMIN");

	private String roleString;
	private String originalString;

	Role(final String string) {
		this.roleString = "ROLE_" + string;
		this.originalString = string;
	}

	@Override
	public String toString() {
		return roleString;
	}

	public String originalString() {
		return originalString;
	}
}
