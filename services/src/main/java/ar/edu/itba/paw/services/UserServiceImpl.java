package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private StudentService studentService;

	@Autowired
	private AdminService adminService;

	@Override
	public List<Role> getRole(final int dni) {
		return userDao.getRole(dni);
	}

//	@Override
//	public User getByDni(final String dniString) {
//		int dni;
//		try {
//			dni = Integer.valueOf(dniString);
//		} catch (final NumberFormatException e) {
//			dni = -1;
//		}
//
//		if (dni <= 0) {
//			return null;
//		}
//
//		List<Role> roles = userDao.getRole(dni);
//
//		if (roles == null) {
//			return null;
//		}
//
//		/* +++xcheck: should User know its subclasses? */
//		if(roles.contains(Role.ADMIN)) {
//			return adminService.getByDni(dni);
//		}
//
//		if (roles.contains(Role.STUDENT)) {
//			return studentService.getByDni(dni);
//		}
//
//		return null;
//	}

	@Override
	public Result changePassword(int dni, String prevPassword, String newPassword, String repeatNewPassword) {
		if (!newPassword.equals(repeatNewPassword)) {
			return Result.PASSWORDS_DO_NOT_MATCH;
		}
		if(dni < 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.changePassword(dni, prevPassword, newPassword);
	}

	@Override
	public Result delete(Integer dni) {
		if(dni <= 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.delete(dni);
	}

}
