package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService service;
	
	
	@GetMapping("/user")
	public ResponseEntity<?> getUsers() {
		List<User> users = service.getUsers();
		return ResponseEntity.status(201).body(users);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		
		User created = service.createUser(user);
		
		return ResponseEntity.status(201).body(created);
		
	}
}











