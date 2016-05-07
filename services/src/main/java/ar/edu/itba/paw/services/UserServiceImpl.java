package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

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
	public Result changePassword(final int dni, final String prevPassword, final String newPassword, final String repeatNewPassword) {
		if (!newPassword.equals(repeatNewPassword)) {
			return Result.PASSWORDS_DO_NOT_MATCH;
		}
		if(dni < 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.changePassword(dni, prevPassword, newPassword);
	}

	@Override
	public Result resetPassword(final int dni) {
		if(dni <= 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.resetPassword(dni);
	}

	@Override
	public Result update(final int dni, final User user) {
		if(dni <= 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.update(dni, user);
	}

	@Override
	public Result delete(final int dni) {
		if(dni <= 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.delete(dni);
	}

}
