package soa.harkka3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Mock database. Static type binds information to the class, not the instance.
public class DBclass {
	
	//Initialize test cases for the company datastructure
	private static Review r1 = new Review("Pretty good",3);
	private static Review r2 = new Review("Not so good", 1);
	private static Review r4 = new Review("Pretty bad", 1);
	private static Review r3 = new Review ("AWESOME!", 4);
	private static Company c1 = new Company(1,"Jyväskylän energia", Arrays.asList(new Review[]{r1,r2,r4}));
	private static Company c2 = new Company(2,"Tampereen energia", Arrays.asList(new Review[]{r3}));
	
	private static Map<Long, Company> companies;
	static{
		c1.addLink("/companies/1", "self");
		c1.addLink("/companies/2", "self");
		companies = new HashMap<>();
		companies.put(1L, c1);
		companies.put(2L, c2);
	}
	
	private static List<Long> companyFreeIds = new ArrayList<>();
	private static List<Long> companyC = new ArrayList<>();
	
	
	
	public static Map<Long, Company> getCompanies(){
		return companies;
	}
	
	public static List<Long> getCompanyFreeIds(){
		return companyFreeIds;
	}
	
	public static List<Long> getCompanyC(){
		return companyC;
	}
}
