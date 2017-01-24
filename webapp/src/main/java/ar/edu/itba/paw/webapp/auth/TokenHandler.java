package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.userdetails.UserDetails;

interface TokenHandler {
  String createToken(UserDetails user);

  String getUsername(String token);
}
