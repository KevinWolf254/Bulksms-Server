package co.ke.bigfootke.app.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import co.ke.bigfootke.app.entities.Client;
import co.ke.bigfootke.app.pojos.ClientPagedData;

public interface ClientRepository {

	public List<Client> findAll();
	
	public Client findById(Long scheduleId);
	
	public Client findByPhone(String phoneNo);
	
	public ResponseEntity<ClientPagedData> findByPage(int clientsPerViewPage, int firstResult, int maxResults);
	
	public ResponseEntity<Object> createClient(Client client);
	
	public ResponseEntity<Object> updateClient(Client client);

	public ResponseEntity<Object> deleteClient(Long id);
	
	public ResponseEntity<ClientPagedData> findPagedByGroup(Long groupId, int clientsPerViewPage, int firstResult, int maxResults);
	
	public ResponseEntity<Object> addToGroup(Long groupId, List<Long> clientIds);
	
	public ResponseEntity<Object>  deleteFromGroup(Long clientId, Long groupId);
	
	public void batchCreateNewClients(List<Client> newClients);
}
