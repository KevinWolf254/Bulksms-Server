package co.ke.bigfootke.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ke.bigfootke.app.entities.DateSchedule;
import co.ke.bigfootke.app.pojos.PagedDateSchedule;
import co.ke.bigfootke.app.services.DateScheduleService;

@RestController
@RequestMapping(value = "api/schedule/date")
public class DateScheduleController {

	@Autowired
	DateScheduleService service;
	private Map<String, String> response;	
	
	/**Create**/
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createSchedule(@RequestBody @Valid DateSchedule schedule,  BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			response = new HashMap<>();
			for(FieldError error:bindingResult.getFieldErrors()) {
				response.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		return service.createSchedule(schedule);
	}
	
	/**Get**/
	@RequestMapping(method=RequestMethod.GET, value = "/list/{firstResult}")
	public ResponseEntity<PagedDateSchedule> findAll(@PathVariable int firstResult) {
		int schedulesPerViewPage = 20;
		int maxResults = 100;
		return service.findAll(schedulesPerViewPage, firstResult, maxResults);
	}
	
	/**Get BY ID**/
	@RequestMapping(method=RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		return service.findScheduleById(id);
	}
	
	/**Get BY title**/
	@RequestMapping(method=RequestMethod.GET, value = "/name/{title}")
	public ResponseEntity<Object> findByTitle(@PathVariable String title) {
		DateSchedule schedule = service.findByTitle(title);
		if(schedule == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+title);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(schedule, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/day/{date}")
	public ResponseEntity<Object> findByDate(@PathVariable String date) {
		return service.findByDate(date);
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/group/{groupId}")
	public ResponseEntity<Object> findByGroup(@PathVariable Long groupId) {
		return service.findByGroup(groupId);
	}
	
	/**Update**/
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> updateSchedule(@RequestBody DateSchedule schedule) {
		if(schedule.getScheduleId() <= 0){
			response = new HashMap<>();
			response.put("message", "Error: Bad Request");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return service.updateSchedule(schedule);
	}
	
	/**Delete**/
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{scheduleId}")
	public ResponseEntity<Object> deleteSchedule(@PathVariable Long scheduleId) {
		return service.deleteSchedule(scheduleId);
	}
	
}
