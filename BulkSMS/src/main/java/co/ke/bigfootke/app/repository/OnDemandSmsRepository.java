package co.ke.bigfootke.app.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;
import co.ke.bigfootke.app.entities.OnDemandSms;

public interface OnDemandSmsRepository{
	
	public List<OnDemandSms> findAll();
	
	public OnDemandSms findById(Long smsId);
	
	public OnDemandSms findByTitle(String title);
	
	public ResponseEntity<Object> findByDate(String date);
	
	public ResponseEntity<Object>  findByGroup(Long groupId);
	
	public ResponseEntity<Object> createSms(OnDemandSms sms);
	
	public ResponseEntity<Object> updateSms(OnDemandSms sms);
	
	public ResponseEntity<Object> deleteSms(Long smsId);
	
}
