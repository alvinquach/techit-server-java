package techit.model.dao;

import java.util.List;

import techit.model.Progress;
import techit.model.Ticket;
import techit.model.User;

public interface TicketDao {

    Ticket getTicket( Long id );

    List<Ticket> getTicketsByUser(User requester);
    
    Ticket saveTicket( Ticket ticket );
    
    void updateTicket( Long id ,Progress progress);

	

}