package co.ke.bigfootke.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.ke.bigfootke.app.entities.WeekSchedule;
import co.ke.bigfootke.app.pojos.PagedWeekSchedule;
import co.ke.bigfootke.app.repository.WeekScheduleRepository;
import co.ke.bigfootke.app.repository.implementation.WeekScheduleRepositoryImpl;

@Service
public class WeekScheduleService implements WeekScheduleRepository{
	@Autowired
	WeekScheduleRepositoryImpl repository;
	private Map<String, String> response;	
	
	/**Not in use**/
	@Override
	public List<WeekSchedule> findAll() {
		return null;
	}
	public ResponseEntity<PagedWeekSchedule> findAll(int schedulesPerViewPage, int firstResult, int maxResults){
		return repository.findAll(schedulesPerViewPage, firstResult, maxResults);		
	}
	@Override
	public WeekSchedule findById(Long id) {
		return repository.findById(id);
	}
	
	public ResponseEntity<Object> findScheduleById(Long scheduleId) {
		WeekSchedule schedule = findById(scheduleId);
		if(schedule == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+scheduleId);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(schedule, HttpStatus.OK);
	}
	@Override
	public WeekSchedule findByTitle(String title) {
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
	public ResponseEntity<Object> createSchedule(WeekSchedule schedule) {
		return repository.createSchedule(schedule);
	}
	@Override
	public ResponseEntity<Object> updateSchedule(WeekSchedule schedule) {
		return repository.updateSchedule(schedule);
	}
	@Override
	public ResponseEntity<Object> deleteSchedule(Long scheduleId) {
		return repository.deleteSchedule(scheduleId);
	}
}
