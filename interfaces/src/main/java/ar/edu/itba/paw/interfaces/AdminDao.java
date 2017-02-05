package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;

import java.util.List;

public interface AdminDao {

    /**
     * Get all the existing administrators with their info
     * @return  A list with the administrators;
     *          in case there aren't any existant admins,
     *          the list will be empty
     */
    List<Admin> getAllAdmins();

    /**
     *
     * @param admin The admin to be persisted in the database.
     */
    void create(Admin admin);

    /**
     * Gets all the data associated with the user that has the
     * given dni.
     * @param dni the dni of the admin
     * @return The admin that has the given dni; null if no admin was found.
     */
    Admin getByDni(int dni);

    /**
     * Gets the admins that comply to a list of filters
     * @param adminFilter The list of filters to apply
     * @return the list of admins that match the list of filters. If no student matches the filters, it returns
     * an empty list.
     */
    List<Admin> getByFilter(AdminFilter adminFilter);

    /**
     * Delete the admin that matches the given dni.
     * @param dni The admin's dni
     */
    void delete(int dni);

    /**
     * Enable the student's authority to add and delete inscriptions
     */
    void enableInscriptions();

    /**
     * Disable the student's authority to add and delete inscriptions
     */
    void disableInscriptions();

    /**
     * @return A boolean indicating whether the inscriptions are enabled
     */
    boolean isInscriptionEnabled();

	/**
     * Updates the given admin, if possible
     * @param admin the admin to be updated, containing the updated values
     */
    void update(final Admin admin);

    /**
     * Get all the procedures, despite their state
     * @return all the existing procedures
     */
    List<Procedure> getAllProcedures();

    /**
     * Answer an existing procedure
     * @param procedure the answer to a procedure
     */
    void answerProcedure(Procedure procedure);
}
