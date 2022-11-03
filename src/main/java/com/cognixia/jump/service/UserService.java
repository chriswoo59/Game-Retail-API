package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;

	public List<User> getUsers() {
		return repo.findAll();
	}

	public User createUser(User user) {
		user.setId(null);

		// Each password for a new user gets encoded
		user.setPassword(encoder.encode(user.getPassword()));

		return repo.save(user);
	}

	public User deleteUser(Long id) throws ResourceNotFoundException {
		Optional<User> found = repo.findById(id);
		if (found.isPresent()) {
			repo.delete(found.get());;
			return found.get();
		}
		throw new ResourceNotFoundException("User with id: " + id + " not found.");
	}
}
