package com.demo3.task3;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Principal {

	private String firstName, lastName,login, email, password;
	private List<String> role;
	
	public User() {
		
	}
	
	public User(String firstName, String lastName,String login, String email,String password){
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = new ArrayList<String>();
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	public void setLogin(String name) {
		this.login = name;
	}
	
	public void setEmail(String address) {
		this.email = address;
	}
	
	public void setPassword(String word) {
		this.password = word;
	}
	
	public void setRole(List<String> roles) {
		this.role = roles;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public List<String> getRole() {
		return this.role;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.firstName + " " + this.lastName;
	}
	
	public boolean hasRole(final Set<String> rolesSet) {
		for (String tempRole : role) {
			if(rolesSet.contains(tempRole)) {
				return true;
			}
		}
		return false;
	}

}
