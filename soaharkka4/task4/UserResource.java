package com.demo3.task3;

import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/users")
public class UserResource {
	
	private UserService userService = new UserService();
	
	@GET
	@RolesAllowed({"admin"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		List<User> users = userService.getUsers();
		return Response.status(Status.CREATED).header("Location", "").entity(users).build();
	}
	
	@POST
	@RolesAllowed({"admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) throws URISyntaxException {
		User newUser = userService.addUser(user);
		return Response.status(Status.CREATED).header("Location", "").entity(newUser).build();
	}
	
	@PUT
	@RolesAllowed({"admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {
		User newUser = userService.updateUser(user);
		return Response.status(Status.CREATED).header("Location", "").entity(newUser).build();
	}
	
	@DELETE
	@RolesAllowed({"admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(User user) {
		User newUser = userService.removeUser(user.getLogin());
		return Response.status(Status.CREATED).header("Location", "").entity(newUser).build();
	}

}
