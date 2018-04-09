package co.ke.bigfootke.app.repository.implementation;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import co.ke.bigfootke.app.entities.User;
import co.ke.bigfootke.app.repository.UserRepository;

@Repository
public class UserRepositoryImpl extends MySessionManager implements UserRepository{
	
	public UserRepositoryImpl() {
	}
	
	@Override
	public void create(User user) {
		try {
			if(!sessionIsOpen) {
				openSession();
			}
			session.save(user);
			session.getTransaction().commit();
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		List<User> mUsers = null;
		try {
			if(!sessionIsOpen) {
				openSession();
			}
			mUsers = session.createQuery("from User").setCacheable(true).list();
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}
		return mUsers;	
	}
	@Override
	public User findById(Long id) {
		User user = null;
		try {
			if(!sessionIsOpen) {
				openSession();
				}
			user = session.get(User.class, id);
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}
		return user;		
	}
	@Override
	public User findByEmail(String email) {
		User user = null;
		try {
			if(!sessionIsOpen) {
				openSession();
			}			
			String hql = "from User where email = :email";
			Query query = session.createQuery(hql);
			query.setCacheable(true);
			query.setString("email", email);
			user = (User) query.uniqueResult();	
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}
		return user;
	}
	
	/**
	 * users are deleted by username rather than id**/
	public void delete(Long id) {
		try {
			if(!sessionIsOpen) {
				openSession();
			}	
			User user = (User) session.load(User.class, id);
			session.delete(user);
			session.getTransaction().commit();
			session.clear();
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}	
	}

	public void update(User details) {
		try {
			if(!sessionIsOpen) {
				openSession();
			}	
			session.update(details);
			session.getTransaction().commit();
			session.clear();
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}	
	}

//	public void update(User newUserDetails) {
//		try {
//			if(!sessionIsOpen) {
//				openSession();
//			}
//			session.update(newUserDetails);
//			session.getTransaction().commit();
//			}catch(Exception e) {
//				session.getTransaction().rollback();
//				e.printStackTrace();
//			}finally {
//				closeSession();
//			}
//	}

//	public void delete(User user) {
//		try {
//			if(!sessionIsOpen) {
//				openSession();
//			}
////			User mUser = findByEmail(user.getEmail());	
//			session.delete(user.getEmail(), user);			
////			session.delete(mUser);
//			session.getTransaction().commit();
//			}catch(Exception e) {
//				session.getTransaction().rollback();
//				e.printStackTrace();
//			}finally {
//				closeSession();
//			}	
//	}
}
