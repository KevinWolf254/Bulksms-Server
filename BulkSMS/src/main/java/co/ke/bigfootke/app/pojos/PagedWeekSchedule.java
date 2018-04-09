package co.ke.bigfootke.app.pojos;

import java.util.List;
import co.ke.bigfootke.app.entities.WeekSchedule;

public class PagedWeekSchedule extends PagedData{
	private List<WeekSchedule> schedules;

	public PagedWeekSchedule() {
		super();
	}
	
	public PagedWeekSchedule(List<WeekSchedule> schedules, Page page) {
		super(page);
		this.schedules = schedules;
	}

	public List<WeekSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<WeekSchedule> schedules) {
		this.schedules = schedules;
	}	
}
