package co.ke.bigfootke.app.pojos;

import java.util.List;
import co.ke.bigfootke.app.entities.MonthSchedule;

public class PagedMonthSchedule extends PagedData{
	private List<MonthSchedule> schedules;

	public PagedMonthSchedule() {
		super();
	}
	
	public PagedMonthSchedule(List<MonthSchedule> schedules, Page page) {
		super(page);
		this.schedules = schedules;
	}

	public List<MonthSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<MonthSchedule> schedules) {
		this.schedules = schedules;
	}	
}
