package co.ke.bigfootke.app.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import co.ke.bigfootke.app.entities.Group;

public interface GroupRepository {
	
	public List<Group> findAll();
	
	public Group findById(Long id);
	
	public Group findByName(String name);
	
	public ResponseEntity<Object> createGroup(Group group);
	
	public ResponseEntity<Object> findByClient(Long clientId);
	
	public ResponseEntity<Object> findByDateSchedule(Long dateScheduleId);
	
	public ResponseEntity<Object> findByWeekSchedule(Long weekScheduleId);
	
	public ResponseEntity<Object> findByMonthSchedule(Long monthScheduleId);
	
	public ResponseEntity<Object> findByOnDemandSms(Long onDemandSmsId);
	
	public ResponseEntity<Object> addToSchedule(Long dateScheduleId, List<Long> groupId, String scheduleName);
	
	public ResponseEntity<Object> deleteFromSchedule(Long scheduleId, Long groupId, String scheduleName);
	
//	public ResponseEntity<Object> addToDateSchedule(Long dateScheduleId, List<Long> groupId);
//	
//	public ResponseEntity<Object> addToWeekSchedule(Long weekScheduleId, List<Long> groupId);
//	
//	public ResponseEntity<Object> addToMonthSchedule(Long monthScheduleId, List<Long> groupId);
//	
//	public ResponseEntity<Object> addToOnDemandSms(Long onDemandSmsId, List<Long> groupId);
//	
//	public ResponseEntity<Object> updateGroup(Group group);
//	
//	public ResponseEntity<Object> deleteGroup(Long groupId);
//	
//	public ResponseEntity<Object> deleteFromDateSchedule(Long scheduleId, Long groupId);
//	
//	public ResponseEntity<Object> deleteFromWeekSchedule(Long scheduleId, Long groupId);
//	
//	public ResponseEntity<Object> deleteFromMonthSchedule(Long scheduleId, Long groupId);
//	
//	public ResponseEntity<Object> deleteFromOnDemandSms(Long onDemandSmsId, Long groupId);
}
