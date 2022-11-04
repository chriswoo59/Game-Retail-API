package com.cognixia.jump.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Game;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository repo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	GameRepository gameRepo;

	public Order createOrder(Long user_id, Long game_id, int qty) throws ResourceNotFoundException {
		Optional<User> user = userRepo.findById(user_id);
		Optional<Game> game = gameRepo.findById(game_id);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User", user_id);
		}
		if (game.isEmpty()) {
			throw new ResourceNotFoundException("Game", game_id);
		}
		
		Order newOrder = new Order(-1L, new Date(), qty, user.get(), game.get());
		repo.save(newOrder);
		return newOrder;
	}
}
