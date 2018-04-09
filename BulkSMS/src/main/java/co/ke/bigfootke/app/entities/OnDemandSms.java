package co.ke.bigfootke.app.entities;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="smsId")
@Entity
@Table(name="ondemand_sms")
public class OnDemandSms {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sms_id")
	private Long smsId;
	
	@NotBlank(message="Please enter message")
	@Column(name="message")
	private String message;
	
	@Column(name="cost", nullable = true)
	private Double cost;
	
	@Column(name="date")
	private Date date;
	
	/**NB: DISABLED THE MAPPED BY FEATURE SO THAT
	 * THE ONDEMANDSMS TABLE DOES NOT HAVE AN EXTRA COLUMN
	 * INSTEAD A NEW VIEW TABLE WILL BE CREATED**/
	@OneToMany	
//	(mappedBy = "sms",
//			fetch = FetchType.LAZY,
//			cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<Group> groups;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="user_sms",
				inverseJoinColumns=@JoinColumn(name = "user_fk"),
				joinColumns=@JoinColumn(name = "sms_fk"))
	private User sender;
	
	public OnDemandSms() {
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@JsonIgnore
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}	
}
