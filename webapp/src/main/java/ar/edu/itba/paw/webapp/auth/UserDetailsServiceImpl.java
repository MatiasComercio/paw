package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.AdminService;
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

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private StudentService studentService;

	@Override
	public UserDetails loadUserByUsername(final String dniString) throws UsernameNotFoundException {

		final int dni;
		try {
			dni = Integer.valueOf(dniString);
		} catch (final NumberFormatException e) {
			throw new UsernameNotFoundException("No user was found with the DNI " + dniString);
		}

		List<Role> roles = userService.getRole(dni);

		if (roles == null || roles.isEmpty()) {
			throw new UsernameNotFoundException("No user was found with the DNI " + dniString);
		}

		UserDetails userDetails = null;
		if(roles.contains(Role.ADMIN)) {
			userDetails = loadAdmin(dni);
		} else if (roles.contains(Role.STUDENT)) {
			userDetails = loadStudent(dni);
		}

		if (userDetails == null) {
			throw new UsernameNotFoundException("No user was found with the DNI " + dniString);
		}

		return userDetails;
	}

	/* +++ximprove */
	private UserDetails loadStudent(final int dni) {
		final Student student = studentService.getByDni(dni);
		if (student == null) {
			return null;
		}

		final StudentDetails.Builder studentDetailsBuilder =
				new StudentDetails.Builder(student.getDocket(), student.getDni());
		studentDetailsBuilder
				.firstName(student.getFirstName())
				.lastName(student.getLastName())
				.email(student.getEmail())
				.password(student.getPassword());
		for (Authority authority : student.getAuthorities()) {
			studentDetailsBuilder.authority(new SimpleGrantedAuthority(authority.getRoleAuthority()));
		}
		return studentDetailsBuilder.build();
	}

	/* +++ximprove */
	private UserDetails loadAdmin(final int dni) {
		final Admin admin = adminService.getByDni(dni);
		if (admin == null) {
			return null;
		}

		final AdminDetails.Builder adminDetailsBuilder =
				new AdminDetails.Builder(admin.getDni());
		adminDetailsBuilder
				.firstName(admin.getFirstName())
				.lastName(admin.getLastName())
				.email(admin.getEmail())
				.password(admin.getPassword());
		for (Authority authority : admin.getAuthorities()) {
			adminDetailsBuilder.authority(new SimpleGrantedAuthority(authority.getRoleAuthority()));
		}
		return adminDetailsBuilder.build();
	}
}
