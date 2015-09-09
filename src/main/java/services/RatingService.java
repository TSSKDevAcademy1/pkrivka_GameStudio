package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.Game;
import entity.Rating;

@Stateless
public class RatingService {

	@Inject
	private EntityManager em;
	@Inject
	GameService gameservice;

	public void addComment(int rate, String gamename) {
		Game game = gameservice.getGame(gamename);
		Rating rating = new Rating(rate, game);
		em.persist(rating);
	}

	public List<Rating> getRating(long gameId) {
		List<Rating> ratings = em.createQuery("select r from Rating r join r.game g where g.id=:gameId", Rating.class)
				.setParameter("gameId", gameId).getResultList();
		return ratings;
	}

}
