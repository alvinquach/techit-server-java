package techit.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import techit.model.Ticket;
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
	public List<Ticket> getTicketsByUser(User user) {
		return entityManager.createQuery("from Ticket where requester = :requester", Ticket.class)
				.setParameter("requester", user)
				.getResultList();
	}

	@Override
	@Transactional
	public Ticket saveTicket(Ticket ticket) {
		return entityManager.merge(ticket);
	}

}