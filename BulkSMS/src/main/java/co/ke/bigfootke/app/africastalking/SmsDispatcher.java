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
	String username = "sandbox";
	private String apiKey = "ad265875a1f43287b2fec500b7902bfb5d58ed5448323d64e04c361639d88fb3";
	private AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
	
	public ResponseEntity<Object> sendOnDemandSms(String recipients, String message) {
//		String recipients = "+254724166274";
		response = new HashMap<>();
		 try {
	            JSONArray results = gateway.sendMessage(recipients, message);
	            for( int i = 0; i < results.length(); ++i ) {
	                JSONObject result = results.getJSONObject(i);
	                log.info(result.getString("status")
	                		+" "+result.getString("number")
	                		+" "+result.getString("messageId")
	                		+" "+result.getString("cost"));
	    			response.put("message", "Success: BulkSms has been sent");
	            }
	        } catch (Exception e) {
                log.info("**************ERROR********************");
	        	log.info("Encountered error while sending: " + e.getMessage());
                log.info("***************************************");
    			response.put("message", "Error: Something went wrong communicating with SMS API provider");
	        }
			return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
