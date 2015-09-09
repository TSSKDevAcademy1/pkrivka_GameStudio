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
	private long akinatorStartTime = 0;
	private long akinatorTime = 0;
	private boolean akinatorShowMenu = true;

	public String startNewGame() {
		newGame = true;
		Random randomGR = new Random();
		randomInt = new Integer(randomGR.nextInt(maximum + 1));
		akinatorStartTime = System.currentTimeMillis();
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
					return "My number is bigger.";
				} else if (userNumber > randomInt) {
					return "My number is smaller.";
				}
			}
			return null;
			// else return "Sorry, " + userNumber + " is incorrect.";
		} else {
			// Random randomGR = new Random();
			// randomInt = new Integer(randomGR.nextInt(maximum + 1));
			newGame = false;
			akinatorTime = (System.currentTimeMillis() - akinatorStartTime) / 1000;
			if (usercontroller.isLogged()) {
				scorecontroller.addScore(akinatorTime, user.getName(), user.getPasswd(), "akinator");
			}
			return "Yay! You got it! Your plaing time was " + akinatorTime + "s.";
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
