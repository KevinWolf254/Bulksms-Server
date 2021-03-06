package co.ke.bigfootke.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="week_sms_cost")
public class WeekSmsCost {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "cost_id")
	private Long costId;
	
	@Column(name = "cost")
	private Double cost;
	
	@Column(name = "date_sent")
	private Date dateSent;
	
	@OneToOne
	@JoinColumn(name = "schedule")
	private WeekSchedule schedule;

	public WeekSmsCost() {
	}

	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public WeekSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(WeekSchedule schedule) {
		this.schedule = schedule;
	}	
}
