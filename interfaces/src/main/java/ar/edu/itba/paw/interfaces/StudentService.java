package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Student;
import ar.edu.itba.paw.models.User;

public interface StudentService {

	/**
	 * Gets the student with the given docket.
	 * If no student exists with that username, null is returned.
	 * @param docket The user's docket
	 * @return The user with the given docket, if exists; null otherwise.
	 */
	Student getByDocket(final int docket);

}
