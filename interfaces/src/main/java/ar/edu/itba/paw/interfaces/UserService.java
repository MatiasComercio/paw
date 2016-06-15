package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;

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

	/**
	 * Change the password for a given user
	 * @param dni of the user
	 * @param prevPassword the previous password of the user
	 * @param newPassword the new password of the user
	 * @param repeatNewPassword the repetition of the new password of the user
     * @return 	true if the password was changed; else false
	 */
	boolean changePassword(int dni, String prevPassword, String newPassword, String repeatNewPassword);

	/**
	 * Reset a user's password to it's default value;
	 * @param dni the user's dni
	 * @return true if the password was reset; else false
     */
	boolean resetPassword(int dni);

	boolean existsEmail(String email);

}
