package co.ke.bigfootke.app.repository.implementation;

import java.util.List;
import org.springframework.stereotype.Repository;
import co.ke.bigfootke.app.entities.Credit;
import co.ke.bigfootke.app.repository.CreditRepository;
/**
 * A child class of MySessionManager 
 * that carries out the CRUD operations 
 * of the ClientGroup entity class 
 * **/
@Repository
public class CreditRepositoryImpl extends MySessionManager implements CreditRepository{
		
	@Override
	public Credit findById(Long id) {
		Credit credit = null;
		try {
			if(!sessionIsOpen) {
				openSession();
				}
			credit = session.get(Credit.class, id);
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}
		return credit;			
	}
	
	@Override
	public void update(Credit credit) {		
		try {
			if(!sessionIsOpen) {
				openSession();
			}
			session.update(credit);
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
	public List<Credit> findAll() {
		List<Credit> credit = null;
		try {
			if(!sessionIsOpen) {
				openSession();
			}
			credit = session.createQuery("from Credit").list();
			}catch(Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}finally {
				closeSession();
			}
		return credit;
	}
	
	/**Not in use in this situation**/
	@Override
	public void create(Credit credit) {
	}
		
	@Override
	public Credit findByName(String name) {
		return null;
	}	
	@Override
	public void delete(Credit credit) {
	}
}
