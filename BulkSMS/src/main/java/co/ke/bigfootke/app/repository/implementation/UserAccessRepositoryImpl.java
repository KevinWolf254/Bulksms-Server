package co.ke.bigfootke.app.repository.implementation;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import co.ke.bigfootke.app.entities.Credentials;
import co.ke.bigfootke.app.repository.UserAccessRepository;

@Repository
public class UserAccessRepositoryImpl extends MySessionManager implements UserAccessRepository{
	
	public UserAccessRepositoryImpl() {
	}
	
	@Override
	public void create(Credentials credentials) {
		try {
			if(!sessionIsOpen) {
				openSession();
			}
			session.save(credentials);
			session.getTransaction().commit();
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}		
	}

	@Override
	public Credentials findByUserId(Long user_id) {
		Credentials credentials = null;
		try {
			if(!sessionIsOpen) {
				openSession();
			}			
			String hql = "from UserAccess where user = :user_id";
			Query query = session.createQuery(hql);
			query.setCacheable(true);
			query.setLong("user_id", user_id);
			credentials = (Credentials) query.uniqueResult();	
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}
		return credentials;
	}
}
