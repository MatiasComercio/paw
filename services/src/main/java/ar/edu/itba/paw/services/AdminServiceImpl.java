package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.models.users.Admin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public List<Admin> getAllAdmins() {
        return null;
    }
}
