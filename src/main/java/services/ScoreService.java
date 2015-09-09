package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.Game;
import entity.Score;
import entity.User;

@Stateless
public class ScoreService {

	@Inject
	private EntityManager em;
	@Inject
	private UserService userservice;
	@Inject
	private GameService gameService;

	public void addScore(long time, String name, String password, String gamename) {
		User user = userservice.getUser(name, password);
		Game game = gameService.getGame(gamename);
		Score score = new Score(time, user, game);
		em.persist(score);
	}

	public List<Score> getScores(long gameId) {
		List<Score> scores = em.createQuery("select s from Score s join s.game g where g.id=:gameId order by score", Score.class)
				.setParameter("gameId", gameId).setMaxResults(10).getResultList();
		return scores;
	}

}
