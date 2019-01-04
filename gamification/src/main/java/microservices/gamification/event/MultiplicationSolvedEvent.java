package microservices.gamification.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author bav
 *Event that models that a {@link microservices.multiplicaiton.
 *domain.Multiplication} 
 *has been solved in the system. Provides some context information
 *about the multiplication.
 *
 */


@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {
	
	private final Long multiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;
	
	

}
