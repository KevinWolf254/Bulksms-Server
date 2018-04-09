package co.ke.bigfootke.app.repository;

import java.util.List;
import co.ke.bigfootke.app.entities.User;

public interface UserRepository{

	public void create(User user);
	
	public List<User> findAll();
	
	public User findById(Long id);
	
	public User findByEmail(String email);
}
