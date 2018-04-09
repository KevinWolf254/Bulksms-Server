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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import co.ke.bigfootke.app.entities.WeekSchedule;
import co.ke.bigfootke.app.pojos.Page;
import co.ke.bigfootke.app.pojos.PagedWeekSchedule;
import co.ke.bigfootke.app.repository.WeekScheduleRepository;

@Repository
public class WeekScheduleRepositoryImpl implements WeekScheduleRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Map<String, String> response;
	private List<Object> responseList;
	
	@Override
	public List<WeekSchedule> findAll() {
		return null;
	}
	
	/**Retrieves paginated schedules
	 * @param schedulesPerViewPage
	 * @param firstResult
	 * @param maxResults**/
	@SuppressWarnings("unchecked")
	public ResponseEntity<PagedWeekSchedule> findAll(int schedulesPerViewPage, int firstResult, int maxResults){

		Session session = sessionFactory.openSession();		
		PagedWeekSchedule response = null;
		int totalPages = 0;
		try {
			Criteria criteria = session.createCriteria(WeekSchedule.class);
			//Order the schedules list by id
			criteria.addOrder(Order.asc("scheduleId"));
			//Retrieve the first page result
			criteria.setFirstResult(firstResult);
			//Retrieve the maximum page result
			criteria.setMaxResults(maxResults);
						
			//Get the schedules list of the requested page
			List<WeekSchedule> scheduledData = criteria.list();
			//Retrieve the total count of the schedules in the database
			criteria.setProjection(Projections.rowCount());
			Long totalRecords = (Long) criteria.uniqueResult();
			int totalElements = totalRecords.intValue();
			//Check if the schedules in the database are more than the schedules view number
			if(totalElements == 0)
				totalPages = 0;
			else if(totalElements <= schedulesPerViewPage) {
				totalPages = 1;
			}else if((totalElements % schedulesPerViewPage) != 0)
				totalPages = (totalElements/schedulesPerViewPage) + 1;
			else
				totalPages = (totalElements/schedulesPerViewPage);
			//setup the page data for ngx-datatable in angular 5
			Page pageResponse = new Page(schedulesPerViewPage, totalElements, totalPages, firstResult);
			//Add the retrieved schedules data list and the page data
			response = new PagedWeekSchedule(scheduledData, pageResponse);			
			
		}catch(HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<PagedWeekSchedule>(response, HttpStatus.OK);
	}
	
	@Override
	public WeekSchedule findById(Long scheduleId) {
		Session session = sessionFactory.openSession();
		WeekSchedule schedule = null;
		try {
			schedule = session.get(WeekSchedule.class, scheduleId);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return schedule;
	}
	
	private WeekSchedule findById(Long scheduleId, Session session) {
		WeekSchedule schedule = null;
		Criteria criteria = session.createCriteria(WeekSchedule.class);
		criteria.add(Restrictions.eqOrIsNull("scheduleId", scheduleId));			
		schedule = (WeekSchedule) criteria.uniqueResult();
		return schedule;	
	}

	@Override
	public WeekSchedule findByTitle(String title) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(WeekSchedule.class);
		WeekSchedule schedule = null;
		try {
			criteria.add(Restrictions.eqOrIsNull("title", title));			
			//Get the client
			schedule = (WeekSchedule) criteria.uniqueResult();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return schedule;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> findByDate(String date) {
		Session session = sessionFactory.openSession();
		List<WeekSchedule> schedules = null;
		try {
			Criteria criteria = session.createCriteria(WeekSchedule.class);
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
		List<WeekSchedule> schedules = null;
		try {
			Criteria scheduleCriteria = session.createCriteria(WeekSchedule.class);
			//find all schedules of the group
			scheduleCriteria.createCriteria("groups").add(Restrictions.eq("groupId", groupId));
			scheduleCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the schedules list by id
			scheduleCriteria.addOrder(Order.asc("scheduleId"));
			//get the schedules in a list
			schedules = scheduleCriteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(schedules, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Object> createSchedule(WeekSchedule schedule) {
		response = new HashMap<>();
		try {
			if(findByTitle(schedule.getTitle()) !=null) {
				response.put("message", "Error: Schedule titled "+schedule.getTitle()+" already exists");
				//send the response to the webClient with error 
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}else {
				save(schedule);
				response.put("message", "Successfully added Schedule");
			}
		}catch(HibernateException e) {
			e.printStackTrace();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(response, HttpStatus.OK);	
	}

	private void save(WeekSchedule schedule) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();		
		try {
			session.save(schedule);
			trans.commit();
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}		
	}
	
	@Override
	public ResponseEntity<Object> updateSchedule(WeekSchedule schedule) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();		

		WeekSchedule search = null;
		
		WeekSchedule updatedSchedule = null;
		responseList = new ArrayList<>();
		response = new HashMap<>();
		try {
			Criteria criteria = session.createCriteria(WeekSchedule.class);
			criteria.add(Restrictions.eqOrIsNull("title", schedule.getTitle()));
			search = (WeekSchedule) criteria.uniqueResult();
			 if(search == null) {
				updatedSchedule = (WeekSchedule) session.merge(schedule);
				trans.commit();
				response.put("message", "Successfully updated Schedule");
				responseList.add(response);
				responseList.add(updatedSchedule);
			}else {
				response.put("message", "Error: Schedule titled "+schedule.getTitle()+" already exists");
				responseList.add(response);
				responseList.add(search);
				//send the response to the webClient
				return new ResponseEntity<Object>(responseList, HttpStatus.CONFLICT);
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
	public ResponseEntity<Object> deleteSchedule(Long scheduleId) {
		Session session = sessionFactory.openSession();
		Transaction trans = null;
		response = new HashMap<>();
		try {
			WeekSchedule schedule = findById(scheduleId, session);
			if(schedule == null) {
				response.put("message", "Error: Schedule with ID:"+scheduleId+" does not Exist!!");
				//send the response to the webClient
				return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
			}
			response.put("message", "Successfully DELETED "+schedule.getTitle());
			trans = session.beginTransaction();
			//Remove all group relation associated with the client. This is to
			//ensure the groups are not deleted together with the client being deleted
			schedule.getWeeklyGroups().removeAll(schedule.getWeeklyGroups());
			session.delete(schedule);
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
