package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom handler used to return a 404 Not Found instead of a 403 (Unauthorized) to prevent unauthorized
 * (but authenticated) users from knowing if a resource, which requires certain authorities
 * or does not have the required Role, exists.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}
