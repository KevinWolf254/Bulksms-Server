package co.ke.bigfootke.app.jpa.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.ke.bigfootke.app.jpa.entities.Sms;
import co.ke.bigfootke.app.jpa.implementations.GroupJpaImplementation;
import co.ke.bigfootke.app.jpa.implementations.SmsJpaImplementation;
import co.ke.bigfootke.app.jpa.implementations.UserJpaImplementation;

@Service
public class SmsJpaService {

	@Autowired
	private SmsJpaImplementation repository;
	@Autowired
	private GroupJpaImplementation groupImpl;
	@Autowired
	private UserJpaImplementation userImpl;
	private Map<String, String> response;
	
	public ResponseEntity<Object> create(Sms sms, Long userId) {
		response = new HashMap<>();
		if(sms.getMessage() != null) {
			Sms newSms = repository.create(sms);
			response.put("message", "Success: Created "+newSms);
			//Link sms to user
			userImpl.addToSms(newSms.getSmsId(), userId);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		response.put("message", "Error: Fill required fields");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<Object> findById(Long smsId) {
		if(!repository.exists(smsId)) {
			response = new HashMap<>();
			response.put("message", "Error: Sms not found");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(repository.findById(smsId), HttpStatus.OK);
	}
	
	@Cacheable(value = "sms.paged", key="#pageNo", unless = "#result != null")
	public ResponseEntity<Page<Sms>> findAll(int pageNo, int pageSize) {
		return repository.findAll(pageNo, pageSize);
	} 
	
	@Cacheable(value = "sms.byGroup", key="#groupId", unless = "#result != null")
	public ResponseEntity<Object> findByGroup(Long groupId, int pageNo, int pageSize) {
		if(!groupImpl.exists(groupId)) {
			response.put("message", "Error: Could not find group");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);				
		}
		final Page<Sms> sms = repository.findByGroup(groupId, pageNo, pageSize);
		return new ResponseEntity<Object>(sms, HttpStatus.OK);
	} 
	
	@Caching(evict = {@CacheEvict(value = "sms.paged", key = "#smsId"),
			@CacheEvict(value = "sms.byGroup", key = "#smsId")})
	public ResponseEntity<Object> delete(Long smsId) {
		if(!repository.exists(smsId)) {
			response = new HashMap<>();
			response.put("message", "Error: Sms not found");
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);			
		}
		response.put("message", "Deleted Sms record");
		repository.delete(smsId);
		return new ResponseEntity<Object>(response, HttpStatus.OK);	
	}
	
}
