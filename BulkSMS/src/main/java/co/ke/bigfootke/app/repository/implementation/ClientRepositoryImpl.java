package co.ke.bigfootke.app.repository.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import co.ke.bigfootke.app.entities.Client;
import co.ke.bigfootke.app.entities.Group;
import co.ke.bigfootke.app.pojos.Page;
import co.ke.bigfootke.app.pojos.ClientPagedData;
import co.ke.bigfootke.app.repository.ClientRepository;

@Repository
public class ClientRepositoryImpl implements ClientRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	//for testing response time REMEMBER TO DELETE DURING PRODUCTION
	private Long startTime = 0L;
	private Long endTime = 0L;
	@SuppressWarnings("unused")
	private Long totalTime = 0L;
	
	private Map<String, String> response;
	private List<Object> responseList;
	
	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client findById(Long scheduleId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**Retrieves Client object from the database by id 
	 * if they already have an id**/
	public Client findById(Long id, Session session) {
		Client client = null;
		Criteria criteria = session.createCriteria(Client.class);
		criteria.add(Restrictions.eqOrIsNull("clientId", id));			
		//Get the client list of the requested page
		client = (Client) criteria.uniqueResult();
		return client;	
	}
	
	/**Checks if client exists 
	 * if true it sends an error response
	 * otherwise 
	 * it saves the new client to the database and
	 * sends a success response
	 * **/
	@Override
	public ResponseEntity<Object> createClient(Client client){
		response = new HashMap<>();
		try {
			if(findByPhone(client.getPhoneNo()) !=null) {
				response.put("message", "Phone Number already exists");
				//send the response to the webClient with error 
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}else {
				save(client);
				response.put("message", "Successfully added new client");
			}
		}catch(HibernateException e) {
			e.printStackTrace();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(response, HttpStatus.OK);		
	}
	
	/**Saves a new Client to the database**/
	public void save(Client client) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();		
		try {
			session.save(client);
			trans.commit();
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}	
	
	/** Retrieves all paginated Clients from the database
	 * maxResults = e.g. 100
	 * clientsPerViewPage = size
	 * totalRecords = totalElements
	 * firstResult = pageNumber
	 * totalRecords/maxResults = totalPages
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<ClientPagedData> findByPage(int clientsPerViewPage, int firstResult, int maxResults){

		Session session = sessionFactory.openSession();		
		ClientPagedData response = null;
		int totalPages = 0;
		try {
			Criteria criteria = session.createCriteria(Client.class);
			//Order the clients list by id
			criteria.addOrder(Order.asc("clientId"));
			//Retrieve the first page result
			criteria.setFirstResult(firstResult);
			//Retrieve the maximum page result
			criteria.setMaxResults(maxResults);
			
			//Set time for operation to begin
			startTime = System.currentTimeMillis();
						
			//Get the client list of the requested page
			List<Client> clientPagedData = criteria.list();
			//Retrieve the total count of the clients in the database
			criteria.setProjection(Projections.rowCount());
			Long totalRecords = (Long) criteria.uniqueResult();
			int totalElements = totalRecords.intValue();
			//Check if the clients in the database are more than the clients view number
			if(totalElements == 0)
				totalPages = 0;
			else if(totalElements <= clientsPerViewPage) {
				totalPages = 1;
			}else if((totalElements % clientsPerViewPage) != 0)
				totalPages = (totalElements/clientsPerViewPage) + 1;
			else
				totalPages = (totalElements/clientsPerViewPage);
			//setup the page data for ngx-datatable in angular 5
			Page pageResponse = new Page(clientsPerViewPage, totalElements, totalPages, firstResult);
			//Add the retrieved client data list and the page data
			response = new ClientPagedData(clientPagedData, pageResponse);
			
			/**DELETE**/
				for (Client c : clientPagedData) {
		            c.getGroups().size();
		        }			
				//Set time for operation to begin
				endTime = System.currentTimeMillis();
				//Calculate total time taken to carry out operation
				totalTime = endTime - startTime;			
			
		}catch(HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<ClientPagedData>(response, HttpStatus.OK);
	}
	
	/**Retrieves Client object from the database 
	 * that has a specific phone number**/
	@Override
	public Client findByPhone(String phoneNo) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Client.class);
		Client client = null;
		try {
			criteria.add(Restrictions.eqOrIsNull("phoneNo", phoneNo));			
			//Get the client list of the requested page
			client = (Client) criteria.uniqueResult();			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return client;	
	}
	
	/**Updates a Client object 
	 * in the database with its new values**/
	@Override
	public ResponseEntity<Object> updateClient(Client client) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();		

		Client search = null;
		
		Client updatedClient = null;
		responseList = new ArrayList<>();
		response = new HashMap<>();
		try {
			Criteria criteria = session.createCriteria(Client.class);
			criteria.add(Restrictions.eqOrIsNull("phoneNo", client.getPhoneNo()));
			search = (Client) criteria.uniqueResult();
			if(search == null) {
				updatedClient = (Client) session.merge(client);
				trans.commit();
				response.put("message", "Successfully updated Client");
				responseList.add(response);
				responseList.add(updatedClient);
			}else {
				response.put("message", "There already exists a client with that phone no");
				responseList.add(response);
				responseList.add(search);
				//send the response to the webClient
				return new ResponseEntity<Object>(responseList, HttpStatus.CONFLICT);
			}
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(responseList, HttpStatus.OK);
	}
	
	/**Deletes clients from the database 
	 * and returns a response
	 * ***/
	@Override
	public ResponseEntity<Object> deleteClient(Long id) {
		Session session = sessionFactory.openSession();
		Transaction trans = null;
		response = new HashMap<>();
		try {
			Client client = findById(id, session);
			if(client == null) {
				response.put("message", "Error: Client with ID:"+id+" does not Exist!!");
				//send the response to the webClient
				return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
			}
			response.put("message", "Successfully DELETED "+client.getFullName()+
					" with Phone No: "+client.getPhoneNo());
			trans = session.beginTransaction();
			//Remove all group relation associated with the client. This is to
			//ensure the groups are not deleted together with the client being deleted
			client.getGroups().removeAll(client.getGroups());
			session.delete(client);
			trans.commit();
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	/**Adds a Client object 
	 * to a Group Object in the database**/
	public ResponseEntity<Object> addToGroup(Long groupId, List<Long> clientIds) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();	
		responseList = new ArrayList<>();
		Client client = null;
		Group group = null;
		try {
			Criteria groupCriteria = session.createCriteria(Group.class);
			groupCriteria.add(Restrictions.eqOrIsNull("groupId", groupId));
			group = (Group) groupCriteria.uniqueResult();
			if(group == null) {
				response = new HashMap<>();
				response.put("message", "Error: Group is none-existant ");
				responseList.add(response);
			}else {
				for(Long clientId: clientIds) {
					response = new HashMap<>();
					client = findById(clientId,session);
					if(client == null) {
						response.put("message", "Error: Client ID: "+clientId+" is none-existant ");
						responseList.add(response);
					}else {
						group.getClients().add(client);
						session.merge(group);
						response.put("message", "Successfuly added Client ID: "+clientId+" to Group ID: "+groupId);
						responseList.add(response);
					}
				}
			}
			trans.commit();
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}		
		//send the response to the webClient
		return new ResponseEntity<Object>(responseList, HttpStatus.OK);
	}
			
	/**Deletes a Client object 
	 * from a Group Object in the database**/
	public ResponseEntity<Object>  deleteFromGroup(Long clientId, Long groupId) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();	
		Client client = null;
		Group group = null;
		try {
			Criteria groupCriteria = session.createCriteria(Group.class);
			groupCriteria.add(Restrictions.eqOrIsNull("groupId", groupId));
			group = (Group) groupCriteria.uniqueResult();
			if(group == null) {
				response = new HashMap<>();
				response.put("message", "Error: Group is none-existant ");
			}else {
				response = new HashMap<>();
				client = findById(clientId,session);
				if(client == null) {
					response.put("message", "Error: Client ID: "+clientId+" is none-existant ");
				}else {
					group.getClients().remove(client);
					session.merge(group);
					response.put("message", "Successfuly removed Client ID: "+clientId+" from Group ID: "+groupId);
				}
			}
			trans.commit();
		}catch(HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}		
		//send the response to the webClient
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
		
	/* Retrieves all paginated Clients 
	 * maxResults = e.g. 50
	 * elementsPerPage = size
	 * totalRecords = totalElements
	 * firstResult = pageNumber
	 * totalRecords/maxResults = totalPages
	 * **/
	@SuppressWarnings("unchecked")
	public ResponseEntity<ClientPagedData> findPagedByGroup(Long groupId, int clientsPerViewPage, int firstResult, int maxResults){
		
		Session session = sessionFactory.openSession();
		Criteria clientCriteria = session.createCriteria(Client.class);
		ClientPagedData response = null;
		int totalPages = 0;
		try {	
			
			//find all clients of the group
			clientCriteria.createCriteria("groups").add(Restrictions.eq("groupId", groupId));
			clientCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//Order the clients list by id
			clientCriteria.addOrder(Order.asc("clientId"));
			//Retrieve the first page result
			clientCriteria.setFirstResult(firstResult);
			//Retrieve the maximum page result
			clientCriteria.setMaxResults(maxResults);
			//get the clients in a list
			List<Client> clients = clientCriteria.list();
			//Retrieve the total count of the clients in the database
			clientCriteria.setProjection(Projections.rowCount());
			Long totalRecords = (Long) clientCriteria.uniqueResult();
			int totalElements = totalRecords.intValue();
			
			//Check if the clients in the database are more than the clients view number
			if(totalElements == 0)
				totalPages = 0;
			else if(totalElements <= clientsPerViewPage) {
				totalPages = 1;
			}else if((totalElements % clientsPerViewPage) != 0)
				totalPages = (totalElements/clientsPerViewPage) + 1;
			else
				totalPages = (totalElements/clientsPerViewPage);
			
			//setup the page data for ngx-datatable in angular 5
			Page pageResponse = new Page(clientsPerViewPage, totalElements, totalPages, firstResult);
			//Add the retrieved client data list and the page data
			response = new ClientPagedData(clients, pageResponse);
			
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		//send the response to the webClient
		return new ResponseEntity<ClientPagedData>(response, HttpStatus.OK);
	}

	/**Batch Processes all clients received
	 * by checking if they exist in the database **/
	public List<Client> batchCheckIfClientExist(List<Client> rawClients) {
		Session session = sessionFactory.openSession();
		List<Client> newClients = new ArrayList<>();
		try {
			for(int i=0; i<rawClients.size(); i++) {
				Client client = rawClients.get(i);
				Client result = findByPhone(client.getPhoneNo());
				if(result == null)
					newClients.add(client);
				if ( i % 20 == 0 ) { //20, same as the JDBC batch size
			        //flush a batch of inserts and release memory:
			        session.flush();
			        session.clear();
				}
			}
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return newClients;
	}

	/**Batch inserts new Clients to the database**/
	public void batchCreateNewClients(List<Client> newClients) {
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		try {
			for(int i=0; i<newClients.size(); i++) {
				Client client = newClients.get(i);
				session.setCacheMode(CacheMode.IGNORE);
				session.save(client);
				if ( i % 20 == 0 ) { //20, same as the JDBC batch size
			        //flush a batch of inserts and release memory:
			        session.flush();
			        session.clear();
				}
			}
			trans.commit();
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
	}	
}
