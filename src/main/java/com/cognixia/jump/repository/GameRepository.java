package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>{

	@Query(value = "SELECT * FROM game WHERE sale_price IS NOT NULL", nativeQuery = true)
	List<Game> findGamesOnSale();

}
