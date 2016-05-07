package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;

import java.util.List;

public interface UserDao {



	/**
	 *
	 * Gets all the data associated with the user that has the
	 * given dni.
	 * @param dni The user's dni.
	 * @param builder The user's builder already instantiated with mandatory params.
	 *                Cannot be null.
	 *                This builder will be used to construct the corresponding User instance.
	 * @param <V> User created type
	 * @param <T> User.Builder type
	 * @return The user that has the given dni; null if no user was found.
	 * @throws NullPointerException if builder is null
	 */
	<V extends User, T extends User.Builder<V,T>> V getByDni(int dni, User.Builder<V, T> builder);

	/* +++xtest *//* +++xchange: should return only one role */
	/**
	 * Gets all the roles the user with the given dni has associated.
	 * @param dni The user's dni
	 * @return The user's roles; null if no user was found with the given dni.
	 */
	List<Role> getRole(final int dni);

	/**
	 * +++xdocument
	 * @param user
	 * @return
     */
	Result create(User user, final Role role);

	/**
	 * Delete a user with the corresponding id
	 * @param dni The user's dni
	 * @return OK if the user was deleted.
	 */
	Result delete(int dni);

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
	Result update(int dni, User user);

	/**
	 * Reset a user's password to it's default value;
	 * @param dni the user's dni
	 * @return OK if the password was reset;
	 *		INVALID_INPUT_PARAMETERS if the provided dni doesn't match to a user;
	 *		else ERROR_UNKNOWN in other case;
	 */
	Result resetPassword(int dni);
}
