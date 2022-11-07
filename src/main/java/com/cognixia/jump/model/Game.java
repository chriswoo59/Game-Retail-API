package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_id")
	private Long id;

	@Column(unique = true, nullable = false)
	private String title;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date release_date;

	@Column(nullable = false)
	private String rating;

	@Column(nullable = false)
	private String genre;

	@Column(nullable = false)
	private String platform;

	@Column(nullable = false)
	private double price;

	@OneToMany(mappedBy = "game", targetEntity = Order.class)
	@JsonIgnoreProperties("game")
	private Set<Order> orders = new HashSet<>();

	public Game() {
		this.id = -1L;
		this.title = "test";
		this.release_date = new Date();
		this.rating = "T";
		this.genre = "Genre";
		this.platform = "Platform";
		this.price = 59.99;
		this.orders = null;
	}

	public Game(Long id, String title, Date release_date, String rating, String genre, String platform, double price,
			Set<Order> orders) {
		super();
		this.id = id;
		this.title = title;
		this.release_date = release_date;
		this.rating = rating;
		this.genre = genre;
		this.platform = platform;
		this.price = price;
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRelease_date() {
		return release_date;
	}

	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", release_date=" + release_date + ", rating=" + rating
				+ ", genre=" + genre + ", platform=" + platform + ", price=" + price + ", orders=" + orders + "]";
	}

}
