package microservices.multiplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import microservices.multiplication.domain.MultiplicationResultAttempt;
import microservices.multiplication.service.MultiplicationService;

@RestController
@RequestMapping("/results")
public final class MultiplicationResultAttemptController {
	
	private final MultiplicationService multiplicationService;
	
	@Autowired
	public MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
		this.multiplicationService = multiplicationService;
	}
	
	
	@PostMapping
	ResponseEntity<ResultResponse> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
		return ResponseEntity.ok(
				new ResultResponse(multiplicationService.checkAttempt(multiplicationResultAttempt)));
	}
	
	@RequiredArgsConstructor
	@NoArgsConstructor(force = true)
	@Getter
	public static final class ResultResponse {
		private final boolean correct;
		
		/*
		public ResultResponse() {
			correct = false;
		}

		public boolean isCorrect() {
			return correct;
		}
		
		public ResultResponse(boolean correct) {
			this.correct = correct; 
		}
		*/
		
	}
	
	
	

}
