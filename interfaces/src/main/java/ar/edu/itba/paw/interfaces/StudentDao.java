package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Student;

public interface StudentDao {

	/**
	 * Gets the student with the given docket.
	 * If no student exists with that username, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getByDocket(final int docket);
}
