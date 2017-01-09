package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionMapper.class);

  @Override
  public Response toResponse(ValidationException e) {
    LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
    final StringBuilder strBuilder = new StringBuilder();
    for (ConstraintViolation<?> cv : ((ConstraintViolationException) e).getConstraintViolations()) {
      strBuilder.append(cv.getMessage() + "\n");
    }
    return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity(strBuilder.toString()).build();
  }

}
