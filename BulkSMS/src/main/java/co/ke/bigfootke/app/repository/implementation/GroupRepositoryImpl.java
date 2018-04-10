package co.ke.bigfootke.app.repository.implementation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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

import co.ke.bigfootke.app.entities.DateSchedule;
import co.ke.bigfootke.app.entities.DateSmsCost;
import co.ke.bigfootke.app.entities.Group;
import co.ke.bigfootke.app.entities.MonthSchedule;
import co.ke.bigfootke.app.entities.MonthSmsCost;
import co.ke.bigfootke.app.entities.OnDemandSms;
import co.ke.bigfootke.app.entities.WeekSchedule;
import co.ke.bigfootke.app.entities.WeekSmsCost;
import co.ke.bigfootke.app.pojos.LineChartData;
import co.ke.bigfootke.app.repository.GroupRepository;

@Repository
public class GroupRepositoryImpl implements GroupRepository{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Map<String, String> response;
	private List<Object> responseList;
	private static final Logger log = LoggerFactory.getLogger(GroupRepositoryImpl.class);
		
	@SuppressWarnings("unchecked")
	public List<Group> findAll() {
		List<Group> groups = null;
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(Group.class);
			groups = criteria.list();
		}catch(Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return groups;		
	}
	
	public Group findById(Long id) {
		Session session = sessionFactory.openSession();
		Group group = null;
		try {
			group = session.get(Group.class, id);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return group;
	}
	
	private Group findById(Long groupId, Session session) {
		Group group = null;	
		Criteria criteria = session.createCriteria(Group.class);
		criteria.add(Restrictions.eqOrIsNull("groupId", groupId));
		group = (Group) criteria.uniqueResult();
		return group;		
	}
	
	@Override
	public Group findByName(String name) {
		Group group = null;	
		Session session = sessionFactory.openSession();
		try {
			group = findByName(name, session);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return group;		
	}
	
	private Group findByName(String name, Session session) {
		Group group = null;	
		Criteria criteria = session.createCriteria(Group.class);
		criteria.add(Restrictions.eqOrIsNull("name", name));
		group = (Group) criteria.uniqueResult();
		return group;		
	}
	
	public ResponseEntity<Object> createGroup(Group group) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();	
		response = new HashMap<>();
		try {
			session.save(group);
			response.put("message", "Success: Group has been created");
			trans.commit();
			}catch(HibernateException e) {
				trans.rollback();
				e.printStackTrace();
			}finally {
				session.close();
			}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> findByClient(Long clientId) {
		
		Session session = sessionFactory.openSession();
		List<Group> groups = null;
		try {
			Criteria groupCriteria = session.createCriteria(Group.class);
			//find all clients of the group
			groupCriteria.createCriteria("clients").add(Restrictions.eq("clientId", clientId));
			groupCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the groups list by id
			groupCriteria.addOrder(Order.asc("groupId"));
			//get the clients in a list
			groups = groupCriteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(groups, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> findByDateSchedule(Long dateScheduleId) {
		Session session = sessionFactory.openSession();
		List<Group> groups = null;
		try {
			Criteria groupCriteria = session.createCriteria(Group.class);
			//find all groups associated with the date schedule
			groupCriteria.createCriteria("dateSchedule").add(Restrictions.eq("scheduleId", dateScheduleId));
			groupCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the groups list by id
			groupCriteria.addOrder(Order.asc("groupId"));
			//get the clients in a list
			groups = groupCriteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(groups, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> findByWeekSchedule(Long weekScheduleId) {
		Session session = sessionFactory.openSession();
		List<Group> groups = null;
		try {
			Criteria groupCriteria = session.createCriteria(Group.class);
			//find all groups associated with the week schedule
			groupCriteria.createCriteria("weekSchedule").add(Restrictions.eq("scheduleId", weekScheduleId));
			groupCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the groups list by id
			groupCriteria.addOrder(Order.asc("groupId"));
			//get the clients in a list
			groups = groupCriteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(groups, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> findByMonthSchedule(Long monthScheduleId) {
		Session session = sessionFactory.openSession();
		List<Group> groups = null;
		try {
			Criteria groupCriteria = session.createCriteria(Group.class);
			//find all groups associated with the month schedule
			groupCriteria.createCriteria("monthSchedule").add(Restrictions.eq("scheduleId", monthScheduleId));
			groupCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the groups list by id
			groupCriteria.addOrder(Order.asc("groupId"));
			//get the clients in a list
			groups = groupCriteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(groups, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> findByOnDemandSms(Long onDemandSmsId) {
		Session session = sessionFactory.openSession();
		List<Group> groups = null;
		try {
			Criteria groupCriteria = session.createCriteria(Group.class);
			//find all groups associated with the month schedule
			groupCriteria.createCriteria("sms").add(Restrictions.eq("smsId", onDemandSmsId));
			groupCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the groups list by id
			groupCriteria.addOrder(Order.asc("groupId"));
			//get the clients in a list
			groups = groupCriteria.list();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(groups, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> updateGroup(Group group) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();		

		Group search = null;		
		Group updatedGroup = null;
		responseList = new ArrayList<>();
		response = new HashMap<>();
		try {
			Criteria criteria = session.createCriteria(Group.class);
			criteria.add(Restrictions.eqOrIsNull("name", group.getName()));
			search = (Group) criteria.uniqueResult();
			if(search == null) {
				updatedGroup = (Group) session.merge(group);
				trans.commit();
				response.put("message", "Successfully updated Group");
				responseList.add(response);
				responseList.add(updatedGroup);
			}else {
				response.put("message", "There already exists a Group with that name");
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

	public ResponseEntity<Object> deleteGroup(Long id) {
		Session session = sessionFactory.openSession();
		Transaction trans = null;
		response = new HashMap<>();
		try {
			Group group = findById(id, session);
			if(group == null) {
				response.put("message", "Error: Group with ID:"+id+" does not Exist!!");
				//send the response to the webClient
				return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
			}
			response.put("message", "Successfully DELETED "+group.getName());
			trans = session.beginTransaction();
			//Remove all group relation associated with the client. This is to
			//ensure the groups are not deleted together with the client being deleted
			group.getClients().removeAll(group.getClients());
			group.getDateSchedule().removeAll(group.getDateSchedule());
			group.getWeekSchedule().removeAll(group.getWeekSchedule());
			group.getMonthSchedule().removeAll(group.getMonthSchedule());
			session.delete(group);
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

	public ResponseEntity<Object> addToSchedule(Long scheduleId, List<Long> groupIds, String scheduleName) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();	
		Group group = null;
		responseList = new ArrayList<>();
		try {
			if(scheduleName == "OnDemandSms") {
				OnDemandSms sms;
				Criteria sCriteria = session.createCriteria(OnDemandSms.class);
				sCriteria.add(Restrictions.eqOrIsNull("smsId", scheduleId));
				sms = (OnDemandSms) sCriteria.uniqueResult();
				if(sms == null) {
					response = new HashMap<>();
					response.put("message", "Error: Schedule is none-existant ");
					responseList.add(response);
				}else {
					for(Long groupId: groupIds) {
						response = new HashMap<>();
						group = findById(groupId,session);					
						if(group == null) {
							response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
					        log.info("**************  ERROR: COULD NOT RETRIEVE GROUP WITH ID: "+groupId+"  ********************");
							responseList.add(response);
						}else {
					        log.info("**************  RETRIEVED GROUP WITH ID: "+group.getGroupId()+"  ********************");
							sms.getGroups().add(group);
							session.merge(sms);
							response.put("message", "Successfuly added Group ID: "+groupId+" "
									+ "to Sms: "+scheduleId);
							responseList.add(response);
						}
					}
				}
			}else if(scheduleName == "DateSchedule") {
				DateSchedule schedule;
				Criteria sCriteria = session.createCriteria(DateSchedule.class);
				sCriteria.add(Restrictions.eqOrIsNull("scheduleId", scheduleId));
				schedule = (DateSchedule) sCriteria.uniqueResult();
				if(schedule == null) {
					response = new HashMap<>();
					response.put("message", "Error: Schedule is none-existant ");
					responseList.add(response);
				}else {
					for(Long groupId: groupIds) {
						response = new HashMap<>();
						group = findById(groupId, session);					
						if(group == null) {
							response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
							responseList.add(response);
						}else {
							schedule.getGroups().add(group);
							session.merge(schedule);
							response.put("message", "Successfuly added Group ID: "+groupId+" "
									+ "to Schedule ID: "+scheduleId);
							responseList.add(response);
						}
					}
				}
			}else if(scheduleName == "WeekSchedule") {
				WeekSchedule schedule;	
				Criteria sCriteria = session.createCriteria(WeekSchedule.class);
				sCriteria.add(Restrictions.eqOrIsNull("scheduleId", scheduleId));
				schedule = (WeekSchedule) sCriteria.uniqueResult();
				if(schedule == null) {
					response = new HashMap<>();
					response.put("message", "Error: Schedule is none-existant ");
					responseList.add(response);
				}else {
					for(Long groupId: groupIds) {
						response = new HashMap<>();
						group = findById(groupId, session);					
						if(group == null) {
							response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
							responseList.add(response);
						}else {
							schedule.getWeeklyGroups().add(group);
							session.merge(schedule);
							response.put("message", "Successfuly added Group ID: "+groupId+" "
									+ "to Schedule ID: "+scheduleId);
							responseList.add(response);
						}
					}
				}
			}else if(scheduleName == "MonthSchedule") {
				MonthSchedule schedule;
				Criteria sCriteria = session.createCriteria(MonthSchedule.class);
				sCriteria.add(Restrictions.eqOrIsNull("scheduleId", scheduleId));
				schedule = (MonthSchedule) sCriteria.uniqueResult();
				if(schedule == null) {
					response = new HashMap<>();
					response.put("message", "Error: Schedule is none-existant ");
					responseList.add(response);
				}else {
					for(Long groupId: groupIds) {
						response = new HashMap<>();
						group = findById(groupId, session);					
						if(group == null) {
							response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
							responseList.add(response);
						}else {
							schedule.getMonthlyGroups().add(group);
							session.merge(schedule);
							response.put("message", "Successfuly added Group ID: "+groupId+" "
									+ "to Schedule ID: "+scheduleId);
							responseList.add(response);
						}
					}
				}
			}
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
	
	public ResponseEntity<Object> deleteFromSchedule(Long scheduleId, Long groupId, String scheduleName) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();	
		Group group = null;
		response = new HashMap<>();
		try {
			if(scheduleName == "OnDemandSms") {
				OnDemandSms sms;
				Criteria criteria = session.createCriteria(OnDemandSms.class);
				criteria.add(Restrictions.eqOrIsNull("smsId", scheduleId));
				sms = (OnDemandSms) criteria.uniqueResult();
				if(sms == null) {
					response.put("message", "Error: sms is none-existant ");
				}else {
					group = findById(groupId,session);
					if(group == null) {
						response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
					}else {
						sms.getGroups().remove(group);
						session.merge(sms);
						response.put("message", "Successfuly removed Group ID: "+groupId+" from Sms with ID: "+scheduleId);
					}
				}
			}else if(scheduleName == "DateSchedule") {
				DateSchedule dSchedule;
				Criteria criteria = session.createCriteria(DateSchedule.class);
				criteria.add(Restrictions.eqOrIsNull("scheduleId", scheduleId));
				dSchedule = (DateSchedule) criteria.uniqueResult();
				if(dSchedule == null) {
					response.put("message", "Error: Schedule is none-existant ");
				}else {
					group = findById(groupId,session);
					if(group == null) {
						response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
					}else {
						dSchedule.getGroups().remove(group);
						session.merge(dSchedule);
						response.put("message", "Successfuly removed Group ID: "+groupId+" from Schedule with ID: "+scheduleId);
					}
				}
			}else if(scheduleName == "WeekSchedule") {
				WeekSchedule wSchedule;
				Criteria criteria = session.createCriteria(WeekSchedule.class);
				criteria.add(Restrictions.eqOrIsNull("scheduleId", scheduleId));
				wSchedule = (WeekSchedule) criteria.uniqueResult();
				if(wSchedule == null) {
					response.put("message", "Error: Schedule is none-existant ");
				}else {
					group = findById(groupId,session);
					if(group == null) {
						response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
					}else {
						wSchedule.getWeeklyGroups().remove(group);
						session.merge(wSchedule);
						response.put("message", "Successfuly removed Group ID: "+groupId+" from Sms with ID: "+scheduleId);
					}
				}
				
			}else if(scheduleName == "MonthSchedule") {
				MonthSchedule mSchedule;
				Criteria criteria = session.createCriteria(MonthSchedule.class);
				criteria.add(Restrictions.eqOrIsNull("scheduleId", scheduleId));
				mSchedule = (MonthSchedule) criteria.uniqueResult();
				if(mSchedule == null) {
					response.put("message", "Error: Schedule is none-existant ");
				}else {
					group = findById(groupId,session);
					if(group == null) {
						response.put("message", "Error: Group ID: "+groupId+" is none-existant ");
					}else {
						mSchedule.getMonthlyGroups().remove(group);
						session.merge(mSchedule);
						response.put("message", "Successfuly removed Group ID: "+groupId+" from Sms with ID: "+scheduleId);
					}
				}
			}
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

	/**Calculates the total cost of all groups 
	 * added to a schedule or OnDemandSms**/
	public ResponseEntity<Object> calculateCosts(List<Long> groupIds){
		Session session = sessionFactory.openSession();
		int cost = 0;
		try {
			Criteria criteria = session.createCriteria(Group.class);
			for(Long groupId : groupIds) {
				criteria.add(Restrictions.eq("groupId", groupId));
				//Retrieve the total count
				int totalRecords = (int) criteria.setProjection(Projections.rowCount()).uniqueResult();
				cost = cost + totalRecords;
			}
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(cost, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> calculatePreviousMonthOnDemandSmsCosts(){
		Session session = sessionFactory.openSession();
		List<OnDemandSms> previousMonthCostsList = null;
		int previousMonthCosts = 0;
		int previousMonth = -1;
		int firstDate = 1;
		Calendar calendar = Calendar.getInstance();
		//add -1 month to current month
		calendar.add(Calendar.MONTH, previousMonth);
		//set DATE to 1, so first date of previous month
		calendar.set(Calendar.DATE, firstDate);
		Date firstDateOfPreviousMonth = calendar.getTime();
		// set actual maximum date of previous month
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDateOfPreviousMonth = calendar.getTime();
		try {
			Criteria criteria = session.createCriteria(OnDemandSms.class);
			criteria.add(Restrictions.between("date", firstDateOfPreviousMonth, lastDateOfPreviousMonth));
			criteria.addOrder(Order.asc("date"));
			previousMonthCostsList = criteria.list();
			if(previousMonthCostsList != null)
				for(OnDemandSms smsCost: previousMonthCostsList) {
					previousMonthCosts = previousMonthCosts + smsCost.getCost().intValue();
				}
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(previousMonthCosts, HttpStatus.OK);
	}
	
//	@SuppressWarnings("unchecked")
//	public ResponseEntity<Object> calculateYearCosts(){
//		Session session = sessionFactory.openSession();
//		List<OnDemandSms> monthCostsList = null;
//		int monthCosts = 0;
//		int firstDate = 1;
//		LineChartData data = new LineChartData();
//		List<Integer> costs = new ArrayList<>();
//		
//		int firstMonthOfCurrentYear = 0;// 0 = january
//		int lastMonthOfCurrentYear = 11;// 11 = december
//		Calendar currentYear = Calendar.getInstance(TimeZone.getTimeZone("Africa/Kenya"));
//		try {
//			for(int month = firstMonthOfCurrentYear; month<=lastMonthOfCurrentYear; month++) {
//				currentYear.set(Calendar.MONTH, month);
//				//set DATE to 1, so first date of that month
//				currentYear.set(Calendar.DATE, firstDate);
//				Date firstDateOfMonth = currentYear.getTime();
//				// set actual maximum date of that month
//				currentYear.set(Calendar.DATE, currentYear.getActualMaximum(Calendar.DAY_OF_MONTH));
//				Date lastDateOfMonth = currentYear.getTime();
//				
//				Criteria criteria = session.createCriteria(OnDemandSms.class);
//				criteria.add(Restrictions.between("date", firstDateOfMonth, lastDateOfMonth));
//				criteria.addOrder(Order.asc("date"));
//				monthCostsList = criteria.list();
//				if(monthCostsList != null)
//					for(OnDemandSms smsCost: monthCostsList) {
//						monthCosts = monthCosts + smsCost.getCost().intValue();
//						costs.add(monthCosts);
//					}
//				//add the monthCosts to array
//				data.setData(costs);
//			}
//			data.setLabel(Integer.toString(currentYear.get(Calendar.YEAR)));			
//		}catch(HibernateException e) {
//			e.printStackTrace();
//		}finally {
//			session.close();
//		}
//		//send the response to the webClient
//		return new ResponseEntity<Object>(data, HttpStatus.OK);
//	}
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> calculateYearCosts(){
		Session session = sessionFactory.openSession();
		List<OnDemandSms> onDemandSmsCostsList = null;
		List<DateSmsCost> dateCostsList = null;
		List<WeekSmsCost> weekCostsList = null;
		List<MonthSmsCost> monthCostsList = null;
		
		int perOnDemandSmsCosts = 0;
		int perDateScheduleCosts = 0;
		int perWeekScheduleCosts = 0;
		int perMonthScheduleCosts = 0;
		
		int firstDate = 1;
		LineChartData onDemandSmsdata = new LineChartData();
		LineChartData dateScheduleData = new LineChartData();
		LineChartData weekScheduleData = new LineChartData();
		LineChartData monthScheduleData = new LineChartData();
		
		List<Integer> onDemandCosts = new ArrayList<>();
		List<Integer> dateScheduleCosts = new ArrayList<>();
		List<Integer> weekScheduleCosts = new ArrayList<>();
		List<Integer> monthScheduleCosts = new ArrayList<>();
		
		List<LineChartData> allYearCosts = new ArrayList<>();
		
		int firstMonthOfCurrentYear = 0;// 0 = january
		int lastMonthOfCurrentYear = 11;// 11 = december
		Calendar currentYear = Calendar.getInstance(TimeZone.getTimeZone("Africa/Kenya"));
		try {
			for(int month = firstMonthOfCurrentYear; month<=lastMonthOfCurrentYear; month++) {
				currentYear.set(Calendar.MONTH, month);
				//set DATE to 1, so first date of that month
				currentYear.set(Calendar.DATE, firstDate);
				Date firstDateOfMonth = currentYear.getTime();
				// set actual maximum date of that month
				currentYear.set(Calendar.DATE, currentYear.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date lastDateOfMonth = currentYear.getTime();
				
				Criteria smsCriteria = session.createCriteria(OnDemandSms.class);				
				smsCriteria.add(Restrictions.between("date", firstDateOfMonth, lastDateOfMonth));
				smsCriteria.addOrder(Order.asc("date"));
				onDemandSmsCostsList = smsCriteria.list();
				if(onDemandSmsCostsList != null)
					for(OnDemandSms smsCost: onDemandSmsCostsList) {
						perOnDemandSmsCosts = perOnDemandSmsCosts + smsCost.getCost().intValue();
						if(perOnDemandSmsCosts == 0)
							onDemandCosts.add(firstMonthOfCurrentYear, 1);
						onDemandCosts.add(firstMonthOfCurrentYear, perOnDemandSmsCosts);
					}
				//add the monthCosts to array
				onDemandSmsdata.setData(onDemandCosts);
				
				Criteria dateCriteria = session.createCriteria(DateSmsCost.class);				
				dateCriteria.add(Restrictions.between("dateSent", firstDateOfMonth, lastDateOfMonth));
				dateCriteria.addOrder(Order.asc("dateSent"));
				dateCostsList = dateCriteria.list();
				if(dateCostsList != null)
					for(DateSmsCost smsCost: dateCostsList) {
						perDateScheduleCosts = perDateScheduleCosts + smsCost.getCost().intValue();
						dateScheduleCosts.add(perDateScheduleCosts);
					}
				//add the monthCosts to array
				dateScheduleData.setData(dateScheduleCosts);
				
				Criteria weekCriteria = session.createCriteria(WeekSmsCost.class);				
				weekCriteria.add(Restrictions.between("dateSent", firstDateOfMonth, lastDateOfMonth));
				weekCriteria.addOrder(Order.asc("dateSent"));
				weekCostsList = weekCriteria.list();
				if(weekCostsList != null)
					for(WeekSmsCost smsCost: weekCostsList) {
						perWeekScheduleCosts = perWeekScheduleCosts + smsCost.getCost().intValue();
						weekScheduleCosts.add(perWeekScheduleCosts);
					}
				//add the monthCosts to array
				weekScheduleData.setData(weekScheduleCosts);
				
				Criteria monthCriteria = session.createCriteria(MonthSmsCost.class);				
				monthCriteria.add(Restrictions.between("dateSent", firstDateOfMonth, lastDateOfMonth));
				monthCriteria.addOrder(Order.asc("dateSent"));
				monthCostsList = monthCriteria.list();				
				if(monthCostsList != null)
					for(MonthSmsCost smsCost: monthCostsList) {
						perMonthScheduleCosts = perMonthScheduleCosts + smsCost.getCost().intValue();
						monthScheduleCosts.add(perMonthScheduleCosts);
					}
				//add the monthCosts to array
				monthScheduleData.setData(monthScheduleCosts);
			}
			onDemandSmsdata.setLabel(Integer.toString(currentYear.get(Calendar.YEAR))+" - OnDemandSms");
			dateScheduleData.setLabel(Integer.toString(currentYear.get(Calendar.YEAR))+" - Schedules by Date");
			weekScheduleData.setLabel(Integer.toString(currentYear.get(Calendar.YEAR))+" - Schedules by Week");
			monthScheduleData.setLabel(Integer.toString(currentYear.get(Calendar.YEAR))+" - Schedules by Month");	
			allYearCosts.add(onDemandSmsdata);
			allYearCosts.add(dateScheduleData);
			allYearCosts.add(weekScheduleData);
			allYearCosts.add(monthScheduleData);
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(allYearCosts, HttpStatus.OK);
	}
}
