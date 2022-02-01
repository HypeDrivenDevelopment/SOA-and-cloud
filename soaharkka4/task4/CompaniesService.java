package com.demo3.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TODO update to match new company datastructure

public class CompaniesService {
	
	
	private Map<Long, Company> companies = (Map<Long, Company>) DBclass.getCompanies();
	private List<Long> companyFreeIds = (List<Long>) DBclass.getCompanyFreeIds();
	private List<Long> companiesCounter = (List<Long>) DBclass.getCompanyC();
	
	//Change this number to be the same as the number of initialization entries
	public CompaniesService() {
		companiesCounter.add(2L);
	}
	public List<Company> getCompanies() {
		return new ArrayList<Company>(companies.values());
	}
	
	public Company getCompany(long id) {
		Company company = companies.get(id);
		if(company == null) {
			throw new DataNotFoundException("Company with id "+id+" was not found");
		}
		return company;
	}
	
	public Company addCompany(Company company) {
		if(companyFreeIds.isEmpty()) {
			companiesCounter.set(0, companiesCounter.get(0)+1L);
			company.setId(companiesCounter.get(0));
		}else {
			company.setId(companyFreeIds.get(0));
			companyFreeIds.remove(0);
		}
		companies.put(company.getId(), company);
		return company;
	}
	
	public Company updateCompany(Company company) {
		if(company.getId()<= 0) {
			return null;
		}
		companies.put(company.getId(), company);
		return company;
	}
	
	public Company removeCompany(long id) {
		companyFreeIds.add(id);
		return companies.remove(id);
	}
	
	public List<Review> getReviews(long id){
		return companies.get(id).getReviews();
	}
	
	public List<Review> getReviewsWithRating(long id, int rating){
		List<Review> temp = companies.get(id).getReviews();
		List<Review> filtered = new ArrayList<Review>();
		for(Review review : temp) {
			if(review.getRating() == rating) {
				filtered.add(review);
			}
		}
		return filtered;
	}
}
