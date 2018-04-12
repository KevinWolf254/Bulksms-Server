package co.ke.bigfootke.app.pojos;

import java.util.List;

public class DispatchedSms {

	private String message;
	private List<Long> groupIds;
	private Double cost;
	private Long senderId;
	
	public DispatchedSms() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Long> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}	
}
