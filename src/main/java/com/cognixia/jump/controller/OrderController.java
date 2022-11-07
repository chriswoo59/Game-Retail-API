package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService service;
	
	@GetMapping
	public ResponseEntity<?> getUserOrders() throws ResourceNotFoundException {
		List<Order> orders = service.getUserOrders();
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllOrders() {
		List<Order> orders = service.getAllOrders();
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}
	
	@PostMapping("/{game_id}/{qty}")
	public ResponseEntity<?> createOrder(@PathVariable Long game_id, @PathVariable int qty) throws ResourceNotFoundException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Order created = service.createOrder(auth.getName(), game_id, qty);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	
	// An admin version of createOrder to create an order for any user
	@PostMapping("/{user_id}/{game_id}/{qty}")
	public ResponseEntity<?> createOrderForUser(@PathVariable Long user_id, @PathVariable Long game_id, @PathVariable int qty) throws ResourceNotFoundException {
		Order created = service.createOrderForUser(user_id, game_id, qty);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long id) throws ResourceNotFoundException{
		Order target = service.deleteOrder(id);
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}
}
