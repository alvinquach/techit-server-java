package techit.model.dao;

import java.util.List;

import techit.model.Progress;
import techit.model.Ticket;

public interface TicketDao {

    Ticket getTicket( Long id );

    List<Ticket> getUserTickets(Long id);
    
    Ticket saveTicket( Ticket ticket );
    
    void updateTicket( Long id ,Progress progress);
    

}