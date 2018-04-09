package co.ke.bigfootke.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.ke.bigfootke.app.entities.MonthSchedule;
import co.ke.bigfootke.app.pojos.PagedMonthSchedule;
import co.ke.bigfootke.app.repository.MonthScheduleRepository;
import co.ke.bigfootke.app.repository.implementation.MonthScheduleRepositoryImpl;

@Service
public class MonthScheduleService implements MonthScheduleRepository{
	@Autowired
	MonthScheduleRepositoryImpl repository;
	private Map<String, String> response;	
	
	/**Not in use**/
	@Override
	public List<MonthSchedule> findAll() {
		return null;
	}
	public ResponseEntity<PagedMonthSchedule> findAll(int schedulesPerViewPage, int firstResult, int maxResults){
		return repository.findAll(schedulesPerViewPage, firstResult, maxResults);		
	}
	@Override
	public MonthSchedule findById(Long id) {
		return repository.findById(id);
	}
	
	public ResponseEntity<Object> findScheduleById(Long scheduleId) {
		MonthSchedule schedule = findById(scheduleId);
		if(schedule == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+scheduleId);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(schedule, HttpStatus.OK);
	}
	@Override
	public MonthSchedule findByTitle(String title) {
		return repository.findByTitle(title);
	}

	@Override
	public ResponseEntity<Object> findByDate(String date) {
		return repository.findByDate(date);
	}
	
	@Override
	public ResponseEntity<Object> findByGroup(Long groupId) {
		return repository.findByGroup(groupId);
	}
	@Override
	public ResponseEntity<Object> createSchedule(MonthSchedule schedule) {
		return repository.createSchedule(schedule);
	}
	@Override
	public ResponseEntity<Object> updateSchedule(MonthSchedule schedule) {
		return repository.updateSchedule(schedule);
	}
	@Override
	public ResponseEntity<Object> deleteSchedule(Long scheduleId) {
		return repository.deleteSchedule(scheduleId);
	}
}
