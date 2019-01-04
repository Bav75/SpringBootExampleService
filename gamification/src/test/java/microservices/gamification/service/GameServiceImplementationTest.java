package microservices.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import microservices.gamification.domain.Badge;
import microservices.gamification.domain.BadgeCard;
import microservices.gamification.domain.GameStats;
import microservices.gamification.domain.ScoreCard;
import microservices.gamification.repository.BadgeCardRepository;
import microservices.gamification.repository.ScoreCardRepository;


public class GameServiceImplementationTest {
	
	/**
	 * Tests the GameService which calculates scores and issues badges.
	 * 
	 */
	
	@Autowired
	private GameService gameService;
	
	@Mock
	private ScoreCardRepository scoreCardRepository; 
	
	@Mock
	private BadgeCardRepository badgeCardRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		gameService = new GameServiceImplementation(badgeCardRepository, scoreCardRepository);
	}
	
	@Test
	public void processFirstCorrectAttemptTest() {
		
		// given
		Long userId = 1L;
		Long attemptId = 2L;
		int totalScore = 10;
		
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());
		
		
		// when
		
		GameStats statUpdate =  gameService.newAttemptForUser(userId, attemptId, true);
		
		// then 
		
		assertThat(statUpdate.getScore()).isEqualTo(scoreCard.getScore());
		assertThat(statUpdate.getBadges()).containsOnly(Badge.FIRST_WON);
		
	}
	
	@Test
	public void ScoreBadgeTest() {
		
		// given
		Long userId = 1L;
		Long attemptId = 2L;
		int totalScore = 100;
		
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		// BadgeCard firstAttemptBadge = new BadgeCard(userId, Badge.FIRST_ATTEMPT);
				
		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(createNScoreCards(10, userId));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.singletonList(firstWonBadge));
		
		// when
		
		GameStats statUpdate = gameService.newAttemptForUser(userId, attemptId, true);
		
		// then
		
		assertThat(statUpdate.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
		assertThat(statUpdate.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
		
	}
	
	@Test
	public void processWrongAttemptTest() {
		
		// given
		Long userId = 1L;
		Long attemptId = 2L;
		int totalScore = 10;
		
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);

		given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.emptyList());
		
		// when
		
		GameStats statUpdate = gameService.newAttemptForUser(userId, attemptId, false);
		
		// then 
		
		assertThat(statUpdate.getScore()).isEqualTo(0);
		assertThat(statUpdate.getBadges()).isEmpty();
		
	}
	
	/* @Test
	public void processFirstAttemptTest() {
		
	}
	
	*/
	
    private List<ScoreCard> createNScoreCards(int n, Long userId) {
        return IntStream.range(0, n)
                .mapToObj(i -> new ScoreCard(userId, (long)i))
                .collect(Collectors.toList());
    }
}
