package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;

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

	/**
	 * Gets all the roles the user with the given dni has associated.
	 * @param dni The user's dni
	 * @return The user's roles; null if no user was found with the given dni.
	 */
	List<Role> getRole(int dni);

	/**
	 * Creates a new user with the given data
	 * @param user the user's data
	 */
	void create(User user, final Role role);

	/**
	 * Delete a user with the corresponding id
	 * @param dni The user's dni
	 * @throws UnsupportedOperationException if the current implementation requires
	 *         that a call to delete on User's subclasses DAO 's should be done instead
	 */
	void delete(int dni);

	/**
	 * Change the password of a given user
	 * @param dni The user's dni.
	 * @param prevPassword The user's previous password. If it is null, then the newPassword will step over the
	 *                     previous password
	 * @param newPassword The user's new password
	 * @return true if the password was changed; else false
	 */
	boolean changePassword(int dni, String prevPassword, String newPassword);

	/**
	 * Update the user
	 * @param dni the user's dni
	 * @param user the new user's details
	 */
	void update(int dni, User user);

	/**
	 * Reset a user's password to it's default value;
	 * @param dni the user's dni
	 * @return true if the password was reset; else false
	 */
	boolean resetPassword(int dni);

	boolean existsEmail(String email);


	boolean userExists(int dni);
}
