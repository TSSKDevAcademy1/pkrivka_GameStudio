package entity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Named
@SessionScoped
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String name;
	// @Size(min = 5, max = 10)
	// @Pattern(regexp = ".*\\d.*")
	private String passwd;
	private boolean admin;
	private int minesNumCols;
	private int minesNumRows;
	private int minesNumMines;
	private int stonesNumCols;
	private int stonesNumRows;

	public User() {
	}

	public User(String name, String passwd) {
		this.name = name;
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String password) {
		this.passwd = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getMinesNumCols() {
		return minesNumCols;
	}

	public void setMinesNumCols(int minesNumCols) {
		this.minesNumCols = minesNumCols;
	}

	public int getMinesNumRows() {
		return minesNumRows;
	}

	public void setMinesNumRows(int minesNumRows) {
		this.minesNumRows = minesNumRows;
	}

	public int getMinesNumMines() {
		return minesNumMines;
	}

	public void setMinesNumMines(int minesNumMines) {
		this.minesNumMines = minesNumMines;
	}

	public int getStonesNumCols() {
		return stonesNumCols;
	}

	public void setStonesNumCols(int stonesNumCols) {
		this.stonesNumCols = stonesNumCols;
	}

	public int getStonesNumRows() {
		return stonesNumRows;
	}

	public void setStonesNumRows(int stonesNumRows) {
		this.stonesNumRows = stonesNumRows;
	}
	
	

}
