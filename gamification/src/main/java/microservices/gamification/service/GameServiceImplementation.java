package microservices.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices.gamification.domain.Badge;
import microservices.gamification.domain.BadgeCard;
import microservices.gamification.domain.GameStats;
import microservices.gamification.domain.ScoreCard;
import microservices.gamification.repository.BadgeCardRepository;
import microservices.gamification.repository.ScoreCardRepository;

@Service	
@Slf4j
public class GameServiceImplementation implements GameService {
	
	private BadgeCardRepository badgeCardRepository;
	private ScoreCardRepository scoreCardRepository;
	
	public GameServiceImplementation(BadgeCardRepository badgeCardRepository, ScoreCardRepository scoreCardRepository) {
		this.badgeCardRepository = badgeCardRepository;
		this.scoreCardRepository = scoreCardRepository;
	}
	
	@Override
	public GameStats newAttemptForUser(final Long userId, final Long attemptId, final boolean correct) {
		if(correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id {}",
					userId, scoreCard.getScore(), attemptId);
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
			return new GameStats(userId, scoreCard.getScore(), badgeCards.stream().
					map(BadgeCard::getBadge).collect(Collectors.toList()));
		}
		return GameStats.emptyStats(userId);
	}
	
	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		int score = scoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}
	
	public List<BadgeCard> processForBadges(final Long userId, final Long attemptId) {
		
		List<BadgeCard> badgeCards = new ArrayList<>();
		
		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("New score for user {} is {}", userId, totalScore);
		
		List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		
		// issue badges depending on score 
		checkAndGiveBadgeBasedOnScore(badgeCards, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId).ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCards, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId).ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCards, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId).ifPresent(badgeCards::add);
		
		// first won badge 
		
		if(scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
			badgeCards.add(firstWonBadge);
		}
		return badgeCards;
	}
	
	/**
	 * 
	 * @param badgeCards
	 * @param badge
	 * @param score
	 * @param scoreThreshold
	 * @param userId
	 * @return Optional
	 * 
	 * Convenience method for checking current list of badgeCards and assigning new badges based on the score 
	 * achieved. 
	 * 
	 */
	
	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge, final int score,
			final int scoreThreshold, final Long userId) {
		if(score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}
	
	
	/**
	 * 
	 * @param badgeCards
	 * @param badge
	 * @return boolean true/false 
	 * 
	 * Checks if the list of badgeCards being passed in contains the badge being checked. 
	 */
	
	private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge) {
		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
	}
	
	/**
	 * 
	 * @param badge
	 * @param userId
	 * @return badgeCard
	 * 
	 * Assigns a new badge to the user. 
	 * 
	 */
	
	
	private BadgeCard giveBadgeToUser(final Badge badge, final Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		log.info("User with id {} won new badge: {}", userId, badge);
		return badgeCard;
	}
	

}
