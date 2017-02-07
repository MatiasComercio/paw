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

/**
 * Service which is used by custom-created filters (i.e. {@link StatelessAuthenticationFilter} , {@link StatelessLoginFilter}
 * for handling Token Authentications.
 */
@Component
public class TokenAuthenticationService {
  private static final String AUTH_HEADER = "X-AUTH-TOKEN"; // Header used to retrieve the Authentication Token

  @Autowired
  private UserDetailsService userDetailsService;

  private final TokenHandler tokenHandler;


  public TokenAuthenticationService() {
    this.tokenHandler = new JWTTokenHandler();
  }

  Authentication getAuthentication(final HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER);
    Authentication authentication = null;

    if(token != null) {
      final String username = tokenHandler.getUsername(token);

      if(username != null) { // There was not an error when trying to retrieve the username from the token

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

  /**
   * In case that authentication is successful a response header is populated with a new token
   * @param response the HTTP response to put the {@link #AUTH_HEADER} with the created token
   * @param userDetails information of the user that may be used to put in the token.
   */
  void addAuthentication(final HttpServletResponse response, final UserDetails userDetails) {
    final String token = tokenHandler.createToken(userDetails.getUsername());

    response.setHeader(AUTH_HEADER, token);
  }

  /**
   * Attempts to read the JSON Body from a POST /login HTTP Request and try to authenticate the user
   * @param request the request which contains the JSON body with the credentials
   * @return an instance of an authentication if the user exists;
   *         null in other case
   */
  Authentication getAuthenticationForLogin(final HttpServletRequest request) {
    try {
      final UserLogDTO user = new ObjectMapper().readValue(request.getInputStream(), UserLogDTO.class); // Attempt to map the JSON received in the body of /login endpoint to a UserLogDTO

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