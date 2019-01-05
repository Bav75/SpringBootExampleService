package microservices.multiplication.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import microservices.multiplication.domain.Multiplication;
import microservices.multiplication.domain.MultiplicationResultAttempt;
import microservices.multiplication.domain.User;
import microservices.multiplication.event.EventDispatcher;
import microservices.multiplication.event.MultiplicationSolvedEvent;
import microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.multiplication.repository.UserRepository;

@Service
public class MultiplicationServiceImplementation implements MultiplicationService {
	
	private RandomGeneratorService randomGeneratorService;
	private MultiplicationResultAttemptRepository attemptRepository;
	private UserRepository userRepository;  
	private EventDispatcher eventDispatcher;
	
	@Autowired
	public MultiplicationServiceImplementation(final RandomGeneratorService randomGeneratorService,
			final MultiplicationResultAttemptRepository attemptRepository, final UserRepository userRepository,
			final EventDispatcher eventDispatcher) {
		this.randomGeneratorService = randomGeneratorService;
		this.attemptRepository = attemptRepository;
		this.userRepository = userRepository;
		this.eventDispatcher = eventDispatcher;
	}
	
	
	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);	
	}
	
	@Transactional
	@Override
	public boolean checkAttempt(final MultiplicationResultAttempt resultAttempt) {
		
		Optional<User> user = userRepository.findByAlias(resultAttempt.getUser().getAlias());
		
		boolean correct = resultAttempt.getResultAttempt() == resultAttempt.getMultiplication().getFactorA()*
				resultAttempt.getMultiplication().getFactorB();
		
		Assert.isTrue((!resultAttempt.isCorrect()), "You can't send an attempt marked as correct.");
		
		MultiplicationResultAttempt checkedAttempt = 
				new MultiplicationResultAttempt(user.orElse(resultAttempt.getUser()), 
						resultAttempt.getMultiplication(), resultAttempt.getResultAttempt(), correct);
		
		attemptRepository.save(checkedAttempt);
		
		eventDispatcher.send(new MultiplicationSolvedEvent(checkedAttempt.getId(),
				checkedAttempt.getUser().getId(),
				checkedAttempt.isCorrect()));
		
		return correct;
	}
	
	@Override
	public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
		return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}
	
	@Override
	public MultiplicationResultAttempt getResultById(final Long resultAttemptId) {
		return attemptRepository.findById(resultAttemptId).orElse(null);
	}
	
}
	
