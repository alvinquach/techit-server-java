package techit.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean enabled = true;

    public User()
    {
    }
    
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToMany
    private List<Ticket> ticket;
    
    @OneToMany
    private List<Unit> unit;
    

    @Column(nullable = false, unique = true)
    private String username;
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
	private String firstName; 		// User's first name
    @Column(nullable = false)
	private String lastName; 		// User's last name
    
	private String phoneNumber;
	private String department;
	private String email;
	private Position status;
	private int unitId; 		// Describes where the user belongs to in a unit (by

	// Simple constructor for regular users ( students )
    
    

    
	
	// --------------- Getters and Setters below ---------------	

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    @Override
    public String toString()
    {
        return "[" + id + ", " + username + ", " + password + "]";
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Position getStatus() {
		return status;
	}

	public void setStatus(Position status) {
		this.status = status;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

}
