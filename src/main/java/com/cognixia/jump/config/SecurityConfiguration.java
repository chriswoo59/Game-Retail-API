package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;

@Configuration
public class SecurityConfiguration {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;

	// Authentication
	@Bean
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	// Authorization
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.authorizeRequests()
			// ANY USER
			.antMatchers("/swagger-ui/**").permitAll()		// Anyone can view docs
			.antMatchers("/v3/**").permitAll()
			.antMatchers("/docs").permitAll()
			.antMatchers(HttpMethod.POST, "/api/users").permitAll()	// Anyone can create a new user
			.antMatchers("/authenticate").permitAll()				// Anyone can create a JWT without needing a JWT first
			// ADMIN
			// USERS
			.antMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
			// GAMES
			.antMatchers(HttpMethod.POST, "/api/games").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/games/{game_id}").hasRole("ADMIN")
			// ORDERS
			.antMatchers(HttpMethod.GET, "/api/orders/all").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/api/orders/{user_id}/{game_id}/{qty}").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/orders/{order_id}").hasRole("ADMIN")
			//.anyRequest().authenticated() 							// The other requests require a user account to access
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Tells Spring security NOT to create sessions/session tokens

		// Request will go through many filters, but typically the first filter it checks is the one for the username & password
		// However, we will set it up, that our JWT filter gets checked first, or else the authentication will fail,
		// since Spring security won't know where to find the username or password.
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	// Encoder -> Method help with encoding/decoding the users' passwords
	@Bean
	protected PasswordEncoder encoder() {

		// encrypts the password with BCrypt algorithm
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());

		return authProvider;
	}

	// can autowire and access the authentication manager (manages authentication (login) of our project)
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}
