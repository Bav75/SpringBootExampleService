package microservices.multiplication.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import microservices.multiplication.service.RandomGeneratorServiceImplementation;

public class RandomGeneratorServiceImplementationTest {
	
	private RandomGeneratorServiceImplementation randomGeneratorServiceImplementation;
	
	@Before
	public void setUp() {
		randomGeneratorServiceImplementation = new RandomGeneratorServiceImplementation();
	}
	
	@Test
	public void generateRandomFactorIsBetweenExpectedLimits() throws Exception {
		List<Integer> randomFactors = IntStream.range(0,  1000)
				.map(i -> randomGeneratorServiceImplementation.generateRandomFactor())
				.boxed().collect(Collectors.toList());
		
		assertThat(randomFactors).containsOnlyElementsOf
		(IntStream.range(11, 100).boxed().collect(Collectors.toList()));	
	}
	
	
	
}
