package ar.edu.itba.paw.webapp.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.glassfish.jersey.server.ParamException.QueryParamException;

@Provider
public class QueryParamExceptionMapper implements ExceptionMapper<QueryParamException> {
  @Override
  public Response toResponse(final QueryParamException exception) {
    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}
