package com.cognixia.jump.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	public Order createOrder(String username, Long game_id, int qty)
			throws ResourceNotFoundException {
		
		Optional<User> user = userRepo.findByUsername(username);
		Optional<Game> game = gameRepo.findById(game_id);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("Username " + username + " not found.");
		}
		if (game.isEmpty()) {
			throw new ResourceNotFoundException("Game", game_id);
		}

		
		Order newOrder = new Order(-1L, user.get(), game.get(), new Date(), qty);
		repo.save(newOrder);
		user.get().addOrder(newOrder);
		game.get().addOrder(newOrder);
		
		return newOrder;
	}

	public Order createOrderForUser(Long user_id, Long game_id, int qty) throws ResourceNotFoundException {
		Optional<User> user = userRepo.findById(user_id);
		Optional<Game> game = gameRepo.findById(game_id);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User", user_id);
		}
		if (game.isEmpty()) {
			throw new ResourceNotFoundException("Game", game_id);
		}

		Order newOrder = new Order(-1L, user.get(), game.get(), new Date(), qty);
		repo.save(newOrder);
		user.get().addOrder(newOrder);
		game.get().addOrder(newOrder);
		
		return newOrder;
	}

	public List<Order> getUserOrders() throws ResourceNotFoundException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> found = userRepo.findByUsername(auth.getName());
		if (found.isPresent()) {
			List<Order> orders = repo.getUserOrders(found.get().getId());
			return orders;
		}

		throw new ResourceNotFoundException("Current user not found.");
	}

	public List<Order> getAllOrders() {
		return repo.findAll();
	}

	public Order deleteOrder(Long id) throws ResourceNotFoundException {
		Optional<Order> found = repo.findById(id);
		if (found.isPresent()) {
			repo.delete(found.get());
			;
			return found.get();
		}
		throw new ResourceNotFoundException("Order", id);
	}

}
