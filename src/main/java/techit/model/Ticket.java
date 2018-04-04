package techit.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/** List of technicians assigned to the ticket. */
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "tickets_xref_technicians", joinColumns = @JoinColumn(name = "ticketId"), inverseJoinColumns = @JoinColumn(name = "technicianId"))
	private List<User> technicians;

	/** The current status of the ticket. */
	@Enumerated
	@Column(nullable = false)
	private Status status = Status.OPEN;

	/** The Importance or level of urgency of the ticket. */
	@Enumerated
	@Column(nullable = false)
	private Priority priority;

	/** The user who created the ticket. */
	@ManyToOne
	@JoinColumn(name = "createdById", nullable = false)
	private User createdBy;
	
	/** The date that the ticket was created on. */
	@Column(nullable = false)
	private Date createdDate;

	/** The project's starting date. */
	private Date startDate;

	/** The project's completion date. */
	private Date endDate;

	/** Last date where changes were made to the ticket (edits, technician updates, etc.). */
	private Date lastUpdated;
	
	// TODO Add an updated by field?

	/** The subject line of the ticket. */
	@Column(nullable = false)
	private String subject;

	/** Text description of the project. */
	@Lob
	private String details;

	/** Location of the project. */
	private String location;

	/** The unit assigned to the ticket. */
	@ManyToOne 
	@JoinColumn(name="unitId") 
	private Unit unit;
	
	/** List of Updates made to the ticket. */
	@JsonIgnore
	@OneToMany(mappedBy = "ticket")
	private List<Update> updates;

	/** Information pertaining to vendors, costs, materials used, etc. */
	@Lob
	private String completionDetails;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
