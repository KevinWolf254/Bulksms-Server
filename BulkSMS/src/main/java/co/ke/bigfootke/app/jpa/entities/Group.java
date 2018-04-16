package co.ke.bigfootke.app.jpa.entities;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="groupId")
@Entity
@Table(name="groups")
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="group_id")
	private Long groupId;
	
	@NotBlank(message="Please enter Group Name")
	@Column(name="group_name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, 
		cascade = {CascadeType.PERSIST, CascadeType.MERGE})
		@JoinTable(name="group_clients",
			inverseJoinColumns=@JoinColumn(name = "client_fk"),
			joinColumns=@JoinColumn(name = "group_fk"))
	private Set<Client> clients;
	
	@ManyToMany(mappedBy = "groups",
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ScheduledSms> schedule;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="ondemand_groups",
				inverseJoinColumns=@JoinColumn(name = "sms_fk"),
				joinColumns=@JoinColumn(name = "group_fk"))
	private Sms sms;
	
	/**ScheduledMessage being mappedBy 
	 * Group 
	 * to form Group_ScheduledMessage
//	@ManyToMany(fetch = FetchType.EAGER,
//				cascade = CascadeType.ALL)
//	@JoinTable(name="smessage_group",
//		joinColumns=
//			@JoinColumn(name = "group_fk"),
//		inverseJoinColumns=
//			@JoinColumn(name = "smessage_fk"))
//	@JsonBackReference(value = "smsg_groups")
//	private Set<ScheduledMessage> sMessage;
	**/
	
	public Group() {
	}	
	
	public Group(Group group) {
		this.groupId = group.groupId;
		this.name = group.name;
		this.clients = group.clients;
	}

	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long id) {
		this.groupId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public Set<Client> getClients() {
		return clients;
	}
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
//	@JsonIgnore
//	public Set<DateSchedule> getDateSchedule() {
//		return dateSchedule;
//	}
//
//	public void setDateSchedule(Set<DateSchedule> dateSchedule) {
//		this.dateSchedule = dateSchedule;
//	}
//	@JsonIgnore
//	public Set<WeekSchedule> getWeekSchedule() {
//		return weekSchedule;
//	}
//	
//	public void setWeekSchedule(Set<WeekSchedule> weekSchedule) {
//		this.weekSchedule = weekSchedule;
//	}
//	@JsonIgnore
//	public Set<MonthSchedule> getMonthSchedule() {
//		return monthSchedule;
//	}
//
//	public void setMonthSchedule(Set<MonthSchedule> monthSchedule) {
//		this.monthSchedule = monthSchedule;
//	}
	@JsonIgnore
	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}

	@JsonIgnore	
	public Set<ScheduledSms> getSchedule() {
		return schedule;
	}

	public void setSchedule(Set<ScheduledSms> schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", name=" + name + "]";
	}
	
}
