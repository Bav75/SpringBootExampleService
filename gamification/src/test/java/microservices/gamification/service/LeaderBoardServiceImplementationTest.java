package microservices.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import microservices.gamification.domain.LeaderBoardRow;
import microservices.gamification.repository.ScoreCardRepository;


public class LeaderBoardServiceImplementationTest {
	
	@Autowired
	private LeaderBoardService leaderBoardService;
	
	@Mock
	private ScoreCardRepository scoreCardRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		leaderBoardService = new LeaderBoardServiceImplementation(scoreCardRepository);
	}

	@Test
	public void getCurrentLeaderBoardTest() {	
		// given
		Long userId = 1L;
		LeaderBoardRow leaderRow1 = new LeaderBoardRow(userId, 300L);
		List<LeaderBoardRow> expectedLeaderBoardList = Collections.singletonList(leaderRow1);
		
		given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoardList);
		
		// when 
		
		List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();
		
		// then 
		
		assertThat(leaderBoard).isEqualTo(expectedLeaderBoardList);
		
	}
	
	/**
	
	private void loadScoreCardsAndCreateLeaderBoardRows(int n) {
		
		Long userId;
		Long attemptId;
		
		for(Long i = (long) 0; i < n; i++) {
			userId = i;
			attemptId = i + i;
			scoreCardRepository.save(new ScoreCard(userId, attemptId));
			createNLeaderBoardRows(1, userId, (long) scoreCardRepository.getTotalScoreForUser(userId));
		}						
	}
	
	private List<LeaderBoardRow> createNLeaderBoardRows(int n, Long userId, Long score) {
		return IntStream.range(0, n)
				.mapToObj(i -> new LeaderBoardRow(userId, score))
				.collect(Collectors.toList());
	}
	
	*/
	
	
	
}
