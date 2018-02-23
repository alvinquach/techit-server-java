package techit.model.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import techit.model.Progress;
import techit.model.Ticket;
import techit.model.User;
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
	public List<Ticket> getTicketsByUser(User user) {
		 return entityManager.createQuery( "from Ticket t WHERE t.id =:requester order by id", Ticket.class ).setParameter("requester", user)
		            .getResultList();
	}


    @Override
    @Transactional
    public Ticket saveTicket( Ticket ticket )
    {
        return entityManager.merge( ticket );
    }

	@Override
	public void updateTicket(Long id,Progress progress) {
	Date date = new Date();
		entityManager.find(Ticket.class , id);
		 entityManager
		     .createQuery("update Ticket set Progress = :progress, lastUpdated = :lastupdate where id =:id" )
		     .setParameter("id", id).setParameter("progress", id).setParameter("lastUpdated", date)
		     .executeUpdate();
		
	}


}
