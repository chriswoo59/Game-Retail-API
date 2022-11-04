package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Game;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	Optional<Order> findByUserAndGame(User user, Game game);
}
