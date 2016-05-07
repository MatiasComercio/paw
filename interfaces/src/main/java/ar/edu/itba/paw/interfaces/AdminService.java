package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;

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
     * @return  OK if the admin could be created;
     *          +++xcomplete remaining return types
     */
    Result create(Admin admin);

    /**
     * +++xdocument
     * @param dni
     * @return
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
     * @return 	OK if the admin was deleted;
     * 		ERROR_DNI_OUT_OF_BOUNDS if the dni is invalid;
     * 		ERROR_UNKNOWN else;
     */
    Result deleteAdmin(Integer dni);

    /**
     * Update an admin
     * @param dni  The dni of the old admin
     * @param admin The new student
     * @return The Result code of update
     */
    Result update(Integer dni, Admin admin);

    /**
     * Reset a user's password to it's default value;
     * @param dni the user's dni
     * @return OK if the password was reset;
     * 		ERROR_DNI_OUT_OF_BOUNDS if dni is out of the allowed limits;
     *		INVALID_INPUT_PARAMETERS if the provided dni doesn't match to a user;
     *		else ERROR_UNKNOWN in other case;
     */
    Result resetPassword(Integer dni);
}
