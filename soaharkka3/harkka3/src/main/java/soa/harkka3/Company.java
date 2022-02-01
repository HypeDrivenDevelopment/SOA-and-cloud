package soa.harkka3;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


//TODO: add link getters setters and review to list

@XmlRootElement
public class Company {
	private long id;
	private String name;
	private List<Review> reviews = new ArrayList<Review>();
	//private Map<Long, Review> reviews = new HashMap<>();
	private List<Link> links = new ArrayList<>();
	
	//Empty constructors are a must have for the JSON conversion to work
	public Company() {
		
	}
	
	public Company(long id, String name, List<Review> review) {
		this.id = id;
		this.name = name;
		this.reviews = review;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setReview(List<Review> review) {
		this.reviews = review;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}
	
}