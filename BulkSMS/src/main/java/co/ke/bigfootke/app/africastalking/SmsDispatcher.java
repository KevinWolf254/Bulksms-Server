package co.ke.bigfootke.app.africastalking;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class SmsDispatcher {
	private Map<String, String> response;
	
	private static final Logger log = LoggerFactory.getLogger(SmsDispatcher.class);
	String username = "KevinWolf254";
	private String apiKey = "83d67fbaaada158aaaede8d41aced7ca35582cad4ebd51409135b35ef49f436d";
	private AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
	
	public ResponseEntity<Object> sendOnDemandSms(String recipients, String message) {
		response = new HashMap<>();
		 try {
	            JSONArray results = gateway.sendMessage(recipients, message);
	            for( int i = 0; i < results.length(); ++i ) {
	                JSONObject result = results.getJSONObject(i);
	                log.info(result.getString("status")
	                		+" "+result.getString("number")
	                		+" "+result.getString("messageId")
	                		+" "+result.getString("cost"));
	            }
    			response.put("message", "Success: BulkSms has been sent");
                log.info("***** SMS has been sent successfully");
	        } catch (Exception e) {
	        	log.info("***** ERROR!! sending SMS - " + e.getMessage());
    			response.put("message", "Error: Something went wrong communicating with SMS API provider");
	        }
			return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
