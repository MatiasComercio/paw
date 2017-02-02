package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.StudentSessionDTO;
import ar.edu.itba.paw.webapp.models.UserSessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/user")
public class SessionController {
	private final static Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

	@Autowired
	private DTOEntityMapper mapper;

	@Autowired
	private AdminService as;

	@Autowired
	private StudentService ss;

	@Autowired
	private UserService us;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sessionShow() {
		final int dni = Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		final List<Role> roles = us.getRole(dni);

		if(roles.contains(Role.ADMIN)) {
			final Admin admin = as.getByDni(dni);
			final UserSessionDTO adminSessionDTO = mapper.convertToAdminSessionDTO(admin);

			return Response.ok(adminSessionDTO).build();
		} else if(roles.contains(Role.STUDENT)) {
			final Student student = ss.getByDni(dni);
			final StudentSessionDTO studentSessionDTO = mapper.convertToStudentSessionDTO(student);

			return Response.ok(studentSessionDTO).build();
		} else {
			LOGGER.warn("User {} does not have Role ADMIN or STUDENT", dni);

			return Response.serverError().build();
		}
	}
}
