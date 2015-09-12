package controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.User;
import services.UserService;

import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	User user;
	@Inject
	UserService userservice;
	private boolean logged = false;
	private boolean checkData = false;
	private boolean checkUser = false;
	private boolean checkRegister = false;

	public String signIn() {
		if (!userservice.findUser(user.getName(), user.getPasswd())) {
			user.setName(null);
			user.setPasswd(null);
			logged = false;
			checkData = true;
			checkRegister = true;
			return "register.jsf";
		} else if (user.getName() == null || user.getPasswd() == null) {
			checkRegister = true;
			return "register.jsf";
		} else {
			logged = true;
			checkData = false;
			checkRegister = false;
			return "index.jsf";
		}
	}

	public String signOut() {
		user.setName(null);
		user.setPasswd(null);
		logged = false;
		return "index.jsf";
	}

	public String register() {
		if (!userservice.findUser(user.getName(), user.getPasswd())) {
			userservice.addUser(user.getName(), user.getPasswd());
			logged = true;
			checkUser = false;
			checkRegister = false;
			return "index.jsf";
		} else {
			user.setName(null);
			user.setPasswd(null);
			logged = false;
			checkUser = true;
			checkRegister = true;
			return "register.jsf";
		}
	}

	public String showRegister() {
		checkRegister = true;
		return "register.jsf";
	}

	public String backToMenu() {
		checkData = false;
		checkRegister = false;
		checkUser = false;
		return "index.jsf";
	}

	public boolean checkAdmin(String name, String password) {
		return userservice.checkAdmin(name, password);
	}

	public void setMinesForUser(String name, String password, int minesNumRows, int minesNumCols, int minesNumMines) {
		userservice.setMinesForUser(name, password, minesNumRows, minesNumCols, minesNumMines);
	}

	public void setStonesForUser(String name, String password, int stonesNumRows, int stonesNumCols) {
		userservice.setStonesForUser(name, password, stonesNumRows, stonesNumCols);
	}

	public int getMinesRows(String name, String password) {
		return userservice.getMinesRows(name, password);
	}

	public int getMinesCols(String name, String password) {
		return userservice.getMinesCols(name, password);
	}

	public int getMinesMines(String name, String password) {
		return userservice.getMinesMines(name, password);
	}
	
	public int getStonesRows(String name, String password){
		return userservice.getStonesRows(name, password);
	}
	
	public int getStonesCols(String name, String password){
		return userservice.getStonesCols(name, password);
	}

	public boolean isLogged() {
		return logged;
	}

	public boolean isCheckData() {
		return checkData;
	}

	public boolean isCheckUser() {
		return checkUser;
	}

	public boolean isCheckRegister() {
		return checkRegister;
	}

}
