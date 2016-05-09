package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Role;
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

    @Override /*+++xtodo: @Gonza improve: you are not validating if the userService creates the admin */
    public Result create(Admin admin) {
//        userService.create(admin);
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

    @Override
    public Result disableInscriptions() {
        Result result = adminDao.disableAddInscriptions();
        if(!result.equals(Result.OK)){
            return result;
        }
        return adminDao.disableDeleteInscriptions();
    }

    @Override
    public Result enableInscriptions() {
        Result enableAddResult = adminDao.enableAddInscriptions();
        Result enableDeleteResult = adminDao.enableDeleteInscriptions();

        if(enableAddResult.equals(Result.ERROR_UNKNOWN) || enableDeleteResult.equals(Result.ERROR_UNKNOWN)){
            return Result.ERROR_UNKNOWN;
        }

        if(enableAddResult.equals(Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS) &&
                enableDeleteResult.equals(Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS)){
            return Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS;
        }

        return Result.OK;
    }

    @Override
    public boolean isInscriptionEnabled() {
        return adminDao.isInscriptionEnabled();

    }

    @Override
    public Result resetPassword(final Integer dni) {
        if(dni <= 0) {
            return Result.ERROR_DNI_OUT_OF_BOUNDS;
        }
        return userService.resetPassword(dni);
    }
}
