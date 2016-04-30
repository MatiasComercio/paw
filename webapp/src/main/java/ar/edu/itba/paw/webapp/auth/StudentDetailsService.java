package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;

public class StudentDetailsService implements UserDetailsService {

	@Autowired
	private StudentService studentService;

	@Override
	public UserDetails loadUserByUsername(final String dni) throws UsernameNotFoundException {

		final Student user = userService.getByDni(dni); /* our own User impl */
		if (user != null) {
			final Collection<GrantedAuthority> authorities = new HashSet<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); /* +++xchange: estamos cableandole el rol; esto deberiamos levantarlo tambien de los datos del usuario de la base de datos */

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true , true, true, authorities);
		}

		throw new UsernameNotFoundException("No user found by " + username);
	}
}
