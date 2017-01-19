package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MethodArgumentTypeMismatchExceptionMapper implements ExceptionMapper<MethodArgumentTypeMismatchException>{

  private static final Logger LOGGER = LoggerFactory.getLogger(MethodArgumentTypeMismatchExceptionMapper .class);

  @Override
  public Response toResponse(MethodArgumentTypeMismatchException e) {
    LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
    return Response.ok().status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
  }
}
