package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.users.Admin;
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
}
