package services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.Game;

@Stateless
public class GameService {

	@Inject
	private EntityManager em;

	public Game getGame(String name) {
		try {
			return em.createQuery("select g from Game g where g.name=:name", Game.class).setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			Game game = new Game(name);
			em.persist(game);
			return game;
		}
	}

	public void addGame(String name) {
		Game game = new Game(name);
		em.persist(game);
	}

	public long getGameId(String nameGame) {
		try {
			return em.createQuery("select g.id from Game g where g.name=:nameGame", Long.class)
					.setParameter("nameGame", nameGame).getSingleResult();
		} catch (Exception e) {
			Game game = new Game(nameGame);
			em.persist(game);
			return em.createQuery("select g.id from Game g where g.name=:nameGame", Long.class)
					.setParameter("nameGame", nameGame).getSingleResult();
		}

	}

}
