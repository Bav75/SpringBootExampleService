package microservices.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class MultiplicationResultAttempt {
	
	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt; 
	private final boolean correct;
	
	
	// Empty constructor 
	public MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
		correct = false;
	}
	
	/*
	public MultiplicationResultAttempt(User user, Multiplication multiplication, int resultAttempt) {
		this.user = user;
		this.multiplication = multiplication;
		this.resultAttempt = resultAttempt;
		
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
	
	*/

}
