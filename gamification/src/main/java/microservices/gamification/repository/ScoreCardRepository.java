package microservices.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import microservices.gamification.domain.ScoreCard;
import microservices.gamification.domain.LeaderBoardRow;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long>{
	
	@Query("SELECT SUM(s.score) FROM microservices.gamification.domain.ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
	public int getTotalScoreForUser(@Param("userId") final Long userId);

	@Query("SELECT NEW microservices.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) " + 
			"FROM microservices.gamification.domain.ScoreCard s " + 
			"GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	public List<LeaderBoardRow> findFirst10();
	
	public List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
	
	
}