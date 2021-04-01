package com.revature.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

import jdk.internal.org.jline.utils.Log;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	@GetMapping
	public ResponseEntity<Set<User>> findAll() {
		MDC.put("event", "find all users");
		Set<User> allUsers = userService.findAll();
		
		if(allUsers.isEmpty()) {
			log.warn("no users found :(");
			MDC.clear();
			return ResponseEntity.noContent().build();
		}
		log.info("users found");
		MDC.clear();
		return ResponseEntity.ok(allUsers);
		
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUsername(@PathVariable("username") int id) {
		MDC.put("event", "find all user by id");
		MDC.put("username", id);
		log.info("users found");
		MDC.clear();
		return ResponseEntity.ok(userService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@Valid @RequestBody User u) {
		return ResponseEntity.ok(userService.insert(u));
	}
	

	@PostMapping("/salary")
	public ResponseEntity<Set<User>> salary() {
		
		Set<User> allUsers = userService.paySalary();
		
		if(allUsers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(allUsers);
	}
}
