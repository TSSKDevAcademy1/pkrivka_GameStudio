package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.Game;
import entity.User;
import entity.Comment;

@Stateless
public class CommentService {

	@Inject
	private EntityManager em;
	@Inject
	GameService gameService;
	@Inject
	UserService userService;

	public void addComment(String comment, String gameName, String userName, String userPassword) {
		User user=userService.getUser(userName, userPassword);
		Game game = gameService.getGame(gameName);
		Comment Comment = new Comment(comment, game, user);
		em.persist(Comment);
	}

	public List<Comment> getComment(long gameId) {
		List<Comment> comments = em.createQuery("select c from Comment c join c.game g where g.id=:gameId", Comment.class)
				.setParameter("gameId", gameId).getResultList();
		return comments;
	}

}
