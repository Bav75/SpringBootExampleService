package microservices.gamification.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class GameStats {
	
	private final Long userId;
	private final int score;
	private final List<Badge> badges;
	
	// Empty constructor for JSON 
	public GameStats() {
		this.userId = 0L;
		this.score = 0;
		this.badges = new ArrayList<>();
	}
	
	// Factory method to build empty instance
	public static GameStats emptyStats(final Long userId) {
		return new GameStats(userId, 0, Collections.emptyList());
	}
	
	// Return unmodifiable view of badge cards 
	public List<Badge> getBadges() {
		return Collections.unmodifiableList(badges);
	}
	
	
	
}
