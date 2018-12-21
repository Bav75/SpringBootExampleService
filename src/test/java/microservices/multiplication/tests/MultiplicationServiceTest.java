package microservices.multiplication.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import microservices.multiplication.domain.Multiplication;
import microservices.multiplication.service.MultiplicationService;
import microservices.multiplication.service.RandomGeneratorService;

import static org.mockito.BDDMockito.given; // what are alternatives to this? 
import static org.assertj.core.api.Assertions.assertThat; // why are these imported statically? 

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiplicationServiceTest {
	
	@MockBean
	private RandomGeneratorService randomGeneratorService;
	
	@Autowired
	private MultiplicationService multiplicationService;
	
	@Test
	public void createRandomMultiplicationTest() {
		// given (our mocked Random Generator service will return first 50, then 30)
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
		
		// when
		Multiplication multiplication = multiplicationService.createRandomMultiplication();
		
		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorA()).isEqualTo(30);
		assertThat(multiplication.getFactorA()).isEqualTo(1500);		
	}
	
	
}
