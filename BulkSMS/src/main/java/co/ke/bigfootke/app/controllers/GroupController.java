package co.ke.bigfootke.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.ke.bigfootke.app.entities.Group;
import co.ke.bigfootke.app.pojos.Family;
import co.ke.bigfootke.app.services.GroupService;

@RestController
@RequestMapping(value = "api/group")
@CrossOrigin(origins="http://localhost:4200")
public class GroupController {

	@Autowired
	private GroupService service;
	private Map<String, String> response;
	/**CREATE GROUP**/
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createGroup(@RequestBody @Valid  Group group, BindingResult bindingResult) {
		response = new HashMap<>();
		if(bindingResult.hasErrors()) {
			for(FieldError error:bindingResult.getFieldErrors()) {
				response.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if(service.findByName(group.getName()) != null) {
			response.put("message", "Error: Group already exists");
			return new ResponseEntity<>( response, HttpStatus.CONFLICT);
		}
		return service.createGroup(group);
	}
	/**GET ALL GROUPS**/
	@RequestMapping(method=RequestMethod.GET)
	public List<Group> findAll() {
		return service.findAll();
	}
	/**GET GROUP BY ID**/
	@RequestMapping(method=RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		return service.findGroupById(id);
	}
	/**GET GROUP BY NAME**/
	@RequestMapping(method=RequestMethod.GET, value = "/name/{name}")
	public ResponseEntity<Object>  findByName(@PathVariable String name) {
		Group group = service.findByName(name);
		if(group == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+name);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return service.findGroupByName(name);
	}
	/**Retrieves the groups that a client belongs to**/
	@RequestMapping(method=RequestMethod.GET, value = "/client/{clientId}")
	public ResponseEntity<Object>  findByClient(@PathVariable Long clientId) {
		return service.findByClient(clientId);
	}
	/**Retrieves the groups that a date schedule is associated with**/
	@RequestMapping(method=RequestMethod.GET, value = "/date/{dateScheduleId}")
	public ResponseEntity<Object>  findByDateSchedule(@PathVariable Long dateScheduleId) {
		return service.findByDateSchedule(dateScheduleId);
	}
	/**Retrieves the groups that a week schedule is associated with**/
	@RequestMapping(method=RequestMethod.GET, value = "/week/{weekScheduleId}")
	public ResponseEntity<Object>  findByWeekSchedule(@PathVariable Long weekScheduleId) {
		return service.findByWeekSchedule(weekScheduleId);
	}
	/**Retrieves the groups that a month schedule is associated with**/
	@RequestMapping(method=RequestMethod.GET, value = "/month/{monthScheduleId}")
	public ResponseEntity<Object>  findByMonthSchedule(@PathVariable Long monthScheduleId) {
		return service.findByMonthSchedule(monthScheduleId);
	}
	/**Retrieves the groups that a month schedule is associated with**/
	@RequestMapping(method=RequestMethod.GET, value = "/sms/{onDemandSmsId}")
	public ResponseEntity<Object>  findByOnDemandSms(@PathVariable Long onDemandSmsId) {
		return service.findByOnDemandSms(onDemandSmsId);
	}
	@RequestMapping(method=RequestMethod.GET, value = "/cost")
	public ResponseEntity<Object>  calculatePreviousMonthOnDemandSmsCosts() {
		return service.calculatePreviousMonthOnDemandSmsCosts();
	}
	@RequestMapping(method=RequestMethod.POST, value="/group/cost")
	public ResponseEntity<Object> calculateCosts(@RequestBody Family family){
		return service.calculateCosts(family);
	}
	@RequestMapping(method=RequestMethod.GET, value="/year")
	public ResponseEntity<Object> calculateYearCosts(){
		return service.calculateYearCosts();
	}
	/**UPDATE GROUP
	 * @return **/
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> updateGroup(@RequestBody Group group) {
		return service.updateGroup(group);
	}
	/**DELETE GROUP**/
	@RequestMapping(method=RequestMethod.DELETE, value = "/{groupId}")
	public ResponseEntity<Object> deleteGroup(@PathVariable Long groupId) {
		return service.deleteGroup(groupId);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/date")
	public ResponseEntity<Object> addToDateSchedule(@RequestBody Family family){
		return service.addToSchedule(family.getParentId(), family.getChildrenIds(), "DateSchedule");
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/week")
	public ResponseEntity<Object> addToWeekSchedule(@RequestBody Family family){
		return service.addToSchedule(family.getParentId(), family.getChildrenIds(), "WeekSchedule");
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/month")
	public ResponseEntity<Object> addToMonthSchedule(@RequestBody Family family){
		return service.addToSchedule(family.getParentId(), family.getChildrenIds(), "MonthSchedule");		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/sms")
	public ResponseEntity<Object> addToOnDemandSms(@RequestBody Family family){
		return service.addToSchedule(family.getParentId(), family.getChildrenIds(), "OnDemandSms");		
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{groupId}/fromDate/{scheduleId}")
	public ResponseEntity<Object> deleteFromDateSchedule(@PathVariable Long groupId, @PathVariable Long scheduleId){
		return service.deleteFromSchedule(scheduleId, groupId, "DateSchedule");
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{groupId}/fromWeek/{scheduleId}")
	public ResponseEntity<Object> deleteFromWeekSchedule(@PathVariable Long groupId, @PathVariable Long scheduleId){
		return service.deleteFromSchedule(scheduleId, groupId, "WeekSchedule");
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{groupId}/fromMonth/{scheduleId}")
	public ResponseEntity<Object> deleteFromMonthSchedule(@PathVariable Long groupId, @PathVariable Long scheduleId){
		return service.deleteFromSchedule(scheduleId, groupId, "MonthSchedule");
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{groupId}/fromSms/{onDemandSmsId}")
	public ResponseEntity<Object> deleteFromOnDemandSms(@PathVariable Long groupId, @PathVariable Long onDemandSmsId){
		return service.deleteFromSchedule(onDemandSmsId, groupId, "OnDemandSms");
	}
}
