package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.webapp.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.*;

@Component
@Path("/admins")
public class AdminController {

  /** Fields **/

  private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  private UserService us;

  @Autowired
  private AdminService as;

  @Autowired
  private DTOEntityMapper mapper;

  @Context
  private UriInfo uriInfo;

  /** API Methods **/

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response adminsIndex(@QueryParam("dni") final Integer dni,
                              @Size(max=50)
                              @QueryParam("firstName") final String firstName,
                              @Size(max=50)
                              @QueryParam("lastName") final String lastName) {
    final AdminFilter adminFilter = new AdminFilter.AdminFilterBuilder()
            .dni(dni)
            .firstName(firstName)
            .lastName(lastName)
            .build();

    final List<Admin> admins = as.getByFilter(adminFilter);
    final List<AdminDTO> adminsDTOList = admins.stream().map(admin -> mapper.converToAdminDTO(admin)).collect(Collectors.toList());

    return ok(new AdminsList(adminsDTOList)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response adminsNew(@Valid AdminNewDTO adminNewDTO) {
    final Admin admin = mapper.convertToAdmin(adminNewDTO);

    if (us.userExists(adminNewDTO.getDni())) {
      return status(Status.CONFLICT).build();
    }
    if(!as.create(admin)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(admin.getDni())).build();

    return created(uri).build();
  }

  @GET
  @Path("/{dni}")
  public Response adminsShow(@PathParam("dni") final int dni) {
    final Admin admin = as.getByDni(dni);

    if(admin == null) {
      return status(Status.NOT_FOUND).build();
    }
    final AdminShowDTO adminShowDTO = mapper.converToAdminShowDTO(admin);

    return ok(adminShowDTO).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{dni}")
  public Response adminsUpdate(@PathParam("dni") final int dni,
                               @Valid final AdminsUpdateDTO adminsUpdateDTO) {
    final Admin oldAdmin = as.getByDni(dni);

    if(oldAdmin == null) {
      return status(Status.NOT_FOUND).build();
    }

    final Admin partialAdmin = mapper.convertToAdmin(adminsUpdateDTO);
    partialAdmin.setDni(dni);
    as.update(partialAdmin);

    return noContent().build();
  }

  @POST
  @Path("/inscriptions/")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response adminsInscriptionsSwitch(@Valid InscriptionSwitchDTO inscriptionSwitchDTO) {
    if(inscriptionSwitchDTO.isEnabled()) {
      as.enableInscriptions();
    } else {
      as.disableInscriptions();
    }

    return noContent().build();
  }
}
