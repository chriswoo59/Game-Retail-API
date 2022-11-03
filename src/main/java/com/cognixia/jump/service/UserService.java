package com.cognixia.jump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
