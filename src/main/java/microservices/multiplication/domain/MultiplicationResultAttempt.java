package microservices.multiplication.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class MultiplicationResultAttempt {
	
	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt; 
	
	
	public MultiplicationResultAttempt(User user, Multiplication multiplication, int resultAttempt) {
		this.user = user;
		this.multiplication = multiplication;
		this.resultAttempt = resultAttempt;
		
	}
	
	// Empty constructor 
	public MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
	}

	public User getUser() {
		return user;
	}

	public Multiplication getMultiplication() {
		return multiplication;
	}

	public int getResultAttempt() {
		return resultAttempt;
	}
	
	

}
