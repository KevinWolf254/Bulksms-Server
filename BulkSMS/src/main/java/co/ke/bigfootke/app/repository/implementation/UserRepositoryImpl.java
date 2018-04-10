package co.ke.bigfootke.app.repository.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import co.ke.bigfootke.app.entities.OnDemandSms;
import co.ke.bigfootke.app.entities.User;
import co.ke.bigfootke.app.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{
	@Autowired
	private SessionFactory sessionFactory;
	private Map<String, String> response;
	private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
//	public UserRepositoryImpl() {
//	}
//	
	@Override
	public void create(User user) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		try {
			session.save(user);
			trans.commit();
			}catch(Exception e) {
				trans.rollback();
				e.printStackTrace();
			}finally {
				session.close();
			}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		List<User> mUsers = null;
		Session session = sessionFactory.openSession();
		try {
			mUsers = session.createQuery("from User").setCacheable(true).list();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				session.close();
			}
		return mUsers;	
	}
	@Override
	public User findById(Long id) {
		User user = null;
		Session session = sessionFactory.openSession();
		try {
			user = session.get(User.class, id);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				session.close();
			}
		return user;		
	}
	@Override
	public User findByEmail(String email) {
		User user = null;
		Session session = sessionFactory.openSession();
		try {		
			String hql = "from User where email = :email";
			Query query = session.createQuery(hql);
			query.setCacheable(true);
			query.setString("email", email);
			user = (User) query.uniqueResult();	
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				session.close();
			}
		return user;
	}
	
	/**
	 * users are deleted by username rather than id**/
	public void delete(Long id) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		try {
			User user = (User) session.load(User.class, id);
			session.delete(user);
			trans.commit();
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				session.close();
			}	
	}

	public void update(User details) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		try {
			session.update(details);
			trans.commit();
			}catch(Exception e) {
				trans.rollback();
				e.printStackTrace();
			}finally {
				session.close();
			}	
	}

	public ResponseEntity<Object> addToSms(Long smsId, Long userId) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		response = new HashMap<>();
		User user = null;
		try {
			OnDemandSms sms;
			Criteria sCriteria = session.createCriteria(OnDemandSms.class);
			sCriteria.add(Restrictions.eqOrIsNull("smsId", smsId));
			sms = (OnDemandSms) sCriteria.uniqueResult();
			if(sms == null) {
				response = new HashMap<>();
				response.put("message", "Error: Schedule is none-existant ");
			}else {
				user = findById(userId);					
				if(userId == null) {
					response.put("message", "Error: User is none-existant ");
			        log.info("**************  ERROR FAILED TO ADD USER: "+userId+"  ********************");
				}else {
					sms.setSender(user);
					session.merge(sms);
					trans.commit();
					response.put("message", "Successfuly added User ID: "+userId+" "
							+ "to Sms: "+smsId);
			        log.info("**************  RETRIEVED USER WITH ID: "+user.getUserId()+"  ********************");
				}
			}	
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(response, HttpStatus.OK);
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
