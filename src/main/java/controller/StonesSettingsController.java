package controller;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;

import entity.User;
import minesweeper.MinesField;
import stones.StonesField;

@Model
public class StonesSettingsController {

	@Inject
	FacesContext fc;
	@Inject
	UserController userController;
	@Inject
	StonesController stonesController;
	@Inject
	User user;

	private int stonesNumRows = 0;
	private int stonesNumCols = 0;

	public void setup() {
		StonesField fieldStone = new StonesField(stonesNumRows, stonesNumCols);
		HttpSession http = (HttpSession) fc.getExternalContext().getSession(true);
		http.setAttribute("fieldStone", fieldStone);
		stonesController.setWinState(false);
		if (userController.isLogged()){
		userController.setStonesForUser(user.getName(), user.getPasswd(), stonesNumRows, stonesNumCols);
		}
	}

	public int getNumberRows() {
		return stonesNumRows;
	}

	public void setNumberRows(int numberRows) {
		this.stonesNumRows = numberRows;
	}

	public int getNumberCols() {
		return stonesNumCols;
	}

	public void setNumberCols(int numberCols) {
		this.stonesNumCols = numberCols;
	}

}
