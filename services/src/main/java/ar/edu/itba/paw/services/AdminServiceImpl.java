package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.AdminFilter;
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
    public void create(final Admin admin) {
        admin.setRole(Role.ADMIN);
	    admin.getAddress().setDni(admin.getDni());
	    admin.setPassword(User.DEFAULT_PASSWORD);
	    if (admin.getEmail() == null || Objects.equals(admin.getEmail(), "")) {
            admin.setEmail(userService.createEmail(admin));
        }
        adminDao.create(admin);
    }

    @Transactional
    @Override
    public void update(final Admin admin) {
        final Admin oldAdmin = getByDni(admin.getDni());

        admin.setId_seq(oldAdmin.getId_seq());
        admin.setPassword(oldAdmin.getPassword());
        admin.setEmail(oldAdmin.getEmail());
        if(oldAdmin.getAddress() != null) {
            admin.getAddress().setId_seq(oldAdmin.getAddress().getId_seq());
        }
        admin.getAddress().setDni(admin.getDni());
        admin.setRole(oldAdmin.getRole());

        adminDao.update(admin);
    }

    @Transactional
    @Override
    public void deleteAdmin(final int dni) {
        adminDao.delete(dni);
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
    public void disableInscriptions() {
        if(isInscriptionEnabled()) {
            adminDao.disableInscriptions();
        }
    }

    @Transactional
    @Override
    public void enableInscriptions() {
        if(!isInscriptionEnabled()) {
            adminDao.enableInscriptions();
        }
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
    public void answerProcedure(final Procedure procedure) {
        adminDao.answerProcedure(procedure);
    }
}
