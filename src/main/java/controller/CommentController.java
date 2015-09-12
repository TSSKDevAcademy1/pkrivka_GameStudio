package controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Comment;
import services.CommentService;
import services.GameService;
import java.io.Serializable;

@Named
@SessionScoped
public class CommentController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	CommentService commentService;
	@Inject
	GameService gameService;
	@Inject
	Comment comment;
	private String gameToComment;

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
		List<Comment> comments = commentService.getComments(id);
		return comments;
	}

	public String commentGame(String gameName) {
		gameToComment = gameName;
		return "gameComments.jsf";
	}
	
	public String deleteComment(long id){
		commentService.deleteComment(id);
		return "gameComments.jsf";
	}

	public String getGameToComment() {
		return gameToComment;
	}
	
}
