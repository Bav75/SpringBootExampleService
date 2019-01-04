package microservices.gamification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices.gamification.domain.LeaderBoardRow;
import microservices.gamification.repository.ScoreCardRepository;

@Service
@Slf4j
public class LeaderBoardServiceImplementation implements LeaderBoardService {
	
	private ScoreCardRepository scoreCardRepository;
	
	public LeaderBoardServiceImplementation(ScoreCardRepository scoreCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
	}
	
	
	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
