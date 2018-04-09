package co.ke.bigfootke.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import co.ke.bigfootke.app.entities.DateSchedule;
import co.ke.bigfootke.app.pojos.PagedDateSchedule;
import co.ke.bigfootke.app.repository.DateScheduleRepository;
import co.ke.bigfootke.app.repository.implementation.DateScheduleRepositoryImpl;

@Service
public class DateScheduleService implements DateScheduleRepository{
	@Autowired
	DateScheduleRepositoryImpl repository;
	private Map<String, String> response;	
	
	/**Not in use**/
	@Override
	public List<DateSchedule> findAll() {
		return null;
	}
	public ResponseEntity<PagedDateSchedule> findAll(int schedulesPerViewPage, int firstResult, int maxResults){
		return repository.findAll(schedulesPerViewPage, firstResult, maxResults);		
	}
	@Override
	public DateSchedule findById(Long id) {
		return repository.findById(id);
	}
	
	public ResponseEntity<Object> findScheduleById(Long scheduleId) {
		DateSchedule schedule = findById(scheduleId);
		if(schedule == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+scheduleId);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(schedule, HttpStatus.OK);
	}
	@Override
	public DateSchedule findByTitle(String title) {
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
	public ResponseEntity<Object> createSchedule(DateSchedule schedule) {
		return repository.createSchedule(schedule);
	}
	@Override
	public ResponseEntity<Object> updateSchedule(DateSchedule schedule) {
		return repository.updateSchedule(schedule);
	}
	@Override
	public ResponseEntity<Object> deleteSchedule(Long scheduleId) {
		return repository.deleteSchedule(scheduleId);
	}
}
