package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Authority;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Admin;
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
		if (user instanceof Admin) {
			return loadAdmin((Admin) user);
		}

		if (user instanceof Student) {
			return loadStudent((Student) user);
		}

		return null;
	}

	private UserDetails loadAdmin(final Admin admin) {
		final AdminDetails.Builder adminDetailsBuilder =
				new AdminDetails.Builder(admin.getDni());
		adminDetailsBuilder
				.firstName(admin.getFirstName())
				.lastName(admin.getLastName())
				.email(admin.getEmail())
				.password(admin.getPassword());
		for (Authority authority : admin.getAuthorities()) {
			adminDetailsBuilder.authority(new SimpleGrantedAuthority(authority.toString()));
		}
		return adminDetailsBuilder.build();
	}

	private UserDetails loadStudent(final Student student) {
		final StudentDetails.Builder studentDetailsBuilder =
				new StudentDetails.Builder(student.getDocket(), student.getDni());
		studentDetailsBuilder
				.firstName(student.getFirstName())
				.lastName(student.getLastName())
				.email(student.getEmail())
				.password(student.getPassword());
		for (Authority authority : student.getAuthorities()) {
			studentDetailsBuilder.authority(new SimpleGrantedAuthority(authority.toString()));
		}
		return studentDetailsBuilder.build();
	}

}
