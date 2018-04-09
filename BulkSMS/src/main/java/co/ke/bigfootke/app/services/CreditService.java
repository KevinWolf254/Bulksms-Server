package co.ke.bigfootke.app.services;

import java.text.DecimalFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import co.ke.bigfootke.app.entities.Credit;
import co.ke.bigfootke.app.repository.CreditRepository;
import co.ke.bigfootke.app.repository.implementation.CreditRepositoryImpl;

@Service
public class CreditService implements CreditRepository{
	@Autowired
	private CreditRepositoryImpl repository;
	
	private Long cId = (long) 1;

	@Override
	public Credit findById(Long id) {
		return repository.findById(id);
	}
	@Override
	public List<Credit> findAll() {
		return repository.findAll();
	}
	@Override
	public void update(Credit credit) {	
		repository.update(credit);
	}
	public void update(JsonNode  credit_amount) {
		DecimalFormat dFormat = new DecimalFormat("#0.00");
		Double addAmount = Double.parseDouble(dFormat.format(credit_amount.path("amount").asDouble()));
				
		Credit credit = repository.findById(cId);
		Double currentAmount = Double.parseDouble(dFormat.format(credit.getAmount()));
		
		Double newAmount = Double.parseDouble(dFormat.format(currentAmount + addAmount));
		credit.setAmount(newAmount);
		repository.update(credit);
	}
	/**Not in use**/
	@Override
	public Credit findByName(String fullName) {
		return null;
	}	
	@Override
	public void delete(Credit credit) {
	}
	@Override
	public void create(Credit credit) {
	}
	public Credit get() {
		return repository.findById(cId);
	}	
}
