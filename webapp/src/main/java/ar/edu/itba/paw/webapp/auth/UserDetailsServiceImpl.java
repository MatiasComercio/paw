package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	/* +++xtodo */
	/*@Autowired
	private UserService userService;*/

	@Autowired
	private StudentService studentService;

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

		if (user.hasRole(STUDENT)) {
			return loadStudent(user);
		}

		return null;
	}

	private UserDetails loadStudent(final User user) {
		final int dni = user.getDni();
		final int docket = studentService.getDocket(dni);
		final StudentDetails.Builder studentDetailsBuilder = new StudentDetails.Builder(docket, dni);
		studentDetailsBuilder
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail());
		for (String role : user.getRoles()) {
			studentDetailsBuilder.authority(new SimpleGrantedAuthority(role));
		}
		return studentDetailsBuilder.build();
	}
}
