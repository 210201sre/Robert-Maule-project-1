package com.revature.repositories;

import java.util.List;

import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;

// Standard CRUD operations as well as anything you might find useful
public interface IUserDAO {

	public List<User> findAllCustomers();
	public List<User> findAllEmployees(); 
	public List<User> findAllUsers(); 
	public int insert(User u);
	public boolean delete(int userId);
	public User findUserById(int userId);
	public boolean paySalary();
}
