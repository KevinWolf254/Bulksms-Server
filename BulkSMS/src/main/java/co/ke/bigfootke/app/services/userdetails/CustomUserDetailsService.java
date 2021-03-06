package co.ke.bigfootke.app.services.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.ke.bigfootke.app.entities.User;
import co.ke.bigfootke.app.repository.implementation.UserRepositoryImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepositoryImpl userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
		return new CustomUserDetails(user);
	}

}
