package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;

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
     * @return 	OK if the dni was deleted;
     * 		ERROR_UNKNOWN else;
     */
    Result deleteAdmin(Integer dni);

    /**
     * Disables inscription authority for Students
     * @return The Result code of the operation
     */
    Result disableAddInscriptions();

    //Result disableDeleteInscriptions();

}
