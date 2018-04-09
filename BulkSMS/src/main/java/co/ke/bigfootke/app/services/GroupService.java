package co.ke.bigfootke.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import co.ke.bigfootke.app.entities.Group;
import co.ke.bigfootke.app.pojos.Family;
import co.ke.bigfootke.app.repository.GroupRepository;
import co.ke.bigfootke.app.repository.implementation.GroupRepositoryImpl;

@Service
public class GroupService implements GroupRepository{
	@Autowired
	private GroupRepositoryImpl repository;
	private Map<String, String> response;

	@Override
	public ResponseEntity<Object> createGroup(Group group) {
		return repository.createGroup(group);		
	}
	
	public ResponseEntity<Object> createGroup(Group group, BindingResult bindingResult) {
		return repository.createGroup(group);		
	}
	
	@Override
	public List<Group> findAll() {
		return repository.findAll();
	}

	@Override
	public Group findById(Long id) {
		return repository.findById(id);
	}
	
	public ResponseEntity<Object> findGroupById(Long id) {
		Group group = findById(id);
		return new ResponseEntity<Object>(group, HttpStatus.OK);
	}

	@Override
	public Group findByName(String name) {
		return repository.findByName(name);
	}

	public ResponseEntity<Object> findGroupByName(String name) {
		Group group = findByName(name);
		if(group == null) {
			response = new HashMap<>();
			response.put("message", "Error: Group is non-existant");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(group, HttpStatus.OK);
	}

	public ResponseEntity<Object> findByClient(Long id) {
		return repository.findByClient(id);
	}

	public ResponseEntity<Object> findByDateSchedule(Long dateScheduleId) {
		return repository.findByDateSchedule(dateScheduleId);
	}

	public ResponseEntity<Object> findByWeekSchedule(Long weekScheduleId) {
		return repository.findByWeekSchedule(weekScheduleId);
	}
	
	public ResponseEntity<Object> findByMonthSchedule(Long monthScheduleId) {
		return repository.findByMonthSchedule(monthScheduleId);
	}
	
	public ResponseEntity<Object> findByOnDemandSms(Long onDemandSmsId) {
		return repository.findByOnDemandSms(onDemandSmsId);
	}
	
	public ResponseEntity<Object> updateGroup(Group group) {
		return repository.updateGroup(group);	
	}	

	public ResponseEntity<Object> deleteGroup(Long id) {
		return repository.deleteGroup(id);		
	}

	@Override
	public ResponseEntity<Object> addToSchedule(Long scheduleId, List<Long> groupIds, String scheduleName) {
		return repository.addToSchedule(scheduleId, groupIds, scheduleName);
	}

	@Override
	public ResponseEntity<Object> deleteFromSchedule(Long scheduleId, Long groupId, String scheduleName) {
		return repository.deleteFromSchedule(scheduleId, groupId, scheduleName);
	}

	public ResponseEntity<Object> calculatePreviousMonthOnDemandSmsCosts() {
		return repository.calculatePreviousMonthOnDemandSmsCosts();
	}
	
	public ResponseEntity<Object> calculateCosts(Family family) {
		List<Long> groupIds = family.getChildrenIds();
		return repository.calculateCosts(groupIds);
	}
	public ResponseEntity<Object> calculateYearCosts() {
		return repository.calculateYearCosts();
	}
	
}
