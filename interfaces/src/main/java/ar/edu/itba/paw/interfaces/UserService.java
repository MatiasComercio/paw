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
	 * Change the password for a given user
	 * @param dni of the user
	 * @param prevPassword the previous password of the user
	 * @param newPassword the new password of the user
	 * @param repeatNewPassword the repetition of the new password of the user
     * @return OK if the password could be changed; // TODO: add Result values here
     */
	Result changePassword(int dni, String prevPassword, String newPassword, String repeatNewPassword);
}
