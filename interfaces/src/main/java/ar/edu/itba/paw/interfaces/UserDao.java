package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;

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

	/**
	 * +++xdocument
	 * @param user
	 * @return
     */
	Result create(User user);

	/**
	 * Delete a user with the corresponding id
	 * @param dni The user's dni
	 * @return OK if the user was deleted.
	 */
	Result delete(final Integer dni);

	/**
	 * Change the password of a given user
	 * @param dni The user's dni.
	 * @param prevPassword The user's previous password
	 * @param newPassword The user's new password
     * @return 	OK if the password could be changed;
	 * 			INVALID_INPUT_PARAMETERS if the DNI or the password are incorrect;
	 * 			else ERROR_UNKNOWN for an unknown error;
     */
	Result changePassword(int dni, String prevPassword, String newPassword);

	/**
	 * Update the user
	 * @param dni the user's dni
	 * @param user the new user's details
	 * @return OK if the user's data was changed correctly
	 * 		+++xdocument
	 */
	Result update(Integer dni, User user);
}
