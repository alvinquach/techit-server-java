package techit.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "units")
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	/** Name of the department. */
	// TODO Should this be unique?
	@Column(nullable = false)
	private String name;
	
	public Unit() {}
	
	public Unit(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Unit)) {
			return false;
		}
		if (id == null || ((Unit)o).getId() == null) {
			return false;
		}
		return id.equals(((Unit)o).getId());
	}
	
	@Override
	public int hashCode() {
		return (int) (id * 13 + 17);
	}

}
