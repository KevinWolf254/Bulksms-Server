package co.ke.bigfootke.app.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import co.ke.bigfootke.app.entities.WeekSchedule;

public interface WeekScheduleRepository{

	public List<WeekSchedule> findAll();
	
	public WeekSchedule findById(Long scheduleId);
	
	public WeekSchedule findByTitle(String title);
	
	public ResponseEntity<Object> findByDate(String date);
	
	public ResponseEntity<Object>  findByGroup(Long groupId);
	
	public ResponseEntity<Object> createSchedule(WeekSchedule schedule);
	
	public ResponseEntity<Object> updateSchedule(WeekSchedule schedule);
	
	public ResponseEntity<Object> deleteSchedule(Long scheduleId);		
}
