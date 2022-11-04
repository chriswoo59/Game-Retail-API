package com.cognixia.jump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping("/{user_id}/{game_id}/{qty}")
	public ResponseEntity<?> createOrder(@PathVariable Long user_id, @PathVariable Long game_id, @PathVariable int qty) throws ResourceNotFoundException {
		Order created = service.createOrder(user_id, game_id, qty);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}
