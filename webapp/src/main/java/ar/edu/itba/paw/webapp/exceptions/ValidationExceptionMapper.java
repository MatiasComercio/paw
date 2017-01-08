package ar.edu.itba.paw.webapp.exceptions;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

  @Override
  public Response toResponse(ValidationException e) {
    return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity("GIBARSIN PUTO " + e.getMessage()).build();
  }

}
