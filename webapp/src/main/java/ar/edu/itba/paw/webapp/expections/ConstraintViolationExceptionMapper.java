package ar.edu.itba.paw.webapp.expections;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>{

  @Override
  public Response toResponse(ConstraintViolationException e) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).type(MediaType.TEXT_PLAIN).entity("[UNA EXCEPCION :O] " + e.getMessage()).build();
  }

}
