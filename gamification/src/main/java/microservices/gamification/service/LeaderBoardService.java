package microservices.gamification.service;

import java.util.List;

import microservices.gamification.domain.LeaderBoardRow;

public interface LeaderBoardService {
	
	public List<LeaderBoardRow> getCurrentLeaderBoard();

}
