/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.guessnumber;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Transient;

import controller.ScoreController;
import controller.UserController;
import entity.User;

@Named
@SessionScoped
public class UserNumberBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	transient private ScoreController scorecontroller;
	@Inject
	transient private UserController usercontroller;
	@Inject
	transient private User user;

	Integer randomInt = null;
	private Integer userNumber = null;
	String response = null;
	private int maximum = 10;
	private int minimum = 0;
	private boolean newGame = false;
	private boolean akinatorShowMenu = true;
	private int steps;

	public String startNewGame() {
		newGame = true;
		steps=1;
		Random randomGR = new Random();
		randomInt = new Integer(randomGR.nextInt(maximum + 1));
		System.out.println("Akinator's number: " + randomInt);
		akinatorShowMenu = false;
		return "akinator.jsf";
	}

	public String goToMenu() {
		if (newGame) {
			return "akinator.jsf";
		} else
			akinatorShowMenu = true;
		return "akinator.jsf";
	}
	
	public String endGame(){
		newGame = false;
		akinatorShowMenu = true;
		return "akinator.jsf";
	}

	public boolean isNewGame() {
		return newGame;
		
	}

	public void setUserNumber(Integer user_number) {
		userNumber = user_number;
	}

	public Integer getUserNumber() {
		return userNumber;
	}

	public String getResponse() {
		if ((userNumber == null) || (userNumber.compareTo(randomInt) != 0)) {
			if (userNumber != null) {
				if (userNumber < randomInt) {
					steps++;
					return "My number is bigger.";
				} else if (userNumber > randomInt) {
					steps++;
					return "My number is smaller.";
				}
			}
			return null;
			// else return "Sorry, " + userNumber + " is incorrect.";
		} else {
			// Random randomGR = new Random();
			// randomInt = new Integer(randomGR.nextInt(maximum + 1));
			newGame = false;
			if (usercontroller.isLogged()) {
				scorecontroller.addScore(steps, user.getName(), user.getPasswd(), "akinator");
			}
			return "You are awesome! You guessed my number on "+steps+". try.";
		}
	}

	public boolean isAkinatorShowMenu() {
		return akinatorShowMenu;
	}

	public int getMaximum() {
		return (this.maximum);
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getMinimum() {
		return (this.minimum);
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
}
