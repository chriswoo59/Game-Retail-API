package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Game;
import com.cognixia.jump.repository.GameRepository;

@Service
public class GameService {
	
	@Autowired
	GameRepository repo;

	public List<Game> getGames() {
		return repo.findAll();
	}
	
	public List<Game> getGamesOnSale() {
		return repo.findGamesOnSale();
	}

	public Game createGame(Game game) {
		return repo.save(game);
	}
	
	public Game updateGame(Game game) {
		return repo.save(game);
	}

	public Game deleteGame(Long id) throws ResourceNotFoundException {
		Optional<Game> found = repo.findById(id);
		if (found.isPresent()) {
			repo.delete(found.get());;
			return found.get();
		}
		throw new ResourceNotFoundException("Game", id);
	}

	

	

}
