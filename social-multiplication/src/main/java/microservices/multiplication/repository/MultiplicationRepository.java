package microservices.multiplication.repository;

import org.springframework.data.repository.CrudRepository;

import microservices.multiplication.domain.Multiplication;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
		
}
	