package soa.harkka3;

import java.net.URI;
import java.util.List;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

//TODO add proper responses with links and exception handler

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
public class ElectricityResource {
	CompaniesService service = new CompaniesService();
	
	@GET
	public Response getCompanies(){
		List<Company> companies = service.getCompanies();
		return Response.status(Status.CREATED)
				.header("Location", "" )
						.entity(companies)
						.build();
	}
	
	@GET
	@Path("/{companyId}")
	public Response getCompany(@PathParam("companyId") long id){
		Company company = service.getCompany(id);
		if(company == null){ throw new DataNotFoundException("Company "+id+" not found"); }
		return Response.status(Status.CREATED)
				.header("Location", "" )
						.entity(company)
						.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCompany (Company company, @Context UriInfo uriInfo){
		Company newComp = service.addCompany(company);
		String uriString = uriInfo.getBaseUriBuilder()
						.path(ElectricityResource.class)
						.path(Long.toString(company.getId()))
						.build()
						.toString();
		newComp.addLink(uriString, "self");
		String id = String.valueOf(newComp.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri).entity(newComp).build();
		//Old response, new work the same, but no links for some reason
		/*return Response.status(Status.CREATED)
				.header("Location","")
				.entity(newComp)
				.build();*/
	}
	
	
	@PUT
	@Path("/{companyId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Company updateCompany(@PathParam("companyId") long id, Company company){
		company.setId(id);
		return service.updateCompany(company);
	}
	
	@DELETE
	@Path("/{companyId}")
	public void deleteCompany (@PathParam("companyId") long id){
		service.removeCompany(id);
	}
	
	@GET
	@Path("/{companyId}/reviews")
	public Response getComments(@PathParam("companyId") long publicationId){
		List<Review> reviews = service.getReviews(publicationId);
		return Response.status(Status.CREATED)
				.header("Location","")
				.entity(reviews)
				.build();
	}
	/*
	public class DataNotFoundException extends RuntimeException{
		private static final long serialVersionUID = -6672553621676928689L;
		public DataNotFoundException(String message){
			super(message);
		}
	}
	*/
}