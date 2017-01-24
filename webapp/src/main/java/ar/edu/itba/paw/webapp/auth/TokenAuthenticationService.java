package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.webapp.models.UserLogDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashSet;

@Component
public class TokenAuthenticationService {
  private static final String AUTH_HEADER = "X-AUTH-TOKEN";

  @Autowired
  UserDetailsService userDetailsService;

  private final TokenHandler tokenHandler;


  public TokenAuthenticationService() {
    this.tokenHandler = new JWTTokenHandler();
  }

  Authentication getAuthentication(final HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER);
    Authentication authentication = null;

    if(token != null) {
      final String username = tokenHandler.getUsername(token);

      if(username != null) {
        try {
          final UserDetails user = userDetailsService.loadUserByUsername(username);

          final Collection<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

          authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
        } catch (UsernameNotFoundException e) {
          return null;
        }
      }
    }

    return authentication;
  }

  void addAuthentication(final HttpServletResponse response, final UserDetails userDetails) {
    final String token = tokenHandler.createToken(userDetails);

    response.setHeader(AUTH_HEADER, token);
  }

  Authentication getAuthenticationForLogin(final HttpServletRequest request) {
    try {
      final UserLogDTO user = new ObjectMapper().readValue(request.getInputStream(), UserLogDTO.class);

      final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getDni());

      if(userDetails != null) {
        if (user.getDni().equals(userDetails.getUsername()) && user.getPassword().equals(userDetails.getPassword())) {
          return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }
      }
    } catch (final Exception e) {
      return null;
    }

    return null;
  }
}