package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.users.User;

public interface UserService {
	/**
	 * Registers a new user
	 * @param username The user's username
	 * @param password The user's password
	 * @return The registered user
	 */
	User register(String username, String password);

	/**
	 * Gets a user with the given username.
	 * If no user exists with that username, null is returned.
	 * @param username The user's username
	 * @return The user with the given username, if exists; null otherwise.
	 */
	User getByUsername(String username);
}

