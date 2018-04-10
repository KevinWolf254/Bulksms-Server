package co.ke.bigfootke.app.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import co.ke.bigfootke.app.entities.User;
import co.ke.bigfootke.app.repository.UserRepository;
import co.ke.bigfootke.app.repository.implementation.UserRepositoryImpl;

@Service
public class UserService implements UserRepository{
	@Autowired
	UserRepositoryImpl repository;

	@Override
	public void create(User user) {
		repository.create(user);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}
	public void update(User user) {
		repository.update(user);
	}
	
	public void delete(Long id) {
		repository.delete(id);
	}
	
	public ResponseEntity<Object> addToSms(Long smsId, Long userId) {
		return repository.addToSms(smsId, userId);
	}
	/**processes the SysUser object 
	 * received from controller**/
//	public void delete(User user) {
//		repository.delete(user);
//	}	
}
