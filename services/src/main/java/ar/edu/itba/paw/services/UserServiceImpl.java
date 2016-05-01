package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private StudentService studentService;

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

}
