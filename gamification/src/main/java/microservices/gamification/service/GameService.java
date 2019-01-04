package microservices.gamification.service;

import microservices.gamification.domain.GameStats;

public interface GameService {
	
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);
	
	public GameStats retrieveStatsForUser(Long userId);

}
