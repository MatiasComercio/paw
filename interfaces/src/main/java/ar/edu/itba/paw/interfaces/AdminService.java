package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.users.Admin;

import java.util.List;

public interface AdminService {

    /**
     * Get all the existing administrators with their info
     * @return  A list with the administrators;
     *          in case there aren't any existant admins,
     *          the list will be empty
     */
    List<Admin> getAllAdmins();
}
