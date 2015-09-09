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

	public void removeUser(long id) {
		em.createQuery("delete from User p where p.id=:id").setParameter("id", id).executeUpdate();
	}

}
