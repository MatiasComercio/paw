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
    public Result create(final Admin admin) {
        admin.setRole(Role.ADMIN);
        if (admin.getEmail() == null || Objects.equals(admin.getEmail(), "")) {
            admin.setEmail(userService.createEmail(admin));
        }
        return adminDao.create(admin);
    }

    @Transactional
    @Override
    public Result update(final int dni, final Admin admin) {
        return adminDao.update(admin);
    }

    @Transactional
    @Override
    public Result deleteAdmin(final int dni) {
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
    public Result disableInscriptions() {
        Result result = adminDao.disableAddInscriptions();
        if(!result.equals(Result.OK)){
            return result;
        }
        return adminDao.disableDeleteInscriptions();
    }

    @Transactional
    @Override
    public Result enableInscriptions() {
        // +++ ximprove: this should be only one method.
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

    @Transactional
    @Override
    public boolean isInscriptionEnabled() {
        return adminDao.isInscriptionEnabled();

    }

    @Override
    public Result resetPassword(final int dni) {
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
