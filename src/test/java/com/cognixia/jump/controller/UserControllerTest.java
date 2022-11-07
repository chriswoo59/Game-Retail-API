package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@InjectMocks
	UserController controller;

	@MockBean
	UserService service;

	@MockBean
	MyUserDetailsService userDetailsService;

	@MockBean
	UserRepository repo;

	@MockBean
	JwtUtil util;

	@Autowired
	MockMvc mockMvc;

	
	@Test
	@WithMockUser
	public void testGetUsers() throws Exception {
		String uri = "/api/users";

		List<User> users = new ArrayList<User>();
		users.add(new User());
		users.add(new User(-1L, "second", "pw123", "test2@email.com", new Date(), Role.ROLE_ADMIN, false, null));

		when(service.getUsers()).thenReturn(users);

		mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(users.size()))
			.andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()));

		verify(service, times(1)).getUsers();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	public void testGetUserById() throws Exception {
		String uri = "/api/users/{id}";
		
		User user = (new User(-1L, "second", "pw123", "test2@email.com", new Date(), Role.ROLE_ADMIN, false, null));
		
		when(service.getUserById(Mockito.anyLong())).thenReturn(user);
		
		mockMvc.perform(get(uri, 1L))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value(user.getUsername()));
		
		verify(service, times(1)).getUserById(Mockito.anyLong());
		verifyNoMoreInteractions(service);
	}

	@Test
	@WithMockUser
	public void testCreateUser() throws Exception {
		String uri = "/api/users";
		
		User user = new User();
		
		when(service.createUser(Mockito.any(User.class))).thenReturn(user);
		
		mockMvc.perform(post(uri).content(asJsonString(user)).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.username").value(user.getUsername()))
			.andExpect(jsonPath("$.email").value(user.getEmail()));
		
		verify(service, times(1)).createUser(Mockito.any(User.class));
		verifyNoMoreInteractions(service);
	}

	// HELPER METHOD
	// converts any object to a JSON string
	public static String asJsonString(final Object obj) {

		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}
	
	@Test
	@WithMockUser
	public void testDeleteUserNotAdmin() throws Exception {
		String uri = "/api/users/{id}";
		
		when(service.deleteUser(Mockito.anyLong())).thenReturn(new User());
		
		mockMvc.perform(delete(uri, 1L))
			.andDo(print())
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteUserWithAdmin() throws Exception {
		String uri = "/api/users/{id}";
		
		when(service.deleteUser(Mockito.anyLong())).thenReturn(new User());
		
		mockMvc.perform(delete(uri, 1L).with(csrf()))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
