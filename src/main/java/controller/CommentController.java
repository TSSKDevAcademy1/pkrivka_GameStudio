package controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import entity.Comment;
import entity.Rating;
import services.CommentService;
import services.GameService;
import services.RatingService;

@Named
public class CommentController {

	@Inject
	CommentService commentService;
	@Inject
	GameService gameService;
	@Inject
	Comment comment;

	public String addComment(String gameName, String userName, String userPassword) {
		String comm = comment.getComment();
		commentService.addComment(comm, gameName, userName, userPassword);
		switch (gameName) {
		case "mines":
			return "minesComments.jsf";
		case "stones":
			return "stonesComments.jsf";
		case "akinator":
			return "akinatorComments.jsf";
		default:
			return null;
		}
	}

	public List<Comment> getAllCommentsToGame(String gameName) {
		long id = gameService.getGameId(gameName);
		List<Comment> comments = commentService.getComment(id);
		return comments;
	}

}
