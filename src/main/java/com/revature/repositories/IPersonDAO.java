package com.revature.repositories;

import java.util.List;

import com.revature.models.Customer;
import com.revature.models.Employee;

// Standard CRUD operations as well as anything you might find useful
public interface IPersonDAO {
	public int insertEmployee(Employee e,int id);
	public int insertCustomer(Customer c,int id);
	public boolean SwitchRole(int id,char c);
}
