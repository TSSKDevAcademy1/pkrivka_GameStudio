package controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import entity.Score;
import services.GameService;
import services.ScoreService;

@Named
public class ScoreController {

	@Inject
	ScoreService scoreservice;
	@Inject
	GameService gameservice;

	public void addScore(long time, String name, String password, String nameGame) {
		scoreservice.addScore(time, name, password, nameGame);
	}

	public List<Score> getScores(String nameGame) {
		long id = gameservice.getGameId(nameGame);
		return scoreservice.getScores(id);
	}

}
