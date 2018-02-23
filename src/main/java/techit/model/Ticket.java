package techit.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany
	@JoinTable(
			name = "tickets_xref_users", 
			joinColumns = @JoinColumn(name = "ticketId"),
			inverseJoinColumns = @JoinColumn(name = "userId"))
	private List<User> technicians;	// List of all technicians 
	
	@Enumerated
	@Column(nullable = false)
	private Progress currentProgress; // Current progress of the ticket
	
	@Enumerated
	@Column(nullable = false)
	private Priority currentPriority; // Importance or level of urgency of the ticket
	
	@ManyToOne
	@JoinColumn(name = "requesterId", nullable = false)
	private User requester;

	private String subject;			// Subject of the ticket.
	
	private String details; 		// Text concerning the project.
	
	private Date startDate; 		// Project's starting date.
	
	private String startDateTime;	// Time of when the ticket was created.
	
	private Date endDate; 			// When the project was completed.
	
	private Date lastUpdated;		// Last date where changes were made to the ticket (edits, technician updates, etc.)
	
	// DO WE NEED THIS?
	private String lastUpdatedTime; // Same as lastUpdated but this is for the time changes were made.
	
	private String location; 	// Location where the project is.
	
	@OneToMany(mappedBy = "ticket")
	private List<Update> updates;	// List of all updates that was made to the ticket.

	private String completionDetails; // Information pertaining vendors, cost,
										// materials used.

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<User> technicians) {
		this.technicians = technicians;
	}

	public Progress getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(Progress currentProgress) {
		this.currentProgress = currentProgress;
	}

	public Priority getCurrentPriority() {
		return currentPriority;
	}

	public void setCurrentPriority(Priority currentPriority) {
		this.currentPriority = currentPriority;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Update> getUpdates() {
		return updates;
	}

	public void setUpdates(List<Update> updates) {
		this.updates = updates;
	}

	public String getCompletionDetails() {
		return completionDetails;
	}

	public void setCompletionDetails(String completionDetails) {
		this.completionDetails = completionDetails;
	}
}
