package fr.enissay.stats;

import java.util.UUID;

public class PlayerStat {

	private UUID uuid;
	private int totalScore, bestScore;
	
	public PlayerStat(UUID uuid, int totalScore, int bestScore) {
		this.uuid = uuid;
		this.totalScore = totalScore;
		this.bestScore = bestScore;
	}
	
	public PlayerStat(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public int getBestScore() {
		return bestScore;
	}
	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
	
}
