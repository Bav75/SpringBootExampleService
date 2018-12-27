package microservices.multiplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import microservices.multiplication.domain.Multiplication;
import microservices.multiplication.domain.MultiplicationResultAttempt;

@Service
public class MultiplicationServiceImplementation implements MultiplicationService {
	
	private RandomGeneratorService randomGeneratorService;
	
	@Autowired
	public MultiplicationServiceImplementation(RandomGeneratorService randomGeneratorService) {
		this.randomGeneratorService = randomGeneratorService;
	}
	
	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}
	
	@Override
	public boolean checkAttempt(final MultiplicationResultAttempt resultAttempt) {
		
		boolean correct = resultAttempt.getResultAttempt() == resultAttempt.getMultiplication().getFactorA()*
				resultAttempt.getMultiplication().getFactorB();
		
		Assert.isTrue((!resultAttempt.isCorrect()), "You can't send an attempt marked as correct.");
		
		MultiplicationResultAttempt checkedAttempt = 
				new MultiplicationResultAttempt(resultAttempt.getUser(), 
						resultAttempt.getMultiplication(), resultAttempt.getResultAttempt(), correct);
		
		return correct;
		
		
	
	}

}
