package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserSessionDetails extends UserDetails {
	int getDni();
	int getId();
	String getPassword();
	String getFirstName();
	String getLastName();
	String getFullName();
	boolean hasAuthority(final String authority);
}
