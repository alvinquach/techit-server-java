package techit.model.dao;

import java.util.List;

import techit.model.Ticket;
import techit.model.Unit;
import techit.model.User;

public interface TicketDao {

	Ticket getTicket(Long id);
	
	Ticket getTicketWithTechnicians(Long id);

	List<Ticket> getTicketsByCreator(User user);
	
	List<Ticket> getTicketsByUnit(Unit unit);

	List<Ticket> getTickets();

	List<Ticket> getTicketsByTechnician(User user);
	
	Ticket saveTicket(Ticket ticket);

}