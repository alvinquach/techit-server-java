package techit.model.dao;

import java.util.List;

import techit.model.Ticket;
import techit.model.User;

public interface TicketDao {

    Ticket getTicket( Long id );

    Ticket saveTicket( Ticket ticket );

	List<Ticket> getTicketsByUser(User user);
		
   

	

}