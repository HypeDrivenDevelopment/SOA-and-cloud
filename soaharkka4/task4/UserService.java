package com.demo3.task3;

import java.util.List;

public class UserService {
	private List<User> userList = (List<User>) DBclass.getUsers();

	public User getUser(String name) {
		for(User user : userList) {
			if(user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	public List<User> getUsers(){
		return userList;
	}
	
	public User addUser(User newUser) {
		for(User user : userList) {
			if(user.getLogin().equals(newUser.getLogin())) {
				return null;
			}
		}
		userList.add(newUser);
		return newUser;
	}
	
	public User findUser(String name) {
		for(User user : userList) {
			if(user.getLogin().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	public User updateUser(User newUser) {
		for(User user : userList) {
			if(user.getLogin().equals(newUser.getLogin())) {
				userList.remove(user);
				userList.add(newUser);
				return newUser;
			}
		}
		return null;
	}
	
	public User removeUser(String name) {
		for(User user : userList) {
			if(user.getLogin().equals(name)) {
				userList.remove(user);
				return user;
			}
		}
		return null;
	}
	
	public User checkCredentials(String name, String password) {
		for(User user : userList) {
			if(user.getLogin().equals(name) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
}
