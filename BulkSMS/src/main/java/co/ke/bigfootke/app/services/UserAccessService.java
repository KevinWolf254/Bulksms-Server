package co.ke.bigfootke.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.ke.bigfootke.app.entities.User;
import co.ke.bigfootke.app.entities.Credentials;
import co.ke.bigfootke.app.repository.UserAccessRepository;
import co.ke.bigfootke.app.repository.implementation.UserAccessRepositoryImpl;

@Service
public class UserAccessService implements UserAccessRepository{
	@Autowired
	UserAccessRepositoryImpl repository;
	
	@Override
	public void create(Credentials credentials) {
		repository.create(credentials);
		
	}

	@Override
	public Credentials findByUserId(Long user_id) {
		return repository.findByUserId(user_id);
	}

	public void update(User user) {
	}
	/**Not in use**/
	
	/**processes the SysUser object 
	 * received from controller**/
	public void delete(User tobedeleteduser) {
//		repository.delete(tobedeleteduser);
	}		
}
