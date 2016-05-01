package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	/* +++xtodo */
	/*@Autowired
	private AdminService adminService;*/

	@Override
	public UserDetails loadUserByUsername(final String dni) throws UsernameNotFoundException {

		final User user = userService.getByDni(dni); /* our own User impl */

		if (user != null) {
			final UserDetails userDetails = loadUserDetails(user);

			if (userDetails == null) {
				throw new IllegalStateException();
			}

			return userDetails;
		}

		throw new UsernameNotFoundException("No user found by " + dni);
	}

	private UserDetails loadUserDetails(final User user) {
		/* From top to bottom role (greatest privilege to lowest privilege) */
		/* +++xtodo */
/*		if (user.hasRole(ADMIN)) {
			return loadAdmin(user);
		}*/

		if (user instanceof Student) {
			return loadStudent((Student) user);
		}

		return null;
	}

	private UserDetails loadStudent(final Student student) {
		final StudentDetails.Builder studentDetailsBuilder =
				new StudentDetails.Builder(student.getDocket(), student.getDni());
		studentDetailsBuilder
				.firstName(student.getFirstName())
				.lastName(student.getLastName())
				.email(student.getEmail());
		for (Role role : student.getRoles()) {
			studentDetailsBuilder.authority(new SimpleGrantedAuthority(role.toString()));
		}
		return studentDetailsBuilder.build();
	}
}
