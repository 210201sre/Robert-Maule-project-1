package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.AccountController;
import com.revature.controllers.Controller;
import com.revature.controllers.PersonController;
import com.revature.controllers.UserController;

import io.javalin.Javalin;

public class App {
	
	private static Javalin app;
	
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		app = Javalin.create( (config) -> {
			
			config.defaultContentType = "application/json";
			
			config.enforceSsl = false;
			
			config.ignoreTrailingSlashes = true;
			
			config.enableCorsForAllOrigins();
			
			config.enableDevLogging();
		});
		
		configure(new UserController(),new AccountController(),new PersonController());
		
		app.start(7000);
	}

	public static void configure(Controller... controllers) {
		for(Controller c : controllers) {
			c.addRoutes(app);
		}
	}
	
	}
