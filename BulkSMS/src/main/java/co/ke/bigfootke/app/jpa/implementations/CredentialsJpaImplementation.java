package co.ke.bigfootke.app.jpa.implementations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.ke.bigfootke.app.jpa.entities.Credentials;
import co.ke.bigfootke.app.jpa.entities.User;
import co.ke.bigfootke.app.jpa.repository.CredentialsJpaRepo;

@Repository
public class CredentialsJpaImplementation {
	
	@Autowired
	CredentialsJpaRepo repository;
	@PersistenceUnit
	EntityManagerFactory factory;
	private static final Logger log = LoggerFactory.getLogger(CredentialsJpaImplementation.class);
		
	public void create(User user, Credentials credentials) {
		final EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		
		Credentials newCred = repository.save(credentials);
		
		newCred.setActive(true);
		newCred.setLoggedIn(false);
		newCred.setUser(user);
		manager.merge(newCred);
		manager.getTransaction().commit();
		
		log.info("***** Created: "+newCred);
	}

	public Credentials findByUserId(Long userId) {
		return repository.findByUserUserId(userId);
	}

	public void update(Credentials cred) {
		final EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		
		manager.merge(cred);

		manager.getTransaction().commit();		
		log.info("***** Updated: "+cred);
	}
}
