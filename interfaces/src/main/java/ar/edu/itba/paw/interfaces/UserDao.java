package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;

import java.util.List;

public interface UserDao {

	/**
	 * Gets all the data associated with the user that has the
	 * given dni.
	 * @param dni The user's dni.
	 * @return The user that has the given dni; null if no user was found.
	 */
//	User getByDni(final int dni);


	/* +++xtest */
	/**
	 * Gets all the roles the user with the given dni has associated.
	 * @param dni The user's dni
	 * @return The user's roles; null if no user was found with the given dni.
	 */
	List<Role> getRoles(final int dni);
}
