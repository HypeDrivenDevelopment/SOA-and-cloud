package com.demo3.task3;

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
		c1.addLink("http://localhost:8080/task3/webapi/companies/1","self");
		c2.addLink("http://localhost:8080/task3/webapi/companies/2","self");
		companies = new HashMap<>();
		companies.put(1L, c1);
		companies.put(2L, c2);
	}
	
	private static List<Long> companyFreeIds = new ArrayList<>();
	private static List<Long> companyC = new ArrayList<>();
	
	private static List<User> userList = new ArrayList<User>() {
		private static final long serialVersionUID = 1L;

		{
			User u1 = new User("Jere", "Pakkanen","testman1","jere@email.com","qwer1234");
			u1.setRole(Arrays.asList("admin","user","quest"));
			User u2 = new User("Samu", "Peltonen","testman2","samu@email.com","qwer");
			u2.setRole(Arrays.asList("admin","user","quest"));
			User u3 = new User("Miikka", "Ruohoniemi","testman3","miikka@email.com","1234");
			u3.setRole(Arrays.asList("admin","user","quest"));
			add(u1);
			add(u2);
			add(u3);
		}
	};
	
	public static List<User> getUsers(){
		return userList;
	}
	
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
