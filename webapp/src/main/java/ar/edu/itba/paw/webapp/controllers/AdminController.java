package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.webapp.models.AdminDTO;
import ar.edu.itba.paw.webapp.models.AdminsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.ok;

@Component
@Path("/admins")
public class AdminController {

  /** Fields **/

  private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  private AdminService as;

  @Autowired
  private DTOEntityMapper mapper;

  /** API Methods **/

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response adminsIndex(@QueryParam("dni") final Integer dni,
                              @QueryParam("firstName") final String firstName,
                              @QueryParam("lastName") final String lastName) {
    final AdminFilter adminFilter = new AdminFilter.AdminFilterBuilder()
            .dni(dni)
            .firstName(firstName)
            .lastName(lastName)
            .build();

    final List<Admin> admins = as.getByFilter(adminFilter);

    final List<AdminDTO> adminsDTOList = admins.stream().map(admin -> mapper.converToAdminDTO(admin)).collect(Collectors.toList());

    System.out.println(adminsDTOList);

    return ok(new AdminsList(adminsDTOList)).build();
  }
}
