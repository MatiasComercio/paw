package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

  private static final int LEAF_POSITION = 2;

  @Override
  public Response toResponse(final ConstraintViolationException e) {
    LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
    final StringBuilder responseEntity = new StringBuilder();

    responseEntity.append("{ errors: [\n");
    for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
      final String propertyName;
      final String[] propertyNodes = cv.getPropertyPath().toString().split("\\.");

      if(propertyNodes.length == 3) {
         propertyName = propertyNodes[LEAF_POSITION];
      } else {
        propertyName = propertyNodes[1];
      }

      LOGGER.debug("propertyname = " + propertyName);

      responseEntity.append("{");
      responseEntity
              .append("\"")
                .append(propertyName)
              .append("\"")
              .append(":")
              .append("\"")
                .append(cv.getMessage())
              .append("\"")
              .append("},")
              .append("\n");
    }
    responseEntity.append("] }");
    return Response.status(Response.Status.BAD_REQUEST).entity(responseEntity.toString()).build();
  }

}
