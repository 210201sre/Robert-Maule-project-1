package com.revature.controllers;

import org.slf4j.MDC;

import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.services.PersonService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import jdk.internal.org.jline.utils.Log;

public class PersonController implements Controller {
	
	private PersonService personService = new PersonService();

	
	private Handler createCustomer = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		Customer c = ctx.bodyAsClass(Customer.class);
		ctx.json(personService.insertCustomer(c,id));
		ctx.status(200);
		MDC.clear();
	};
	
	private Handler createEmployee = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		Employee e = ctx.bodyAsClass(Employee.class);

		ctx.json(personService.insertEmployee(e,id));
		ctx.status(200);
		MDC.clear();
	};
	private Handler switchRole = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		String type = ctx.pathParam("type");
		ctx.json(personService.SwitchRole(id,type.charAt(0)));
		ctx.status(200);
		MDC.clear();
	};

	
	public void addRoutes(Javalin app) {
		
		app.post("/employee/:id", this.createEmployee);
		
		app.post("/customer/:id", this.createCustomer);
		
		app.post("/switch/:id/:type", this.switchRole);
		
		
	}
}
