package co.ke.bigfootke.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.ke.bigfootke.app.entities.OnDemandSms;
import co.ke.bigfootke.app.pojos.DispatchedSms;
import co.ke.bigfootke.app.pojos.PagedOnDemandSms;
import co.ke.bigfootke.app.services.OnDemandSmsService;

@RestController
@RequestMapping(value = "api/sms")
public class OnDemandSmsController {

	@Autowired
	OnDemandSmsService service;
	private Map<String, String> response;	
	
	/**Send OnDemandSms**/
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> sendSms(@RequestBody DispatchedSms sms) {		
		return service.dispatchSms(sms);
	}	
	
	/**Creates schedule**/
//	@RequestMapping(method=RequestMethod.POST)
//	public ResponseEntity<Object> createSms(@RequestBody @Valid OnDemandSms sms,  BindingResult bindingResult) {
//		if(bindingResult.hasErrors()) {
//			response = new HashMap<>();
//			for(FieldError error:bindingResult.getFieldErrors()) {
//				response.put(error.getField(), error.getDefaultMessage());
//			}
//			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//		}
//		return service.createSms(sms);
//	}
	
	/**GET ALL ScheduledByDate**/
	@RequestMapping(method=RequestMethod.GET, value = "/list/{firstResult}")
	public ResponseEntity<PagedOnDemandSms> findAll(@PathVariable int firstResult) {
		int schedulesPerViewPage = 20;
		int maxResults = 100;
		return service.findAll(schedulesPerViewPage, firstResult, maxResults);
	}
	
	/**GET ScheduledByDate BY ID**/
	@RequestMapping(method=RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		return service.findScheduleById(id);
	}
	
	/**GET ScheduledByDate BY title**/
	@RequestMapping(method=RequestMethod.GET, value = "/name/{title}")
	public ResponseEntity<Object> findByTitle(@PathVariable String title) {
		OnDemandSms sms = service.findByTitle(title);
		if(sms == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+title);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(sms, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/day/{date}")
	public ResponseEntity<Object> findByDate(@PathVariable String date) {
		return service.findByDate(date);
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/group/{groupId}")
	public ResponseEntity<Object> findByGroup(@PathVariable Long groupId) {
		return service.findByGroup(groupId);
	}
	
	/**UPDATE ScheduledByDate**/
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> updateSms(@RequestBody OnDemandSms sms) {
		if(sms.getSmsId() <= 0){
			response = new HashMap<>();
			response.put("message", "Error: Bad Request");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return service.updateSms(sms);
	}
	
	/**DELETE ScheduledByDate**/
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{smsId}")
	public ResponseEntity<Object> deleteSms(@PathVariable Long smsId) {
		return service.deleteSms(smsId);
	}
	
}
