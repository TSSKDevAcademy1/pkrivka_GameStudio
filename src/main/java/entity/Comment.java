package entity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Named
@Entity
@RequestScoped
public class Comment {

	@Id
	@GeneratedValue
	private long id;
	private String comment;
	@ManyToOne(cascade = CascadeType.ALL)
	private Game game;
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	public Comment() {
	}

	public Comment(String comment, Game game, User user) {
		this.comment = comment;
		this.game = game;
		this.user = user;
	}
	
	public long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
