package co.ke.bigfootke.app.repository.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import co.ke.bigfootke.app.entities.OnDemandSms;
import co.ke.bigfootke.app.pojos.Page;
import co.ke.bigfootke.app.pojos.PagedOnDemandSms;
import co.ke.bigfootke.app.repository.OnDemandSmsRepository;
/**
 * A child class of MySessionManager 
 * that carries out the CRUD operations 
 * of the ClientGroup entity class 
 * **/
@Repository
public class OnDemandSmsRepositoryImpl extends MySessionManager implements OnDemandSmsRepository{
	
	@Autowired
	private SessionFactory sessionFactory;
	private static final Logger log = LoggerFactory.getLogger(OnDemandSmsRepositoryImpl.class);
	private Map<String, String> response;
	private List<Object> responseList;
	
	@Override
	public List<OnDemandSms> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<PagedOnDemandSms> findAll(int smsPerViewPage, int firstResult, int maxResults){
		Session session = sessionFactory.openSession();		
		PagedOnDemandSms response = null;
		int totalPages = 0;
		try {
			Criteria criteria = session.createCriteria(OnDemandSms.class);
			//Order the sms list by id
			criteria.addOrder(Order.asc("smsId"));
			//Retrieve the first page result
			criteria.setFirstResult(firstResult);
			//Retrieve the maximum page result
			criteria.setMaxResults(maxResults);
						
			//Get the sms list of the requested page
			List<OnDemandSms> smsData = criteria.list();
			//Retrieve the total count of the sms in the database
			criteria.setProjection(Projections.rowCount());
			Long totalRecords = (Long) criteria.uniqueResult();
			int totalElements = totalRecords.intValue();
			//Check if the schedules in the database are more than the schedules view number
			if(totalElements == 0)
				totalPages = 0;
			else if(totalElements <= smsPerViewPage) {
				totalPages = 1;
			}else if((totalElements % smsPerViewPage) != 0)
				totalPages = (totalElements/smsPerViewPage) + 1;
			else
				totalPages = (totalElements/smsPerViewPage);
			//setup the page data for ngx-datatable in angular 5
			Page pageResponse = new Page(smsPerViewPage, totalElements, totalPages, firstResult);
			//Add the retrieved schedules data list and the page data
			response = new PagedOnDemandSms(smsData, pageResponse);			
			
		}catch(HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<PagedOnDemandSms>(response, HttpStatus.OK);
	}
	
	@Override
	public OnDemandSms findById(Long smsId) {
		Session session = sessionFactory.openSession();
		OnDemandSms sms = null;
		try {
			sms = session.get(OnDemandSms.class, smsId);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return sms;
	}

	private OnDemandSms findById(Long smsId, Session session) {
		OnDemandSms sms = null;
		Criteria criteria = session.createCriteria(OnDemandSms.class);
		criteria.add(Restrictions.eqOrIsNull("smsId", smsId));			
		sms = (OnDemandSms) criteria.uniqueResult();
		return sms;	
	}
	
	@Override
	public OnDemandSms findByTitle(String title) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(OnDemandSms.class);
		OnDemandSms sms = null;
		try {
			criteria.add(Restrictions.eqOrIsNull("title", title));			
			//Get the sms 
			sms = (OnDemandSms) criteria.uniqueResult();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return sms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> findByDate(String date) {
		Session session = sessionFactory.openSession();
		List<OnDemandSms> schedules = null;
		try {
			Criteria criteria = session.createCriteria(OnDemandSms.class);
			criteria.add(Restrictions.eqOrIsNull("date", date));			
			//Get the client
			schedules = criteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(schedules, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> findByGroup(Long groupId) {
		Session session = sessionFactory.openSession();
		List<OnDemandSms> smss = null;
		try {
			Criteria criteria = session.createCriteria(OnDemandSms.class);
			//find all OnDemandSms of the group
			criteria.createCriteria("groups").add(Restrictions.eq("groupId", groupId));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the schedules list by id
			criteria.addOrder(Order.asc("smsId"));
			//get the schedules in a list
			smss = criteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(smss, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> createSms(OnDemandSms sms) {
		response = new HashMap<>();
		try {
			if(findById(sms.getSmsId()) !=null) {
				response.put("message", "Error: SMS with ID: "+sms.getSmsId()+" already exists");
				//send the response to the webClient with error 
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}else {
				save(sms);
				response.put("message", "Successfully added SMS");
			}
		}catch(HibernateException e) {
			e.printStackTrace();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(response, HttpStatus.OK);	
	}

	public Long save(OnDemandSms sms) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();	
		Long newId = null;
		try {			
			newId = (Long) session.save(sms);
			log.info("############  NEW ONDEMAND SMS ID "+newId+"  ############");
			trans.commit();
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}	
		return newId;
	}

	@Override
	public ResponseEntity<Object> updateSms(OnDemandSms sms) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();	
		
		OnDemandSms updatedSms = null;
		responseList = new ArrayList<>();
		response = new HashMap<>();
		try {
			 if(findById(sms.getSmsId()) == null) {
				response.put("message", "Error: SMS is non-existant");
				responseList.add(response);
				//send the response to the webClient
				return new ResponseEntity<Object>(responseList, HttpStatus.NOT_FOUND);
			}else {
				updatedSms = (OnDemandSms) session.merge(sms);
				trans.commit();
				response.put("message", "Successfully updated SMS");
				responseList.add(response);
				responseList.add(updatedSms);
			}
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(responseList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteSms(Long smsId) {
		Session session = sessionFactory.openSession();
		Transaction trans = null;
		response = new HashMap<>();
		try {
			OnDemandSms sms = findById(smsId, session);
			if(sms == null) {
				response.put("message", "Error: SMS with ID:"+smsId+" does not Exist!!");
				//send the response to the webClient
				return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
			}
			response.put("message", "Successfully DELETED SMS with ID:"+sms.getSmsId());
			trans = session.beginTransaction();
			//Remove all group relation associated with the client. This is to
			//ensure the groups are not deleted together with the client being deleted
			sms.getGroups().removeAll(sms.getGroups());
			session.delete(sms);
			trans.commit();
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

		
}
