package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;

import java.util.List;

public interface AdminService {

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
     * @param dni the dni of the administrator
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
     * @param admin The updated admin
     */
    void update(Admin admin);

    /**
     * Delete the admin that matches the given dni.
     * @param dni The admin's dni
     */
    void deleteAdmin(int dni);

    /**
     * Disables inscription authority for Students
     */
    void disableInscriptions();

    /**
     * Enables inscription authority for Students
     */
    void enableInscriptions();

    /**
     * @return A boolean indicating whether the inscriptions are enabled
     */
    boolean isInscriptionEnabled();

    /**
     * Reset a user's password to it's default value;
     * @param dni the user's dni
     * @return OK if the password was reset;
     * 		ERROR_DNI_OUT_OF_BOUNDS if dni is out of the allowed limits;
     *		INVALID_INPUT_PARAMETERS if the provided dni doesn't match to a user;
     *		else ERROR_UNKNOWN in other case;
     */
    boolean resetPassword(int dni);

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
