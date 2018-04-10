package co.ke.bigfootke.app.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.ke.bigfootke.app.africastalking.SmsDispatcher;
import co.ke.bigfootke.app.entities.Client;
import co.ke.bigfootke.app.entities.Group;
import co.ke.bigfootke.app.entities.OnDemandSms;
import co.ke.bigfootke.app.entities.User;
import co.ke.bigfootke.app.pojos.DispatchedSms;
import co.ke.bigfootke.app.pojos.PagedOnDemandSms;
import co.ke.bigfootke.app.repository.OnDemandSmsRepository;
import co.ke.bigfootke.app.repository.implementation.OnDemandSmsRepositoryImpl;

@Service
public class OnDemandSmsService implements OnDemandSmsRepository{
	@Autowired
	OnDemandSmsRepositoryImpl repository;
	@Autowired
	GroupService groupService;
	@Autowired
	UserService userService;
	@Autowired
	ClientService clientService;
	
	SmsDispatcher dispatcher;

	private static final Logger log = LoggerFactory.getLogger(OnDemandSmsService.class);
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

		OnDemandSms mySms = new OnDemandSms();
		Double cost = 2000.00;
		Calendar date = Calendar.getInstance();
		mySms.setSmsId(0L);
		mySms.setCost(cost);
		mySms.setMessage("blah blah blah");
		mySms.setDate(date.getTime());
		repository.createSms(mySms);
		
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
	
	public ResponseEntity<Object> dispatchSms(DispatchedSms sms) {
		dispatcher = new SmsDispatcher();
        log.info("**************  STARTED SENDING PROCESS  ********************");
		String message = sms.getMessage();
		List<Long> groupsIds = sms.getGroupIds();
		Double cost = sms.getCost();
		Long senderId = sms.getSenderId();

		Set<Group> recipientGroups = new HashSet<Group>();
		//retrieve user
		User sender = userService.findById(senderId);
		//Create OnDemandSms object to hold data
		OnDemandSms newSms = new OnDemandSms();
		newSms.setMessage(message);
//		newSms.setGroups(recipientGroups);
		newSms.setCost(cost);
//		newSms.setSender(sender);
		//get current date using Calendar class
		Calendar today = Calendar.getInstance();
		newSms.setDate(today.getTime());
		//Retrieve the groups
		for(Long groupId : groupsIds) {
			Group group = new Group();
			group = groupService.findById(groupId);
			if(group != null)
				recipientGroups.add(group);
		}
		//get id of new sms while saving the sms without groups and sender
		Long newSmsId = repository.save(newSms);
        log.info("**************  SAVED SMS ID: "+newSmsId+"  ********************");
		//update sms with new groups
		groupService.addToSchedule(newSmsId, groupsIds, "OnDemandSms");
        log.info("**************  COMPLETED ADDING GROUPS TO SMS  ********************");
		//update sms with user/sender details
		userService.addToSms(newSmsId, sender.getUserId());
        log.info("**************  COMPLETED ADDING SENDER TO SMS  ********************");
        //process the groups to get their clients full phoneNo
        String recipients = processGroups(recipientGroups);
		//Send to africastalking api
        dispatcher.sendOnDemandSms(recipients, message);
		//send the response to the webClient
		return new ResponseEntity<Object>("", HttpStatus.OK);
	}

	private String processGroups(Set<Group> recipientGroups) {
//		StringBuffer receipients = new StringBuffer();
		String receipients = null;
		List<Long> allGroupIds = new ArrayList<>();;
		//Retrieve clients of groups
		for(Group group : recipientGroups) {
            log.info("**************ADDING CLIENTS OF GROUP: "+group.getGroupId()+"********************");
			Long groupId = group.getGroupId();
			allGroupIds.add(groupId);
			receipients = clientService.findByGroup(allGroupIds);
		}
		return receipients;
	}
}
