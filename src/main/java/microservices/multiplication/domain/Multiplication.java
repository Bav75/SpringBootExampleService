package microservices.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public final class Multiplication {
	
	// Factors for multiplication
	private final int factorA;
	private final int factorB;
	//private final int result;
		
	
	public Multiplication() {
		this(0, 0);
	}
	
	
	/*
	public Multiplication(int factorA, int factorB) {
		this.factorA = factorA;
		this.factorB = factorB;
		this.result = factorA * factorB;
	}

	public int getFactorA() {
		return factorA;
	}


	public int getFactorB() {
		return factorB;
	}


	public int getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "Multiplication{" +
				"factorA=" + factorA +
				", factorB=" + factorB + 
				", result(A*B)=" + result + 
				'}';
	}

	*/
	
	
}
