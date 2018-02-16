package techit.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Unit")
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;

	public Unit() {
    	
    }

	@Id
    @GeneratedValue
	private int id; // Unit's unique id.

	@Column(nullable = false)
	private String name; // Name of the department.

	// A unit may have more than one supervisor. This will allow them to assign
	// temporary leads when they are gone.
	private String phone;
    private String location;
    private String email;
    private String description;
    
	// --------------- Getters and Setters below ---------------
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setContact(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
