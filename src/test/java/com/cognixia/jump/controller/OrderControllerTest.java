package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;
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
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.service.GameService;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.OrderService;
import com.cognixia.jump.util.JwtUtil;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
	
	@InjectMocks
	OrderController controller;

	@MockBean
	OrderService service;

	@MockBean
	MyUserDetailsService userDetailsService;

	@MockBean
	OrderRepository repo;

	@MockBean
	JwtUtil util;

	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser
	void testGetUserOrders() throws Exception {
		String uri = "/api/orders";
		List<Order> orders = new ArrayList<>();
		orders.add(new Order());
		orders.add(new Order());
		
		when(service.getUserOrders()).thenReturn(orders);
		
		mockMvc.perform(get(uri))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(orders.size()));
		
		verify(service, times(1)).getUserOrders();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testGetAllOrders() throws Exception {
		String uri = "/api/orders/all";
		List<Order> orders = new ArrayList<>();
		orders.add(new Order());
		orders.add(new Order());
		
		when(service.getAllOrders()).thenReturn(orders);
		
		mockMvc.perform(get(uri))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(orders.size()));
		
		verify(service, times(1)).getAllOrders();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void testCreateOrder() throws Exception {
		String uri = "/api/orders/{game_id}/{qty}";
		
		Order order = new Order();
		
		when(service.createOrder(Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt())).thenReturn(order);
		
		mockMvc.perform(post(uri, 1L, 1).with(csrf()))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(order.getId()));
		
		verify(service, times(1)).createOrder(Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt());
		verifyNoMoreInteractions(service);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void testDeleteOrder() throws Exception {
		String uri = "/api/orders/{order_id}";
		
		when(service.deleteOrder(Mockito.anyLong())).thenReturn(null);
		
		mockMvc.perform(delete(uri, 1L).with(csrf()))
		.andDo(print())
		.andExpect(status().isOk());
		
		verify(service, times(1)).deleteOrder(Mockito.anyLong());
		verifyNoMoreInteractions(service);
	}
}











