package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;

public interface UserService {


	/* +++xtest */
	/**
	 * Gets all the data associated with the user that has the
	 * given dni.
	 * @param dni The user's dni.
	 * @return The user that has the given dni; null if no user was found.
	 */
	User getByDni(final String dni);


	/**
	 * Gets all the roles the user with the given dni has associated.
	 * @param dni The user's dni as a String
	 * @return The user's roles; null if no user was found with the given dni.
	 */
//	List<Role> getRoles(final String dni);

	/**
	 * +++xdocument
	 * @param user
	 * @return
     */
	Result create(final User user);

	/**
	 * Delete a user with the corresponding id
	 * @param id The user's id
	 * @return OK if the user was deleted.
     */
	Result delete(final Integer id);
}
