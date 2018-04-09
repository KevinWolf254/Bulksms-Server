package co.ke.bigfootke.app.oath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthenticationServer extends GlobalAuthenticationConfigurerAdapter {
	@Autowired
	private UserDetailsService customUserDetailsService;

	public void init(AuthenticationManagerBuilder authUsers) 
			throws Exception {	
		authUsers.inMemoryAuthentication()
		.withUser("allen")
		.password("pass")
		.roles("USER")
	.and()
		.withUser("ben")
		.password("pass1")
		.roles("USER","ADMIN")
		;
//			authUsers.userDetailsService(customUserDetailsService);	
		
	}
}
