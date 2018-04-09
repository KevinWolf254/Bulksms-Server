package co.ke.bigfootke.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import co.ke.bigfootke.app.entities.OnDemandSms;
import co.ke.bigfootke.app.pojos.PagedOnDemandSms;
import co.ke.bigfootke.app.repository.OnDemandSmsRepository;
import co.ke.bigfootke.app.repository.implementation.OnDemandSmsRepositoryImpl;

@Service
public class OnDemandSmsService implements OnDemandSmsRepository{
	@Autowired
	OnDemandSmsRepositoryImpl repository;
	private Map<String, String> response;	

	@Override
	public List<OnDemandSms> findAll() {
		return null;
	}
	public ResponseEntity<PagedOnDemandSms> findAll(int schedulesPerViewPage, int firstResult, int maxResults){
		return repository.findAll(schedulesPerViewPage, firstResult, maxResults);		
	}

	@Override
	public OnDemandSms findById(Long smsId) {
		return repository.findById(smsId);
	}

	public ResponseEntity<Object> findScheduleById(Long scheduleId) {
		OnDemandSms schedule = findById(scheduleId);
		if(schedule == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+scheduleId);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(schedule, HttpStatus.OK);
	}
	
	@Override
	public OnDemandSms findByTitle(String title) {
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
	public ResponseEntity<Object> createSms(OnDemandSms sms) {
		return repository.createSms(sms);
	}

	@Override
	public ResponseEntity<Object> updateSms(OnDemandSms sms) {
		return repository.updateSms(sms);
	}

	@Override
	public ResponseEntity<Object> deleteSms(Long smsId) {
		return repository.deleteSms(smsId);
	}

}
