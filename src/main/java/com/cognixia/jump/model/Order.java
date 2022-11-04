package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "order_table")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("orders")
	private User user;

	@ManyToOne
	@JoinColumn(name = "game_id")
	@JsonIgnoreProperties("orders")
	private Game game;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date order_date;

	@Column(nullable = false)
	private int qty;


	public Order() {

	}

	public Order(Long id, User user, Game game, Date order_date, int qty) {
		super();
		this.id = id;
		this.user = user;
		this.game = game;
		this.order_date = order_date;
		this.qty = qty;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	

}
