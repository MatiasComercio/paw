package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

  @Override
  public Response toResponse(final ConstraintViolationException e) {
    LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
    final StringBuilder responseEntity = new StringBuilder();

    responseEntity.append("{ \"errors\": [\n");
    final Iterator<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations().iterator();
//    for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
    while(constraintViolations.hasNext()) {
      final ConstraintViolation<?> cv = constraintViolations.next();

      responseEntity.append("{");
      responseEntity
              .append("\"")
              .append(getPropertyName(cv))
              .append("\"")
              .append(":")
              .append("\"")
              .append(cv.getMessage())
              .append("\"")
              .append("}");

              if(constraintViolations.hasNext()) {
                responseEntity.append(", ");
              }

              responseEntity.append("\n");
    }
    responseEntity.append("] }");
    return Response.status(Response.Status.BAD_REQUEST).entity(responseEntity.toString()).build();
  }

  private String getPropertyName(final ConstraintViolation<?> cv) {
    final Iterator<Path.Node> iterator = cv.getPropertyPath().iterator();

    Path.Node next = null;

    while(iterator.hasNext()) {
      next = iterator.next();
    }

    return next.getName();
  }

}
