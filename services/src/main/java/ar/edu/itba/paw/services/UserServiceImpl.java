package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
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

		if (roles == null) {
			return null;
		}

//		if (roles.contains(Role.ADMIN)) {
//			/* loadAdmin data
//			* return;
//			* */
//		}

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
//					.genre(ts.getGenre())
					.address(ts.getAddress())
//					.roles(roles); +++xdebug
					.role(Role.STUDENT);
			return sb.build();
		}

		return null;
	}

}
