package microservices.multiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import microservices.multiplication.service.MultiplicationService;


@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {
	
	@MockBean
	private MultiplicationService multiplicationService;
	
	@Autowired
	private MockMvc mvc;
	
	private JacksonTester<MultiplicationResultAttempt> jsonResult;
	private JacksonTester<ResultResponse> jsonResponse;
	
	@Before
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false));
	}
	
	
	@Test
	public void postResultReturnCorrect() throws Exception {
		genericParameterizedTest(true);
	}
	
	@Test
	public void postResultReturnNotCorrect() throws Exception {
		genericParameterizedTest(false);
	}
	
	public void genericParameterizedTest(final boolean correct) throws Exception {
		
		// given
		when(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).thenReturn(correct);
		//note that given(any()) implementation is deprecated - use when / thenReturn 
		
		
		User user = new User("brian");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500);
		
		
		// when
		MockHttpServletResponse response = mvc.perform(post("/results").
				contentType(MediaType.APPLICATION_JSON).
				characterEncoding("utf-8").
				content(jsonResult.write(attempt).
						getJson())).andReturn().getResponse();
		
		// my mvc is continuing to return false - why is this? 
		
		System.out.println("MVC Result " + response.getContentAsString());
		System.out.println("Result Response " + jsonResponse.write(new ResultResponse(correct)).getJson());
		
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonResponse.write(new
						ResultResponse(correct)).getJson());
		
	}
	
}
