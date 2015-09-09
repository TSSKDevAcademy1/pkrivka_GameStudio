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
			return "register.jsf";
		}
	}

	public String showRegister() {
		checkRegister = true;
		return "register.jsf";
	}

	public String backToMenu() {
		checkData = false;
		return "index.jsf";
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
