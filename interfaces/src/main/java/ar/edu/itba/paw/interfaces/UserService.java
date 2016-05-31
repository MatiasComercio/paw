package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;

import java.util.List;

public interface UserService {

	/**
	 * Gets all the roles the user with the given dni has associated.
	 * @param dni The user's dni
	 * @return The user's roles; null if no user was found with the given dni.
	 */
	List<Role> getRole(int dni);

	/* +++xdocument +++xtest */
	String createEmail(final User user);

//
//	/**
//	 * Gets all the data associated with the user that has the
//	 * given dni.
//	 * @param dni The user's dni.
//	 * @return The user that has the given dni; null if no user was found.
//	 */
//	User getByDni(final String dni);


	/**
	 * Gets all the roles the user with the given dni has associated.
	 * @param dni The user's dni as a String
	 * @return The user's roles; null if no user was found with the given dni.
	 */
//	List<Role> getRole(final String dni);

	/**
	 * Delete a user with the corresponding id
	 * @param dni The user's dni
	 * @return OK if the user was deleted.
     */
	Result delete(int dni);

	/**
	 * Change the password for a given user
	 * @param dni of the user
	 * @param prevPassword the previous password of the user
	 * @param newPassword the new password of the user
	 * @param repeatNewPassword the repetition of the new password of the user
     * @return 	OK if the password could be changed;
	 * 			PASSWORDS_DO_NOT_MATCH if newPassword and repeatNewPassword don't match;
	 * 			ERROR_DNI_OUT_OF_BOUNDS if the DNI is out of the allowed limits;
	 * 			INVALID_INPUT_PARAMETERS if the DNI or the password are incorrect;
	 * 			else ERROR_UNKNOWN for an unknown error;
     */
	Result changePassword(int dni, String prevPassword, String newPassword, String repeatNewPassword);

	/**
	 * Reset a user's password to it's default value;
	 * @param dni the user's dni
	 * @return OK if the password was reset;
	 * 		ERROR_DNI_OUT_OF_BOUNDS if dni is out of the allowed limits;
	 *		INVALID_INPUT_PARAMETERS if the provided dni doesn't match to a user;
	 *		else ERROR_UNKNOWN in other case;
     */
	Result resetPassword(int dni);

	/**
	 * Update the user
	 * @param dni the user's dni
	 * @param user the new user's details
     * @return OK if the user's data was changed correctly
	 * 		ERROR_DNI_OUT_OF_BOUNDS if the dni limit was out of bounds;
     */
	Result update(int dni, User user);
}
