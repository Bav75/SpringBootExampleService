package microservices.multiplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.assertj.core.internal.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.multiplication.domain.Multiplication;
import microservices.multiplication.domain.MultiplicationResultAttempt;
import microservices.multiplication.domain.User;
import microservices.multiplication.event.EventDispatcher;
import microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.multiplication.repository.UserRepository;
import microservices.multiplication.service.MultiplicationServiceImplementation;
import microservices.multiplication.service.RandomGeneratorService;

public class MultiplicationServiceImplementationTest {
	
	private MultiplicationServiceImplementation multiplicationServiceImplementation;
	
	@Mock
	private RandomGeneratorService randomGeneratorService;
	
	@Mock
	private MultiplicationResultAttemptRepository attemptRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private EventDispatcher eventDispatcher;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		multiplicationServiceImplementation = new MultiplicationServiceImplementation(randomGeneratorService, attemptRepository, userRepository, eventDispatcher);
	}
	
	
	@Test
	public void createRandomMultiplication() {
		// given (our mocked Random Generator service will return first 50, then 30)
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
		
		// when
		Multiplication multiplication = multiplicationServiceImplementation.createRandomMultiplication();
		
		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		// assertThat(multiplication.getResult()).isEqualTo(1500);
}
	@Test
	public void checkCorrectAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		
		MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
		
		given(userRepository.findByAlias("john doe")).willReturn(Optional.empty());
		
		// when
		boolean attemptResult = multiplicationServiceImplementation.checkAttempt(attempt);
		
		// assert 
		assertThat(attemptResult).isTrue();
		verify(attemptRepository).save(verifiedAttempt);
		
	}
	
	@Test
	public void checkWrongAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, false);
		
		given(userRepository.findByAlias("john doe")).willReturn(Optional.empty());
		
		// when
		boolean attemptResult = multiplicationServiceImplementation.checkAttempt(attempt);
		
		// assert 
		assertThat(attemptResult).isFalse();
		verify(attemptRepository).save(attempt);

	}
	
	@Test
	public void retrieveStatsTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
		List<MultiplicationResultAttempt> latestAttempts = org.assertj.core.util.Lists.newArrayList(attempt1, attempt2);
		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe")).willReturn(latestAttempts);

		// when
		List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImplementation.getStatsForUser("john_doe");
		
		// then
		assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
		
		
	}
	
	
}
