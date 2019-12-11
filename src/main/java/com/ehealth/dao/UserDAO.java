package com.ehealth.dao; 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ehealth.enity.Article;
import com.ehealth.enity.User;
 
@Transactional
@Repository
public class UserDAO implements IUserDAO{
	@PersistenceContext	
	private EntityManager entityManager;
	@Override
	public List<User> getAllUsers() {
		String hql = "FROM User as usr ORDER BY usr.userId";
		return (List<User>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public User getUserById(int userid) {
		return entityManager.find(User.class, userid);
	}

	 
	@Override
	public User updateUser(User user) {
		User userObj = getUserById(user.getId());
//		artcl.setTitle(article.getTitle());
//		artcl.setCategory(article.getCategory());
		entityManager.flush();
		return entityManager.find(User.class, user.getId());
	}

	@Override
	public void deleteUser(int userid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String title, String category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User save(User user) { 
	   entityManager.persist(user);
	   return user;
	}

	@Override
	public User getUserByEmail(String emailId) {
		return (User)entityManager.createQuery(
				"SELECT u FROM User u WHERE u.email = :emailId")
				.setParameter("emailId", emailId).getSingleResult();
	}
	
}