package controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Rating;
import services.GameService;
import services.RatingService;
import java.io.Serializable;

@Named
@SessionScoped
public class RatingController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	RatingService ratingService;
	@Inject
	GameService gameService;
	private String gameToRate;

	public String addRating(int rate, String gameName) {
		ratingService.addRating(rate, gameName);
		switch (gameName) {
		case "mines":
			return "mines.jsf";
		case "stones":
			return "stones.jsf";
		case "akinator":
			return "akinator.jsf";
		default:
			return null;
		}
	}

	public int getRating(String gameName) {
		int rating = 0;
		long id = gameService.getGameId(gameName);
		List<Rating> ratings = ratingService.getRating(id);
		if (ratings.iterator().hasNext()) {
			for (int i = 0; i < ratings.size(); i++) {
				rating += ratings.get(i).getRating();
			}
			rating = Math.round(rating / ratings.size());
		}
		return rating;
	}

	public boolean isRated(String gameName) {
		int rating = getRating(gameName);
		if (rating == 0) {
			return false;
		} else
			return true;
	}

	public String rateGame(String gameName) {
		gameToRate = gameName;
		return "rateGame.jsf";
	}

	public String getGameToRate() {
		return gameToRate;
	}

}
