package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
	private Set<Order> games = new HashSet<>();

	public Game() {

	}

	public Game(Long id, String title, Date release_date, String rating, double price, String platform,
			Set<Order> games) {
		super();
		this.id = id;
		this.title = title;
		this.release_date = release_date;
		this.rating = rating;
		this.price = price;
		this.platform = platform;
		this.games = games;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Set<Order> getGames() {
		return games;
	}

	public void setGames(Set<Order> games) {
		this.games = games;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", release_date=" + release_date + ", rating=" + rating
				+ ", price=" + price + ", platform=" + platform + ", games=" + games + "]";
	}

}
