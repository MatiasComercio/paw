package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Autowired
    UserService userService;

    @Override
    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    @Override
    public Result create(Admin admin) {
        userService.create(admin);
        Result result = adminDao.create(admin);

        //TODO: add role to roles table which indicates (dni, role)

        return result;
    }

    @Override
    public Admin getByDni(int dni) {
        return adminDao.getByDni(dni);
    }

    @Override
    public List<Admin> getByFilter(AdminFilter adminFilter) {
        return adminDao.getByFilter(adminFilter);
    }

    @Override
    public Result deleteAdmin(Integer dni) {
        if(dni <= 0) {
            return Result.ERROR_DNI_OUT_OF_BOUNDS;
        }
        return adminDao.deleteAdmin(dni);
    }

    @Override
    public Result update(Integer dni, Admin admin) {
        if(dni <= 0) {
            return Result.ERROR_DNI_OUT_OF_BOUNDS;
        }
        return userService.update(dni, admin);
    }
}
