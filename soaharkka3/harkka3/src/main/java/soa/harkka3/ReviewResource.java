package soa.harkka3;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/")
public class ReviewResource {
	private CompaniesService companiesService = new CompaniesService();

	@GET
	public List<Review> getReviews(@PathParam("companyId") long companyId) {
		return companiesService.getReviews(companyId);
	}

	@GET
	@Path("/{rating}")
	public List<Review> getReview(@PathParam("companyId") long companyId, @PathParam("rating") int rating) {
		return companiesService.getReviewsWithRating(companyId,rating);    //getReviews(companyId).get(rating);
	}
}