package techit.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import techit.model.Ticket;
import techit.model.Unit;
import techit.model.User;
import techit.model.dao.TicketDao;
import techit.rest.error.EntityDoesNotExistException;


@Repository
public class TicketDaoImpl implements TicketDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Ticket> getTickets() {
		return entityManager.createQuery("from Ticket order by id", Ticket.class)
				.getResultList();
	}
	
	
	/**
	 * Gets the ticket by ID.
	 * Note that the technicians list in the {@code Ticket} object are lazy loaded, 
	 * but are not initialized by this method, so accessing the list will throw an error.
	 * The technicians must by retrieved separately using {@code getTicketTechnicians()}.
	 */
	@Override
	public Ticket getTicket(Long id) {
		Ticket ticket = entityManager.find(Ticket.class, id);
		return ticket;
	}
	
	
	/** Gets the ticket by ID. Also loads the list of technicians. */
	@Override
	@Transactional
	public Ticket getTicketWithTechnicians(Long id) {
		Ticket ticket = entityManager.find(Ticket.class, id);
		if (ticket == null) {
			return null;
		}
		Hibernate.initialize(ticket.getTechnicians());
		return ticket;
	}

	@Override
	public List<Ticket> getTicketsByCreator(User user) {
		return entityManager.createQuery("from Ticket where createdBy = :createdBy", Ticket.class)
				.setParameter("createdBy", user)
				.getResultList();
	}
	
	@Override
	public List<Ticket> getTicketsByTechnician(User technician) {
		String query = "from Ticket ticket where :technician in elements(ticket.technicians)";
		return entityManager.createQuery(query, Ticket.class)
				.setParameter("technician", technician)
				.getResultList();
	}

	@Override
	@Transactional
	public Ticket saveTicket(Ticket ticket) {
		return entityManager.merge(ticket);
	}

	@Override
	public List<Ticket> getTicketsByUnit(Unit unit) {
		return entityManager.createQuery("from Ticket where unit = :unit", Ticket.class)
				.setParameter("unit", unit)
				.getResultList();
	}

}