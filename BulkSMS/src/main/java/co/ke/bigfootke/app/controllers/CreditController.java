package co.ke.bigfootke.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import co.ke.bigfootke.app.entities.Credit;
import co.ke.bigfootke.app.services.CreditService;

@RestController
@RequestMapping(value = "api/credit")
@CrossOrigin(origins="http://localhost:4200")
public class CreditController {
	
	@Autowired
	CreditService service;
	
	private Map<String, String> errors;
	
	/**GET Credit amount **/
	@RequestMapping(method=RequestMethod.GET)
	public Credit getCredit() {
		return service.get();
	}
//	/**UPDATE Credit**/
//	@RequestMapping(method=RequestMethod.PUT)
//	public void update(@RequestBody Credit credit) {
//		service.update(credit);
//	}	
	/**UPDATE Credit**/
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> topUp(@RequestBody @Valid JsonNode credit_amount, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			errors = new HashMap<>();
			for(FieldError error:bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);//response status 406
		}
		if(credit_amount.path("amount").asInt() == 0 || credit_amount.path("amount").asInt() < 0) {
			errors = new HashMap<>();
			errors.put("Amount", "Invalid Amount. Can't be zero or negative");
			return new ResponseEntity<>( errors, HttpStatus.FORBIDDEN);//response status 403
		}
		service.update(credit_amount);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
