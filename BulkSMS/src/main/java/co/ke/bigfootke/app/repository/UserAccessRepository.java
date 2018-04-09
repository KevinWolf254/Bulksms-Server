package co.ke.bigfootke.app.repository;

import co.ke.bigfootke.app.entities.Credentials;

public interface UserAccessRepository{

	public void create(Credentials credentials);
	
	public Credentials findByUserId(Long id);
	
}
