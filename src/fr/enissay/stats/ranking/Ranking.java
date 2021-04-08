package fr.enissay.stats.ranking;

import java.util.UUID;

public class Ranking {
	
    private Integer score;
    private Integer rank;
    private UUID uuid;
    
	public Ranking(Integer rank, Integer score, UUID uuid) {
		this.score = score;
		this.rank = rank;
		this.uuid = uuid;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public Integer getRank() {
		return rank;
	}

	public UUID getUUID() {
		return uuid;
	}
}
