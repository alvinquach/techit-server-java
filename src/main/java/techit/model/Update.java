package techit.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Update")
public class Update implements Serializable {
	private static final long serialVersionUID = 1L;
	

    @Id
    @GeneratedValue
	private int id;
    
    @ManyToOne
    private Ticket ticket;
    
    @Column(nullable = false)
	private int ticketId;
    @Column(nullable = false)
	private String modifier; // modifier's username
    @Column(nullable = false)
	private String modifiedDate;

    private String updateDetails;
   
    public Update(){
		
	}
	
	// --------------- Getters and Setters below ---------------	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTicket() {
		return ticketId;
	}
	public void setTicket(int ticket) {
		this.ticketId = ticket;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getUpdateDetails() {
		return updateDetails;
	}
	public void setDetails(String details) {
		this.updateDetails = details;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}