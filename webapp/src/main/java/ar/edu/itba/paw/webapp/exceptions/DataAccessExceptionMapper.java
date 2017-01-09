package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataAccessExceptionMapper implements ExceptionMapper<DataAccessException>{

  private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessExceptionMapper.class);

  @Override
  public Response toResponse(DataAccessException e) {
    LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
    return Response.ok().status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
  }

}
