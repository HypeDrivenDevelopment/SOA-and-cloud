package com.demo3.task3;

public class Link {
	private String link;
	private String rel;
// below generate getters and setters for all the variable of the class…

	public Link() {
		
	}

	public Link(String link, String rel) {
		this.link = link;
		this.rel = rel;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

}

