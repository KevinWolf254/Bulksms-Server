package co.ke.bigfootke.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.ke.bigfootke.app.entities.Client;
import co.ke.bigfootke.app.pojos.Family;
import co.ke.bigfootke.app.pojos.ClientPagedData;
import co.ke.bigfootke.app.services.ClientService;

@RestController
@RequestMapping(value = "api/client")
@CrossOrigin(origins="http://localhost:4200")
public class ClientController {

	@Autowired
	private ClientService service;
	
	private Map<String, String> response;	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createClient(@RequestBody @Valid Client client, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			response = new HashMap<>();
			for(FieldError error:bindingResult.getFieldErrors()) {
				response.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if(service.findByPhone(client.getPhoneNo()) != null) {
			response.put("message", "Error: Client already exists");
			return new ResponseEntity<>( response, HttpStatus.CONFLICT);
		}
		return service.createClient(client);
	}	
	
	/**Add Multiple Clients from Excel 2007
	 * by database batch processing**/
	@RequestMapping(value = "/processExcel2007", method = RequestMethod.POST)
	public ResponseEntity<Object> createClientsFromExcel(@RequestParam("file") MultipartFile excelfile) {	
		return service.batchProcessClients(excelfile);	
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/list/{firstResult}")
	public ResponseEntity<ClientPagedData> getAllClients(@PathVariable int firstResult) {
		int clientsPerViewPage = 20;
		int maxResults = 100;
		return service.findByPage(clientsPerViewPage, firstResult, maxResults);
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/{phoneNo}")
	public ResponseEntity<Object> getByPhone(@PathVariable String phoneNo) {
		Client client = service.findByPhone(phoneNo);
		if(client == null) {
			response = new HashMap<>();
			response.put("message", "Error: Could not find "+phoneNo);
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(client, HttpStatus.OK);
	}
		
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Client client) {
		return service.updateClient(client);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{clientId}")	
	public ResponseEntity<Object> delete(@PathVariable Long clientId){
		return service.deleteClient(clientId);
	}
	
	/** Add Client(s) to group **/
	@RequestMapping(method=RequestMethod.POST, value="/group")
	public ResponseEntity<Object> addToGroup(@RequestBody Family family) {		
		return service.addToGroup(family.getParentId(), family.getChildrenIds());
	}
	
	/**Get clients in a certain group**/	
	@RequestMapping(method=RequestMethod.GET, value = "/group/{groupId}/{firstResult}")
	public ResponseEntity<ClientPagedData> getPagedClientsByGroup(@PathVariable Long groupId, @PathVariable int firstResult) {
		return service.findPagedByGroup(groupId,firstResult);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/delete/{clientId}/from/{groupId}")	
	public ResponseEntity<Object> deleteClientFromGroup(@PathVariable Long clientId, @PathVariable Long groupId){
		return service.deleteFromGroup(clientId, groupId);
	}
	
	@RequestMapping(value = "/processExcel", method = RequestMethod.POST)
	public String processExcel(@RequestParam("excelfile") MultipartFile excelfile) {		
//		try {
//			int i = 0;
//			// Creates a workbook object from the uploaded excelfile
//			HSSFWorkbook workbook = new HSSFWorkbook(excelfile.getInputStream());
//			// Creates a worksheet object representing the first sheet
//			HSSFSheet worksheet = workbook.getSheetAt(0);
//			// Reads the data in excel file until last row is encountered
//			while (i <= worksheet.getLastRowNum()) {
//				// Creates an object for the Client Model
//				Client client = new Client();
//				// Creates an object representing a single row in excel
//				HSSFRow row = worksheet.getRow(i++);
//				// Sets the Read data to the model class
//				client.setCountryCode(row.getCell(0).getStringCellValue());
//				client.setPhoneNo(row.getCell(1).getStringCellValue());
//				client.setFullName(row.getCell(2).getStringCellValue());
//				client.setTelecom(row.getCell(3).getStringCellValue());
//				// persist data into database in here
//				service.create(client);
//			}			
//			workbook.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//should return message of completion otherwise error
		return "hello";	
	}
	
}
