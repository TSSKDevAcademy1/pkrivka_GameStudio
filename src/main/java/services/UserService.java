package services;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.User;

@Stateless
public class UserService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public void addUser(String name, String password) {
		User user = new User(name, password);
		em.persist(user);
	}

	public boolean findUser(String name, String password) {
		try {
			return em.createQuery("select u from User u where u.name=:name and u.passwd=:password", User.class)
					.setParameter("name", name).setParameter("password", password).getSingleResult() != null;
		} catch (Exception e) {
			return false;
		}
	}

	public User getUser(String name, String password) {
		try {
			return em.createQuery("select u from User u where u.name=:name and u.passwd=:password", User.class)
					.setParameter("name", name).setParameter("password", password).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean checkAdmin(String name, String password) {
		return getUser(name, password).isAdmin();
	}

	public void setMinesForUser(String name, String password, int minesNumRows, int minesNumCols, int minesNumMines) {
		User user = getUser(name, password);
		user.setMinesNumRows(minesNumRows);
		user.setMinesNumCols(minesNumCols);
		user.setMinesNumMines(minesNumMines);
	}

	public void setStonesForUser(String name, String password, int stonesNumRows, int stonesNumCols) {
		User user = getUser(name, password);
		user.setStonesNumRows(stonesNumRows);
		user.setStonesNumCols(stonesNumCols);
	}

	public int getMinesRows(String name, String password) {
		return em.createQuery("select u.minesNumRows from User u where u.name=:name and u.passwd=:password",
				Integer.class).setParameter("name", name).setParameter("password", password).getSingleResult();
	}
	
	public int getMinesCols(String name, String password) {
		return em.createQuery("select u.minesNumCols from User u where u.name=:name and u.passwd=:password",
				Integer.class).setParameter("name", name).setParameter("password", password).getSingleResult();
	}
	
	public int getMinesMines(String name, String password) {
		return em.createQuery("select u.minesNumMines from User u where u.name=:name and u.passwd=:password",
				Integer.class).setParameter("name", name).setParameter("password", password).getSingleResult();
	}
	
	public int getStonesRows(String name, String password) {
		return em.createQuery("select u.stonesNumRows from User u where u.name=:name and u.passwd=:password",
				Integer.class).setParameter("name", name).setParameter("password", password).getSingleResult();
	}
	
	public int getStonesCols(String name, String password) {
		return em.createQuery("select u.stonesNumCols from User u where u.name=:name and u.passwd=:password",
				Integer.class).setParameter("name", name).setParameter("password", password).getSingleResult();
	}

}
