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

	@Override
	public List<Ticket> getTickes() {

		return entityManager.createQuery("from Ticket order by id", Ticket.class)
				.getResultList();
	}

}