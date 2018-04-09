package co.ke.bigfootke.app.repository;

import java.util.List;

import co.ke.bigfootke.app.entities.Credit;

public interface CreditRepository{
	
	public void create(Credit credit);
	
	public List<Credit> findAll();
	
	public Credit findById(Long msg_id);
	
	public Credit findByName(String title);
	
	public void update(Credit amount);
	
	public void delete(Credit credit);
	
}
