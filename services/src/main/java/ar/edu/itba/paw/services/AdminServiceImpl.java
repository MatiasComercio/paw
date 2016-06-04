package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    @Transactional
    @Override
    public boolean create(final Admin admin) {
        admin.setRole(Role.ADMIN);
        if (admin.getEmail() == null || Objects.equals(admin.getEmail(), "")) {
            admin.setEmail(userService.createEmail(admin));
        }
        return adminDao.create(admin);
    }

    @Transactional
    @Override
    public boolean update(final int dni, final Admin admin) {
        return adminDao.update(admin);
    }

    @Transactional
    @Override
    public boolean deleteAdmin(final int dni) {
        return adminDao.delete(dni);
    }

    @Transactional
    @Override
    public List<Admin> getByFilter(final AdminFilter adminFilter) {
        return adminDao.getByFilter(adminFilter);
    }

    @Transactional
    @Override
    public Admin getByDni(final int dni) {
        return adminDao.getByDni(dni);
    }

    @Transactional
    @Override
    public boolean disableInscriptions() {
        return adminDao.disableInscriptions();
//        final boolean done = adminDao.disableAddInscriptions();
//
//        if(!done) {
//            return false;
//        }
//        adminDao.disableDeleteInscriptions();
    }

    @Transactional
    @Override
    public boolean enableInscriptions() {
        return adminDao.enableInscriptions();
//        // +++ ximprove: this should be only one method.
//        boolean enableAddResult = adminDao.enableAddInscriptions();
//        boolean enableDeleteResult = adminDao.enableDeleteInscriptions();
//
//        if(enableAddResult.equals(Result.ERROR_UNKNOWN) || enableDeleteResult.equals(Result.ERROR_UNKNOWN)){
//            return false;
//        }
//
////        if(enableAddResult.equals(Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS) &&
////                enableDeleteResult.equals(Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS)){
////            return Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS;
////        }
//
////        return Result.OK;
//        return true;
    }

    @Transactional
    @Override
    public boolean isInscriptionEnabled() {
        return adminDao.isInscriptionEnabled();

    }

    @Override
    public boolean resetPassword(final int dni) {
        return userService.resetPassword(dni);
    }

    @Override
    public List<Procedure> getAllProcedures() {
        return adminDao.getAllProcedures();
    }

    @Override
    public boolean answerProcedure(final Procedure procedure) {
        return adminDao.answerProcedure(procedure);
    }
}
