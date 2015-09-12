package controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Score;
import services.GameService;
import services.ScoreService;
import java.io.Serializable;

@Named
@SessionScoped
public class ScoreController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	ScoreService scoreservice;
	@Inject
	GameService gameservice;
	private String gameToShowScore;

	public void addScore(int steps, String name, String password, String nameGame) {
		scoreservice.addScore(steps, name, password, nameGame);
	}
	
	public void addScore(long time, String name, String password, String nameGame) {
		scoreservice.addScore(time, name, password, nameGame);
	}

	public List<Score> getScores(String nameGame) {
		long id = gameservice.getGameId(nameGame);
		return scoreservice.getScores(id);
	}
	
	public String showScoreInGame(String gameName){
		gameToShowScore=gameName;
		return "gameScore.jsf";
	}
	
	public String getGameToShowScore() {
		return gameToShowScore;
	}
	
	public String deleteScore(long id){
		scoreservice.deleteScore(id);
		return "gameScore.jsf";
	}

}
