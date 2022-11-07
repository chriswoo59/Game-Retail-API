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

import com.cognixia.jump.model.Game;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.service.GameService;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(GameController.class)
class GameControllerTest {
	
	@InjectMocks
	GameController controller;

	@MockBean
	GameService service;

	@MockBean
	MyUserDetailsService userDetailsService;

	@MockBean
	GameRepository repo;

	@MockBean
	JwtUtil util;

	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser
	void testGetGames() throws Exception {
		String uri = "/api/games";
		
		List<Game> games = new ArrayList<Game>();
		games.add(new Game());
		games.add(new Game());
		games.get(1).setTitle("second");
		
		when(service.getGames()).thenReturn(games);
		
		System.out.println(games);
		
		mockMvc.perform(get(uri))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(games.get(0).getId()))
			.andExpect(jsonPath("$[1].title").value("second"));
		
		verify(service, times(1)).getGames();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	public void testCreateGame() throws Exception {
		String uri = "/api/games";
		
		Game game = new Game();
		
		when(service.createGame(Mockito.any(Game.class))).thenReturn(game);
		
		mockMvc.perform(post(uri).content(asJsonString(game)).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value(game.getTitle()))
			.andExpect(jsonPath("$.genre").value(game.getGenre()));
		
		verify(service, times(1)).createGame(Mockito.any(Game.class));
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
	@WithMockUser(roles = "ADMIN")
	public void testDeleteUser() throws Exception {
		String uri = "/api/games/{id}";
		
		when(service.deleteGame(Mockito.anyLong())).thenReturn(new Game());
		
		mockMvc.perform(delete(uri, 1L).with(csrf()))
			.andDo(print())
			.andExpect(status().isOk());
	}

}












