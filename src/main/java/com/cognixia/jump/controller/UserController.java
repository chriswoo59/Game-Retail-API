package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService service;
	
	
	@GetMapping
	public ResponseEntity<?> getUsers() {
		List<User> users = service.getUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		User user = service.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		
		User created = service.createUser(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
		User targetUser = service.deleteUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(targetUser);
	}
}











