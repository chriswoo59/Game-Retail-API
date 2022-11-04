package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Game;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	public Optional<Order> findByUserAndGame(User user, Game game);

	@Query(value = "SELECT * FROM order_table WHERE user_id = ?1", nativeQuery = true)
	public List<Order> getUserOrders(Long id);
}
