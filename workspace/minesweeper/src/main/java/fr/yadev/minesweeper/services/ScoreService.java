package fr.yadev.minesweeper.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.yadev.minesweeper.model.GameMode;
import fr.yadev.minesweeper.model.Score;
import fr.yadev.minesweeper.model.Tile;
import fr.yadev.minesweeper.repository.GameModeRepository;
import fr.yadev.minesweeper.repository.ScoreRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ScoreService {
	@Autowired
	private ScoreRepository scores;
	
	@Autowired GameModeRepository gamemodes;
	
	public Comparator<Score> compareScore = new Comparator<Score>() {
	    @Override
	    public int compare(Score s1, Score s2) {
	        return s1.getTime().compareTo(s2.getTime());
	    }
	};
	
	public Comparator<GameMode> compareDeleted = new Comparator<GameMode>() {
		@Override
		public int compare(GameMode g1, GameMode g2) {
			return Boolean.compare(g1.isDeleted(), g2.isDeleted());
		}
	};
	
	public List<HighScore> getHighScores(int nb){
		List<HighScore> h_scores = new ArrayList<>();
		List<Score> sc = scores.findAll();
		Collections.sort(sc, compareScore);
		
		for(GameMode gm : gamemodes.findAll()) {
			int cpt = 0;
			for(Score score : sc) {
				if(score.getGamemode().getId() == gm.getId()) {
					h_scores.add(new HighScore(gm.getTitle(), score.getUsername(), score.getTime().toString()));
					++cpt;
				}
				
				if(cpt == nb) {
					break;
				}
			}
		}
		
		return h_scores;
	}
	
	public List<GameMode> getRelevantGameModes(){
		List<GameMode> gm = new ArrayList<>();
		List<Score> sc = scores.findAll();
		
		for(Score s : sc) {
			if(!gm.contains(s.getGamemode()))
				gm.add(s.getGamemode());
		}
		
		Collections.sort(gm, compareDeleted);
		
		return gm;
	}
	
	@Getter
	@Setter
	public class HighScore{
		private String gamemode;
		private String playerName;
		private String score;
		
		public HighScore(String gm, String player, String score) {
			this.setGamemode(gm);
			this.setPlayerName(player);
			this.setScore(score);
		}
		
		public String getScoreString() {
			return playerName + " - Avec un temps de : " + score + " secondes.";
		}
	}
}
