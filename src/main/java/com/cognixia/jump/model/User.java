package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Role {
		USER, ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Incrementation will use auto-increment
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private Date dob;

	// Will store the role as a string in database
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(columnDefinition = "boolean default true")
	private boolean enabled;
	
	public User() {
		
	}

	public User(Long id, String username, String password, String email, Date dob, Role role, boolean enabled) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.role = role;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", dob="
				+ dob + ", role=" + role + ", enabled=" + enabled + "]";
	}
}
