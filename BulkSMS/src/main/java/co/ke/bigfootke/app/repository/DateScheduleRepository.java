package co.ke.bigfootke.app.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import co.ke.bigfootke.app.entities.DateSchedule;

public interface DateScheduleRepository{

	public List<DateSchedule> findAll();
	
	public DateSchedule findById(Long scheduleId);
	
	public DateSchedule findByTitle(String title);
	
	public ResponseEntity<Object> findByDate(String date);
	
	public ResponseEntity<Object>  findByGroup(Long groupId);
	
	public ResponseEntity<Object> createSchedule(DateSchedule schedule);
	
	public ResponseEntity<Object> updateSchedule(DateSchedule schedule);
	
	public ResponseEntity<Object> deleteSchedule(Long scheduleId);		
}
