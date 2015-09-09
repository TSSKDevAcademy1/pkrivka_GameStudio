package entity;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Named
@Entity
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String name;

	public Game() {
	}

	public Game(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
