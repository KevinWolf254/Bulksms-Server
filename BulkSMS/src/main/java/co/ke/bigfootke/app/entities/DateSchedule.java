package co.ke.bigfootke.app.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="scheduleId")
@Entity
@Table(name="date_schedule")
public class DateSchedule {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "schedule_id")
	private Long scheduleId;	
	
	@NotBlank(message="Please Title for the Schedule")
	@Column(name = "title", unique = true)
	private String title;
	
	@NotBlank(message="Please enter the sms Message")
	@Column(name = "message")
	private String message;
	
	@NotBlank(message="Please enter date of when to send sms")
	@Column(name = "date")
	private String date;
	
	@NotBlank(message="Please enter time to send the sms")
	@Column(name = "time")
	private String time;

	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="date_schedule_groups",
				inverseJoinColumns=@JoinColumn(name = "group_fk"),
				joinColumns=@JoinColumn(name = "schedule_fk"))
		private Set<Group> groups;
	
	public DateSchedule() {
	}
	
	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@JsonIgnore
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}	
}
