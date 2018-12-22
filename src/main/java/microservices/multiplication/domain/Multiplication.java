package microservices.multiplication.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Multiplication {
	
	// Factors for multiplication
	private final int factorA;
	private final int factorB;
	
	// The result of the operation A * B 
	private int result;
	
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
				"}";
	}

	
	
	
}
