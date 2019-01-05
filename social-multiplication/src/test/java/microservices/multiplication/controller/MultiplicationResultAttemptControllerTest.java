package microservices.multiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;


import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import microservices.multiplication.controller.MultiplicationResultAttemptController.ResultResponse;
import microservices.multiplication.domain.Multiplication;
import microservices.multiplication.domain.MultiplicationResultAttempt;
import microservices.multiplication.domain.User;
import microservices.multiplication.event.MultiplicationSolvedEvent;
import microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.multiplication.service.MultiplicationService;


@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {
	
	@MockBean
	private MultiplicationService multiplicationService;
	
	@Mock
	private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
	
	@Autowired
	private MockMvc mvc;
	
	private JacksonTester<MultiplicationResultAttempt> jsonResult;
	private JacksonTester<List<MultiplicationResultAttempt>> jsonResultList;
	//private JacksonTester<ResultResponse> jsonResponse;
	
	@Before
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false));
	}
	
	
	@Test
	public void getUserStats() throws Exception {
		// given
		User user = new User("john_doe");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
		List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
		given(multiplicationService.getStatsForUser("john_doe")).willReturn(recentAttempts);
		
		// when
		MockHttpServletResponse response = mvc.perform(get("/results").param("alias", "john_doe")).andReturn().getResponse();
		
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResultList.write(recentAttempts).getJson());
	}
	
	@Test
	public void postResultReturnCorrect() throws Exception {
		genericParameterizedTest(true);
	}
	
	@Test
	public void postResultReturnNotCorrect() throws Exception {
		genericParameterizedTest(false);
	}
	
	@Test
	public void getResultByIdTest() throws Exception {
		// given		
		
		// model the user, the multiplication, and the attempt 
		User user = new User("john_doe");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
		
		// this is the test to the method 
		given(multiplicationService.getResultById(4L)).willReturn((attempt));
		
		// when 
		MockHttpServletResponse response = mvc.perform(get("/results/4")).andReturn().getResponse();
	
		// then 
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResult.write(attempt).getJson());
		
	}
	
	public void genericParameterizedTest(final boolean correct) throws Exception {
		
		// given
		when(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).thenReturn(correct);
		//note that given(any()) implementation is deprecated - use when / thenReturn 
		
		
		User user = new User("brian");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);
		
		
		// when
		MockHttpServletResponse response = mvc.perform(post("/results").
				contentType(MediaType.APPLICATION_JSON).
				characterEncoding("utf-8").
				content(jsonResult.write(attempt).
						getJson())).andReturn().getResponse();
		
		
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonResult.write(
						new MultiplicationResultAttempt(
								attempt.getUser(), attempt.getMultiplication(), 
								attempt.getResultAttempt(), correct)).getJson());
		
	}
	
	
	// model the event off of the above info from the attempt 
	// MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(), attempt.getUser().getId(), attempt.isCorrect());
	
}
