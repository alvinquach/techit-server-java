package techit.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import techit.model.Ticket;
import techit.model.dao.TicketDao;


@Repository
public class TicketDaoImpl implements TicketDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Ticket getTicket( Long id )
    {
        return entityManager.find( Ticket.class, id );
    }

    @Override
    public List<Ticket> getUserTickets()
    {
        return entityManager.createQuery( "from Ticket t WHERE t.id =:requester order by id", Ticket.class )
            .getResultList();
    }

    @Override
    @Transactional
    public Ticket saveTicket( Ticket ticket )
    {
        return entityManager.merge( ticket );
    }


}
