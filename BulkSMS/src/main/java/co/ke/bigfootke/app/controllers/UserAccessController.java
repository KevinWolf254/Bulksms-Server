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
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.ke.bigfootke.app.entities.User;
import co.ke.bigfootke.app.entities.Credentials;
import co.ke.bigfootke.app.services.UserAccessService;
import co.ke.bigfootke.app.services.UserService;

@RestController
@RequestMapping(value = "api/credentials")
@CrossOrigin(origins="http://localhost:4200")
public class UserAccessController {

	@Autowired
	private UserAccessService service;

	@Autowired
	private UserService userService;
	
	private Map<String, String> errors;
	
	/**CREATE USER CREDENTIALS**/
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@RequestBody @Valid Credentials credentials, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			errors = new HashMap<>();
			for(FieldError error:bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
		}
		User userExists = userService.findByEmail(credentials.getUser().getEmail());
		if(userExists!=null) {
			errors = new HashMap<>();
			errors.put("Transaction Error", "User already exists");
			return new ResponseEntity<>( errors, HttpStatus.CONFLICT);
		}
		service.create(credentials);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**GET USER BY ID**/
//	@RequestMapping(method=RequestMethod.GET, value = "/{id}")
//	public User get(@PathVariable Long id) {
//		return service.findById(id);
//	}
	/**GET USER BY USERNAME**/
//	@RequestMapping(method=RequestMethod.GET, value = "/email/{email}")
//	public User get(@PathVariable String username) {
//		return service.findByEmail(username);
//	}
	/**UPDATE USER**/
//	@RequestMapping(method=RequestMethod.PUT)
//	public void update(@RequestBody User user) {
//		service.update(user);
//	}
	/**DELETE USER**/
//	@RequestMapping(method=RequestMethod.DELETE)
//	public void delete(@RequestBody User user) {
//		service.delete(user);
//	}
	
}
