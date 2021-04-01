package com.revature.controllers;

import org.slf4j.MDC;

import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import jdk.internal.org.jline.utils.Log;

public class UserController implements Controller {
	
	private UserService userService = new UserService();

	private Handler getAllCustomers = (ctx) -> {
		ctx.json(userService.findAllCustomers());
		ctx.status(200);
		MDC.clear();
	};
	
	private Handler getAllEmployees = (ctx) -> {
		ctx.json(userService.findAllEmployees());
		ctx.status(200);
		MDC.clear();
	};
	
	private Handler getAllUsers = (ctx) -> {
		ctx.json(userService.findAllUsers());
		ctx.status(200);
		MDC.clear();
	};
	
	
	private Handler getSingleUser = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		ctx.json(userService.findUserById(id));
		MDC.clear();
	};

	private Handler removeFullUser = (ctx) -> {
		String idString = ctx.pathParam("id");
		int id = Integer.parseInt(idString);
		userService.delete(id);
		MDC.clear();
	};
	
	private Handler createUser = (ctx) -> {
		User u = ctx.bodyAsClass(User.class);
		u = userService.register(u);
		ctx.json(u);
		ctx.status(201);
		
		MDC.clear();
	};
	
	private Handler PaySalary = (ctx) -> {
		ctx.json(userService.paySalary());
		ctx.status(201);
	};

	
	public void addRoutes(Javalin app) {
		app.get("/customers", this.getAllCustomers);
		
		app.get("/users/:id", this.getSingleUser);
		
		app.get("/users", this.getAllUsers);
		
		app.get("/employees", this.getAllEmployees);
		
		app.post("/users", this.createUser);
		
		app.post("/users/remove/:id", this.removeFullUser);
		
		app.post("salary", this.PaySalary);
		
	}
}
