package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlainTextBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
  }
}
