package soa.harkka3;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Offers implements Comparable<Offers>{
	
	private int price;
	private String company;
	private String area;
	private List<Link> links = new ArrayList<>();
	
	public Offers(int price, String company, String area) {
		this.price = price;
        this.area = area;
        this.company = company;
	}
	
	@Override
    public int compareTo(Offers compare) {
        int compareprice=((Offers)compare).getPrice();
        return this.price-compareprice;

    }
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setprice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getArea() {
		return this.area;
	}
	
	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}


}
