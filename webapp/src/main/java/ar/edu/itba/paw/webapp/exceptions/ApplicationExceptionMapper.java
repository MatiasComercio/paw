package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception>{

  @Override
  public Response toResponse(Exception e) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).type(MediaType.TEXT_PLAIN).entity("[Gibarsin es menos Puto ] " + e.getMessage()).build();
  }
}
