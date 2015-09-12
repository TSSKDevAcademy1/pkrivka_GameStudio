package controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class MinesController implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean newGame = false;
	private boolean winState = false;

	public String startNewGame() {
		newGame = true;
		return "mines.jsf";
	}

	public boolean isNewGame() {
		return newGame;
	}

	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

	public boolean isWinState() {
		return winState;
	}

	public void setWinState(boolean winState) {
		this.winState = winState;
	}

}
