package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	// Custom query for finding user by username
	// Important for security, security will only know how to find a user by the username
	// Optional -> Possibility that no user exists with this username, so we represent that with an Optional object (could be null)
	public Optional<User> findByUsername(String username);
}
