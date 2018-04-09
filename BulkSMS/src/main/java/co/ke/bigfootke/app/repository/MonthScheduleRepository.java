package co.ke.bigfootke.app.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import co.ke.bigfootke.app.entities.MonthSchedule;

public interface MonthScheduleRepository{

	public List<MonthSchedule> findAll();
	
	public MonthSchedule findById(Long scheduleId);
	
	public MonthSchedule findByTitle(String title);
	
	public ResponseEntity<Object> findByDate(String date);
	
	public ResponseEntity<Object>  findByGroup(Long groupId);
	
	public ResponseEntity<Object> createSchedule(MonthSchedule schedule);
	
	public ResponseEntity<Object> updateSchedule(MonthSchedule schedule);
	
	public ResponseEntity<Object> deleteSchedule(Long scheduleId);		
}
