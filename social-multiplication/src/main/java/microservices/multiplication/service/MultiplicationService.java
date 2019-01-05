package microservices.multiplication.service;

import java.util.List;
import java.util.Optional;

import microservices.multiplication.domain.Multiplication;
import microservices.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
	
	/**
	 * Creates a Multiplication object with two randomly-generated factors
	 * between 11 and 99.
	 * 
	 * @return a Multiplication object with random factors
	 */

	public Multiplication createRandomMultiplication();
	
	public boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
	
	public List<MultiplicationResultAttempt> getStatsForUser(final String userAlias);
	
	public MultiplicationResultAttempt getResultById(final Long resultAttemptId);
	
}
