package co.ke.bigfootke.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.ke.bigfootke.app.entities.Client;
import co.ke.bigfootke.app.pojos.ClientPagedData;
import co.ke.bigfootke.app.repository.ClientRepository;
import co.ke.bigfootke.app.repository.implementation.ClientRepositoryImpl;

@Service
public class ClientService implements ClientRepository{
	@Autowired
	private ClientRepositoryImpl repository;
	
	@Override
	public ResponseEntity<Object> createClient(Client client) {
		return repository.createClient(client);
	}

	@Override
	@Cacheable(value = "clients.paged", key="#firstResult", unless = "#result != null")
	public ResponseEntity<ClientPagedData> findByPage(int clientsPerViewPage, int firstResult, int maxResults) {
		return repository.findByPage(clientsPerViewPage, firstResult, maxResults);
	}
	
	@Override
	@Cacheable(value = "client.byPhone", key="#phoneNo", unless = "#result != null")
	public Client findByPhone(String phoneNo) {
		return repository.findByPhone(phoneNo);
	}

	@Override
	@Caching(evict = {@CacheEvict(value = "client.byPhone", allEntries = true)},
	        	put = {@CachePut(value = "clients.paged", key = "#client.clientId", unless = "#result != null"),
	        			@CachePut(value = "client.byGroup", key = "#client.clientId", unless = "#result != null")})
	public ResponseEntity<Object> updateClient(Client client) {	
		return repository.updateClient(client);
	}
	
	@Override
	@Caching(evict = {@CacheEvict(value = "clients.paged", key = "#clientId"), 
						@CacheEvict(value = "client.byPhone", key = "#clientId"),
						@CacheEvict(value = "client.byGroup", key = "#clientId")})
	public ResponseEntity<Object> deleteClient(Long clientId) {
		return repository.deleteClient(clientId);
	}
	
	public ResponseEntity<Object> addToGroup(Long groupId, List<Long> clientIds) {
		return repository.addToGroup(groupId, clientIds);
	}
	
	/**Retrieves paged clients of a certain group 
	 * taking groupId as one of the parameters**/
	@Cacheable(value = "client.byGroup", key="#groupId", unless = "#result != null")
	public ResponseEntity<ClientPagedData> findPagedByGroup(Long groupId, int firstResult) {
		int clientsPerViewPage = 20;
		int maxResults = 100;
		return repository.findPagedByGroup(groupId, clientsPerViewPage, firstResult, maxResults);
	}

	public ResponseEntity<Object> batchProcessClients(MultipartFile excelfile) {
		List<Client> rawClients = new ArrayList<>();
		Map<String, String> response = new HashMap<>();
		
		try {			
			int i = 0;
			// Creates a workbook object from the uploaded excelfile
			XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());
			// Creates a worksheet object representing the first sheet
			XSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object representing a single row in excel
				XSSFRow row = worksheet.getRow(i++);
				// Creates an object for the Client Model
				Client client = new Client();
				// Sets the Read data to the model class				
				client.setCountryCode("+"+(Integer.toString((int) row.getCell(0).getNumericCellValue())));
				client.setPhoneNo(Integer.toString((int) row.getCell(1).getNumericCellValue()));
				client.setFullName(row.getCell(2).getStringCellValue());
				client.setTelecom(row.getCell(3).getStringCellValue());				
				//Add client to the rawClients List
				rawClients.add(client);
			}			
			workbook.close();
			//Check if clients already exist and get the clients that do not exist
			List<Client> newClients = repository.batchCheckIfClientExist(rawClients);
			//save the clients that do not exist to database
			repository.batchCreateNewClients(newClients);
			response.put("message", "Successfully CREATED "+newClients.size()+" NEW Clients. If more expected"
					+ "they were duplicates! ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//should return message of completion 
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> deleteFromGroup(Long clientId, Long groupId) {
		return repository.deleteFromGroup(clientId, groupId);
	}

	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client findById(Long clientId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String findByGroup(List<Long> groupsIds) {
		return repository.findByGroup(groupsIds);
	}

	@Override
	public ResponseEntity<ClientPagedData> findPagedByGroup(Long groupId, int clientsPerViewPage, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchCreateNewClients(List<Client> newClients) {
		// TODO Auto-generated method stub
		
	}

}
