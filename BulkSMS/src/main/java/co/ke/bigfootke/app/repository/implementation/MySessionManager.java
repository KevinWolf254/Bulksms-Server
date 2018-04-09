package co.ke.bigfootke.app.repository.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * Parent class of all repository implementation classes
 * that manages the opening and closing of sessions
 * and injects session factory
 * **/
@Repository
public class MySessionManager {
	@Autowired
	private SessionFactory sessionFactory;
	protected Session session = null;
			
	
	boolean sessionIsOpen = false;
	 
	void openSession() {
		session = sessionFactory.openSession();
		session.beginTransaction();
		sessionIsOpen = true;
	} 
	void closeSession() {
		if(sessionIsOpen) {
			session.clear();
			sessionIsOpen = false;
		}
	}
}
