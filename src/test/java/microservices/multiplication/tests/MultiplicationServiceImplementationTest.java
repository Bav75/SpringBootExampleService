package microservices.multiplication.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.multiplication.domain.Multiplication;
import microservices.multiplication.service.MultiplicationServiceImplementation;
import microservices.multiplication.service.RandomGeneratorService;

public class MultiplicationServiceImplementationTest {
	
	private MultiplicationServiceImplementation multiplicationServiceImplementation;
	
	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		multiplicationServiceImplementation = new MultiplicationServiceImplementation(randomGeneratorService);
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
		assertThat(multiplication.getResult()).isEqualTo(1500);
}
	
}
