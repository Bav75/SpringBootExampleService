package microservices.multiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
@Entity
public final class Multiplication {
	
	@Id
	@GeneratedValue
	@Column(name = "MULTIPLICATION_ID")
	private Long id;
	

	private final int factorA;
	private final int factorB;
		
	
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
