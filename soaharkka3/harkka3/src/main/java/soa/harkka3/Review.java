package soa.harkka3;

public class Review {
	private String review;
	private int rating;
	
	public Review() {
		
	}
	
	public Review(String review, int rating) {
		this.review = review;
		this.rating = rating;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public String getReview() {
		return review;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public int getRating() {
		return rating;
	}
	
}