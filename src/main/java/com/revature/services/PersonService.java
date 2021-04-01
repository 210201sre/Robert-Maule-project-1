
package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.repositories.IPersonDAO;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.PersonDAO;
import com.revature.repositories.UserDAO;

public class PersonService {

	private IPersonDAO personDAO = new PersonDAO();
	
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);
	
	public Employee insertEmployee(Employee e,int id) {
		
		MDC.put("event", "Inserting Employee Data");
		
		log.info("Inserting Employee Data");
		
		int generatedId = personDAO.insertEmployee(e,id);
		
		if(generatedId != -1 && generatedId != e.getId()) {
			e.setId(generatedId);
		} else {
			log.error("insert was not done correctly");
		}
		
		
		MDC.put("EmployeeId", Integer.toString(e.getId()));
		log.info("Successfully registered User");
		
		return e;
	}
	public Customer insertCustomer(Customer c,int id) {
		
		MDC.put("event", "Inserting Customer Data");
		
		log.info("Inserting Customer Data");
		
		int generatedId = personDAO.insertCustomer(c,id);
		
		if(generatedId != -1 && generatedId != c.getId()) {
			c.setId(generatedId);
		} else {
			log.error("insert was not done correctly");
		}
		
		
		MDC.put("CustomerId", Integer.toString(c.getId()));
		log.info("Successfully registered User");
		
		return c;
	}
	public boolean SwitchRole(int id,char c) {
		return personDAO.SwitchRole( id, c);
	}

	
}
