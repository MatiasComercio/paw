package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class LoggedUser {

	public static int getDni() {
		return Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}

	public static Collection<? extends GrantedAuthority> getAuthorities() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	}
}
