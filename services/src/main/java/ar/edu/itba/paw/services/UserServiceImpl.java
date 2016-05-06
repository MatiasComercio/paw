package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
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

	@Autowired
	private AddressService addressService;

	@Override
	public User getByDni(final String dniString) {
		int dni;
		try {
			dni = Integer.valueOf(dniString);
		} catch (final NumberFormatException e) {
			dni = -1;
		}

		if (dni <= 0) {
			return null;
		}

		List<Role> roles = userDao.getRoles(dni);
		roles.add(Role.ADMIN); /* +++xdebug */

		if (roles == null) {
			return null;
		}

		/* +++xtodo */
//		if (roles.contains(Role.ADMIN)) {
//			/* loadAdmin data
//			* return;
//			* */
//		}

		if(roles.contains(Role.ADMIN)) {
			final Admin ta =  adminService.getByDni(dni);

			if(ta == null) {
				return null;
			}

			final Admin.Builder ab = new Admin.Builder(ta.getDni());

			ab
					.firstName(ta.getFirstName())
					.lastName(ta.getLastName())
					.email(ta.getEmail())
					.birthday(ta.getBirthday())
					.address(ta.getAddress())
					.password(ta.getPassword())
					.roles(roles);

			return ab.build();
		}

		if (roles.contains(Role.STUDENT)) {
			final Student ts =  studentService.getByDni(dni); /* +++xcheck: Gonza */
			if (ts == null) {
				return null;
			}

			final Student.Builder sb = new Student.Builder(ts.getDocket(), ts.getDni());
			sb
					.firstName(ts.getFirstName())
					.lastName(ts.getLastName())
					.email(ts.getEmail())
					.birthday(ts.getBirthday())
//					.genre(ts.getGenre()) +++xcheck
					.address(ts.getAddress())
					.password(ts.getPassword())
					.roles(roles);
			return sb.build();
		}

		return null;
	}

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
	public Result update(Integer dni, User user) {
		if(dni <= 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}

		if(addressService.hasAddress(dni)) {
			addressService.updateAddress(dni, user.getAddress());
		} else {
			addressService.createAddress(dni, user.getAddress());
		}

		/**
		 * +++xfinish
		 */
		return userDao.update(dni, user);
	}

	@Override
	public Result create(User user) {
		return userDao.create(user);
	}

	@Override
	public Result delete(Integer dni) {
		if(dni <= 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.delete(dni);
	}

}
