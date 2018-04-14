package co.ke.bigfootke.app.jpa.implementations;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import co.ke.bigfootke.app.jpa.entities.Schedule;
import co.ke.bigfootke.app.jpa.repository.ScheduleJpaRepo;

@Repository
public class ScheduleJpaImplementation {

	@Autowired
	private ScheduleJpaRepo repository;
	@PersistenceUnit
	EntityManagerFactory factory;
	private static final Logger log = LoggerFactory.getLogger(ScheduleJpaImplementation.class);
	
	public boolean exists(Long scheduleId) {
		return repository.exists(scheduleId);
	}
	
	public Schedule create(Schedule schedule) {
		Schedule newSchedule = repository.save(schedule);	
		log.info("***** Created: "+newSchedule);
		return newSchedule;
	}
	
	public Schedule findById(final Long scheduleId) {
		Schedule schedule = repository.findOne(scheduleId);	
		log.info("***** Found: "+schedule);
		return schedule;
	}

	public Schedule findByTitle(String title) {
		return repository.findByTitle(title);
	}
	
	public Schedule findByDate(Date date) {
		return repository.findByDate(date);
	}
		
	public ResponseEntity<Page<Schedule>> findAll(final int pageNo, final int pageSize){
		Page<Schedule> schedules = repository.findAll(new PageRequest(pageNo, pageSize));
		//send the response to the webClient
		return new ResponseEntity<Page<Schedule>>(schedules, HttpStatus.OK);
	}
	
	public void delete(final Long scheduleId){		
		final Schedule schedule = findById(scheduleId);
		log.info("***** Removing groups assigned to schedule");
		schedule.getGroups().removeAll(schedule.getGroups());
		repository.delete(scheduleId);		
		log.info("***** deleted: "+schedule);
	}
	
	public Page<Schedule> findByGroup(final Long groupId, final int pageNo, final int pageSize){
		return repository.findByGroupsGroupIdOrderByDate(groupId, new PageRequest(pageNo, pageSize));		
	}

	public List<Schedule> findBtwnDates(Date firstDate, Date lastDate) {
		final EntityManager manager = factory.createEntityManager();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Schedule> query = builder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		Path<Date> date = root.get("date");
		Predicate predicate = builder.between(date, firstDate, lastDate);
		query.where(predicate);
		List<Schedule> schedules =  manager.createQuery(query).getResultList();
		return schedules;
	}
	
}
