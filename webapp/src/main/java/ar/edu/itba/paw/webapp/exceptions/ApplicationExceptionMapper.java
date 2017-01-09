package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception>{

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionMapper.class);

  @Override
  public Response toResponse(Exception e) {
    LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
  }
}
