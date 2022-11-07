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
import com.cognixia.jump.model.Game;
import com.cognixia.jump.service.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {
	
	@Autowired
	GameService service;
	
	@GetMapping
	public ResponseEntity<?> getGames() {
		List<Game> games = service.getGames();
		return ResponseEntity.status(HttpStatus.OK).body(games);
	}
	
	@GetMapping("/sale")
	public ResponseEntity<?> getGamesOnSale() {
		List<Game> gamesOnSale = service.getGamesOnSale();
		return ResponseEntity.status(HttpStatus.OK).body(gamesOnSale);
	}
	
	@PostMapping
	public ResponseEntity<?> createGame(@RequestBody Game game) {
		
		Game created = service.createGame(game);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGame(@PathVariable Long id) throws ResourceNotFoundException {
		Game targetGame = service.deleteGame(id);
		return ResponseEntity.status(HttpStatus.OK).body(targetGame);
	}

}
