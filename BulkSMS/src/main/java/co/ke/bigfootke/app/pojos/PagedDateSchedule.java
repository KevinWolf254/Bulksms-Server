package co.ke.bigfootke.app.pojos;

import java.util.List;
import co.ke.bigfootke.app.entities.DateSchedule;

public class PagedDateSchedule extends PagedData{
	private List<DateSchedule> schedules;

	public PagedDateSchedule() {
		super();
	}
	
	public PagedDateSchedule(List<DateSchedule> schedules, Page page) {
		super(page);
		this.schedules = schedules;
	}

	public List<DateSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<DateSchedule> schedules) {
		this.schedules = schedules;
	}	
}
