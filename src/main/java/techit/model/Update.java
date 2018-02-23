package techit.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "updates")
public class Update implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ticketId", nullable = false)
	private Ticket ticket; // associated ticket

	@ManyToOne
	@JoinColumn(name = "modifiedById", nullable = false)
	private User modifiedBy;
	
	private String updateDetails;
	
	@Column(nullable = false)
	private Date modifiedDate;
	
	public Update(){
		
	}

	public Update(Long id, Ticket ticket, User modifiedBy, String updateDetails, Date modifiedDate) {
		super();
		this.id = id;
		this.ticket = ticket;
		this.modifiedBy = modifiedBy;
		this.updateDetails = updateDetails;
		this.modifiedDate = modifiedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getUpdateDetails() {
		return updateDetails;
	}

	public void setUpdateDetails(String updateDetails) {
		this.updateDetails = updateDetails;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
