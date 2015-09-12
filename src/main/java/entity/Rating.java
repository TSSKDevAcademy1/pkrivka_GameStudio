package entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rating {

	@Id
	@GeneratedValue
	private long id;
	private int rating;
	@ManyToOne(cascade = CascadeType.ALL)
	private Game game;

	public Rating() {
	}

	public Rating(int rating, Game game) {
		this.rating = rating;
		this.game = game;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
