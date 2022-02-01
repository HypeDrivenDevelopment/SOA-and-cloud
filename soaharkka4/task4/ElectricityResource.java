package com.demo3.task3;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;


@Path("/companies")
public class ElectricityResource {

	CompaniesService service = new CompaniesService();

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanies() {
		List<Company> companies = service.getCompanies();
		return Response.status(Status.CREATED).header("Location", "").entity(companies).build();
	}

	@GET
	@PermitAll
	@Path("/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompany(@PathParam("companyId") long id) {
		Company company = service.getCompany(id);
		return Response.status(Status.CREATED).header("Location", "").entity(company).build();
	}

	@POST
	@RolesAllowed({"admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCompany(Company company, @Context UriInfo uriInfo)
			throws URISyntaxException {
		Company newCompany = service.addCompany(company);
		String uriStr = uriInfo.getBaseUriBuilder()
							.path(ElectricityResource.class)
							.path(Long.toString(company.getId())).build()
							.toString();
		newCompany.addLink(uriStr, "self");
		String id = String.valueOf(newCompany.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri).entity(newCompany).build();
	}

	@PUT
	@RolesAllowed({"admin","user"})
	@Path("/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCompany(@PathParam("companyId") long id, Company company, @Context UriInfo uriInfo) {
		company.setId(id);//Company newCompany = service.updateCompany(company);//addCompany(company);
		String uriStr = uriInfo.getBaseUriBuilder()
							.path(ElectricityResource.class)
							.path(Long.toString(company.getId())).build()
							.toString();
		company.addLink(uriStr, "self");
		String id2 = String.valueOf(company.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id2).build();
		return Response.created(uri).entity(company).build();
		
		/*company.setId(id);
		return Response.status(Status.CREATED).header("Location", "").entity(company).build();*/
	}

	@DELETE
	@RolesAllowed({"admin"})
	@Path("/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCompany(@PathParam("companyId") long id) {
		Company company = service.getCompany(id);
		if (company == null) {
			throw new DataNotFoundException("Company with id " + id + " not found");
		}
		service.removeCompany(id);
		return Response.status(Status.CREATED).header("Location", "").entity(company).build();
	}

	@Path("/{companyId}/reviews")
	public ReviewResource getReviewResource(){
		return new ReviewResource(); 
	}

}