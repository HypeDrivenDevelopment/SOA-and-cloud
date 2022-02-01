package com.demo3.task3;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

@Path("/offers")
public class OfferResource {
	OfferService offerService = new OfferService();
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOffers(){
		List<Offers> offers = offerService.getAllOffers();
		return Response.status(Status.CREATED)
				.header("Location", "" )
						.entity(offers)
						.build();
	}
	
	@PermitAll
	@GET
	@Path("/cheapest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cheapest() {
		Offers cheapest = offerService.getCheapest();
		return Response.status(Status.CREATED)
				.header("Location", "" )
						.entity(cheapest)
						.build();
	}
	
	@PermitAll
	@GET
	@Path("/{region}/cheapest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cheapest(@PathParam("region")String region, @Context UriInfo uriInfo) {
		Offers cheapest = offerService.getCheapest(region);
		if(cheapest == null){ throw new DataNotFoundException("Region "+region+" not found"); }
		
		String uriString = uriInfo.getBaseUriBuilder()
				.path(OfferResource.class)
				.path(cheapest.getCompany())
				.build()
				.toString();
		cheapest.addLink(uriString, "self");
		String name = String.valueOf(cheapest.getCompany());
		URI uri = uriInfo.getAbsolutePathBuilder().path(name).build();
		
		return Response.created(uri).entity(cheapest).build();
		/*return Response.status(Status.CREATED)
				.header("Location", "" )
						.entity(cheapest)
						.build();
						*/
	}
	
	@RolesAllowed({"admin","user"})
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postOffer(@QueryParam("price") int price, @QueryParam("company") String company, @QueryParam("region") String region){
		if (price == 0 || company == null || region == null) {
			throw new DataNotFoundException("All parameters not found");
			/*return Response.status(Status.NOT_FOUND)
					.entity("errorMessage") // TODO
					.build();*/
		}
		List<Offers> list = offerService.postOffer(price, company, region);
		return Response.status(Status.CREATED)
				.header("Location", "" )
						.entity(list)
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