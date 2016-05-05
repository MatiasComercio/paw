package ar.edu.itba.paw.models;

public enum Role {
	STUDENT("STUDENT"),
	ADMIN("ADMIN");
/*	ADD_ADMIN       ("ADD_ADMIN"),
	ADD_STUDENT     ("ADD_STUDENT"),
	ADD_COURSE      ("ADD_COURSE"),
	ADD_GRADE       ("ADD_GRADE"),
	ADD_INSCRIPTION ("ADD_INSCRIPTION"),

	EDIT_ADMIN       ("EDIT_ADMIN"),
	EDIT_STUDENT     ("EDIT_STUDENT"),
	EDIT_COURSE      ("EDIT_COURSE"),
	EDIT_GRADE       ("EDIT_GRADE"),
	EDIT_INSCRIPTION ("EDIT_INSCRIPTION"),

	DELETE_ADMIN       ("DELETE_ADMIN"),
	DELETE_STUDENT     ("DELETE_STUDENT"),
	DELETE_COURSE      ("DELETE_COURSE"),
	DELETE_GRADE       ("DELETE_GRADE"),
	DELETE_INSCRIPTION ("DELETE_INSCRIPTION"),

	VIEW_ADMIN       ("VIEW_ADMIN"),
	VIEW_STUDENT     ("VIEW_STUDENT"),
	VIEW_COURSE      ("VIEW_COURSE"),
	VIEW_GRADE       ("VIEW_GRADE"),
	VIEW_INSCRIPTION ("VIEW_INSCRIPTION");*/

	/* Template */
	/*
	ADMIN       ("ADMIN"),
	STUDENT     ("STUDENT"),
	COURSE      ("COURSE"),
	GRADE       ("GRADE"),
	INSCRIPTION ("INSCRIPTION"),
	*/

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
