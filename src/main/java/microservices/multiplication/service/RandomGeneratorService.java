package microservices.multiplication.service;

public interface RandomGeneratorService {
	
	/**
	 * @return a randomly-generated factor. Always a number between 11 and 99
	 */

	public int generateRandomFactor();
	
}
