package controller;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;

import entity.User;
import minesweeper.MinesField;

@Model
public class MinesSettingsController {
	
	@Inject
	FacesContext fc;
	@Inject
	MinesController minesController;
	@Inject
	UserController userController;
	@Inject
	User user;
	
	private int minesNumRows=0;
	private int minesNumCols=0;
	private int minesNumMines=0;
	
	public void setup(){
		MinesField fieldMine=new MinesField(minesNumRows, minesNumCols, minesNumMines);
		HttpSession http=(HttpSession) fc.getExternalContext().getSession(true);
		http.setAttribute("fieldMine", fieldMine);
		minesController.setWinState(false);
		if (userController.isLogged()){
		userController.setMinesForUser(user.getName(), user.getPasswd(), minesNumRows, minesNumCols, minesNumMines);
		}
		
	}

	public int getNumberRows() {
		return minesNumRows;
	}

	public void setNumberRows(int numberRows) {
		this.minesNumRows = numberRows;
	}

	public int getNumberCols() {
		return minesNumCols;
	}

	public void setNumberCols(int numberCols) {
		this.minesNumCols = numberCols;
	}

	public int getNumberMines() {
		return minesNumMines;
	}

	public void setNumberMines(int numberMines) {
		this.minesNumMines = numberMines;
	}
	
	
}
