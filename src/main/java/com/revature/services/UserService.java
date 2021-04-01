package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.UserDAO;

public class UserService {

	private IUserDAO userDAO = new UserDAO();
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	public User register(User u) {
		
		MDC.put("event", "Register");
		
		log.info("Registering new User");
		
		int generatedId = userDAO.insert(u);
		
		if(generatedId != -1 && generatedId != u.getId()) {
			u.setId(generatedId);
		} else {
			log.error("insert was not done correctly");
		}
		
		
		MDC.put("userId", Integer.toString(u.getId()));
		log.info("Successfully registered User");
		
		return u;
	}
	public List<User> findAllCustomers() {
		return userDAO.findAllCustomers();
	}
	public List<User> findAllEmployees() {
		return userDAO.findAllEmployees();
	}
	public List<User> findAllUsers() {
		return userDAO.findAllUsers();
	}
	public boolean delete(int userId) {
		
		return userDAO.delete(userId);
	}
	public boolean paySalary() {
		return userDAO.paySalary();
	}
	public User findUserById(int userId) {
		return userDAO.findUserById(userId);
	}
	
}
