package entity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@ApplicationScoped
public class Score {

	@Id
	@GeneratedValue
	private long id;
	private long score;
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	@ManyToOne(cascade = CascadeType.ALL)
	private Game game;

	public Score() {
	}

	public Score(long score, User user, Game game) {
		this.score = score;
		this.user = user;
		this.game = game;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
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
	
	public long getId() {
		return id;
	}

}
