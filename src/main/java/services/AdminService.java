package services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.User;

@Stateless
public class AdminService {
	
	@Inject
	private EntityManager em;
	
	public void addUserREST(User user){
		em.persist(user);
	}
	
	public void addUser(String name, String password){
		User user=new User(name, password);
		em.persist(user);
	}
	
	public void removeUser(long id) {
		em.createQuery("delete from User p where p.id=:id").setParameter("id", id).executeUpdate();
	}
	
	public void deleteComment(long id){
		em.createQuery("delete from Comment c where c.id=:id").setParameter("id", id).executeUpdate();
	}
	
	public User findUser(long id){
		return em.createQuery("select u from User u where u.id=:id",User.class).setParameter("id", id).getSingleResult();
	}
	
	public void setAdmin(long id){
		User user=findUser(id);
		user.setAdmin(true);
	}

}
