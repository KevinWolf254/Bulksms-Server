package co.ke.bigfootke.app.pojos;

import java.util.List;

import co.ke.bigfootke.app.entities.OnDemandSms;

public class PagedOnDemandSms extends PagedData{
	private List<OnDemandSms> sms;

	public PagedOnDemandSms() {
		super();
	}
	
	public PagedOnDemandSms(List<OnDemandSms> sms, Page page) {
		super(page);
		this.sms = sms;
	}

	public List<OnDemandSms> getSms() {
		return sms;
	}

	public void setSchedules(List<OnDemandSms> sms) {
		this.sms = sms;
	}	
}
