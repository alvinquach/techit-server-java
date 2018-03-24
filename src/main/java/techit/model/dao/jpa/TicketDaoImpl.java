package techit.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import techit.model.Ticket;
import techit.model.Unit;
import techit.model.User;
import techit.model.dao.TicketDao;


@Repository
public class TicketDaoImpl implements TicketDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Ticket getTicket(Long id) {
		return entityManager.find(Ticket.class, id);
	}

	@Override
	public List<Ticket> getTicketsByRequestor(User user) {
		return entityManager.createQuery("from Ticket where requestorId = :requestor", Ticket.class)
				.setParameter("requestor", user)
				.getResultList();
	}
	@Override
	public List<Ticket> getTechnicianTickets(User technician) {
		String query = "select t from Ticket t join t.tickets_xref_users tt "
	            + "where tt = :technician";

	        return entityManager.createQuery( query, Ticket.class )
	            .setParameter( "technician", technician )
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

	@Override
	public List<Ticket> getTickes() {
		
		 return entityManager.createQuery("from Ticket order by id", Ticket.class)
				.getResultList();
	}

}