package techit.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Ticket")
public class Ticket implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    public Ticket(){
    	
    }
    @Id
    @GeneratedValue
    int id; // Ticket's unique id.
    
    @OneToMany(mappedBy="ticket")
	private List<Update> updates;	// List of all updates that was made to the ticket.
	// Needs more work...
    
    @OneToMany
	private List<User> technicians;	// List of all technicians 

    User user;

	@Column(nullable = false)
	private String phone; // Requestor's phone
	@Column(nullable = false)
	private String email; // Requestor's email. May be different from the User's login email.
	@Column(nullable = false)
	private String department; // Department that is related to the ticket or the person who created the ticket
	@Column(nullable = false)
	private int unitId;				// The unit that was assigned to the ticket.
	@Column(nullable = false)
	private String subject;			// Subject of the ticket.
	@Column(nullable = false)
	private String ticketLocation; 	// Location where the project is.
	
	private Progress currentProgress; // Current progress of the ticket
	private Priority currentPriority; // Importance or level of urgency of the ticket
	private String details; 		// Text concerning the project.
	private Date startDate; 		// Project's starting date.
	private String startDateTime;	// Time of when the ticket was created.
	private Date endDate; 			// When the project was completed.
	private Date lastUpdated;		// Last date where changes were made to the ticket (edits, technician updates, etc.)
	private String lastUpdatedTime; // Same as lastUpdated but this is for the time changes were made.
	private String completionDetails; // Information pertaining vendors, cost,
										// materials used.
	
	
	// --------------- Getters and Setters below ---------------
	
	public String getUserFirstName() {
		return user.getFirstName();
	}

	public void setUserFirstName(String userFirstName) {
		user.setFirstName(userFirstName); 
	}

	public String getUserLastName() {
		return user.getLastName();
	}

	public void setUserLastName(String userLastName) {
		user.setLastName(userLastName);
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user.getUsername();
	}

	public void setUser(String username) {
		user.setUsername(username);
		
	}

	public List<User> getTechnicians() {
		return technicians;
	}

	public void addTechnician(User technician) {
		this.technicians.add(technician);
	}
	
	public void removeTechnician(User technician){
		if(this.getNumOfTechnician() > 0){
			for(int i = 0; i < this.getNumOfTechnician(); i ++ ){
				if(this.technicians.get(i).getId() == technician.getId()){
					this.technicians.remove(i);
				}
			}
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public String getDepartment(){
		return this.department;
	}
	
	public void setDepartment(String department){
		this.department = department;
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

	public String getLastUpdateTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdatedTime = lastUpdateTime;
	}

	public String getTicketLocation() {
		return ticketLocation;
	}

	public void setTicketLocation(String ticketLocation) {
		this.ticketLocation = ticketLocation;
	}

	public String getCompletionDetails() {
		return completionDetails;
	}

	public void setCompletionDetails(String completionDetails) {
		this.completionDetails = completionDetails;
	}

	public List<Update> getUpdates() {
		return updates;
	}

	public void setUpdates(List<Update> updateComments) {
		this.updates = updateComments;
	}

	public Progress getProgress() {
		return this.currentProgress;
	}

	public void setProgress(Progress progress) {
		
			this.currentProgress = progress;
				
	}
	
	public Priority getPriority(){
		return this.currentPriority;
	}
	
	public void setPriority(Priority priority){
			this.currentPriority = priority;
		
	}
	
	public Priority getPriorityNumeric(){
		return this.currentPriority;
	}
	
	public int getNumOfTechnician(){
		return this.technicians.size();
	}
	
	public int getNumOfUpdates(){
		return this.updates.size();
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String toString(){
		return "Ticket #: " + this.id + "\n"
				+ "Requestor's Name: " + user.getFirstName() + " " 
				+ user.getLastName() + "\n" 
				+ "Location: " + this.ticketLocation +"\n"
				+ "Department: " + this.department + "\n"
				+ "Subject: " +this.subject +"\n"
				+ "Details: " + this.details;
	}
	
	public boolean isTechnician(String username){
		for(int i = 0; i < this.technicians.size(); i ++){
			if(this.technicians.get(i).getUsername().equals(username)){
				return true;
			}
		}
		return false;
	}
	
	
}
