package techit.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import techit.model.Priority;
import techit.model.Status;
import techit.model.Ticket;
import techit.model.Unit;
import techit.model.User;

@Test(groups = "TicketDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TicketDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    TicketDao ticketDao; 

    @Test
    public void getTicket() {
        assert ticketDao.getTicket(1L).getCreatedBy().getId() == 1L;
    }
    
    @Test
    public void getTicketsByCreatedBy() {
    	
    	// Create a user for querying.
    	User user = new User();
    	user.setId(2L);
    	
    	// Query for the tickets.
		List<Ticket> tickets = ticketDao.getTicketsByCreator(user);
		
		// There should be at least 2 tickets requested by user 2, which were added by the sql create script.
		if (tickets.size() < 2) {
			assert false;
		}
				
		// Check if the users's ID in each of the tickets match that of the queried user.
		for (Ticket ticket : tickets) {
			if (ticket.getCreatedBy() == null || ticket.getCreatedBy().getId() != user.getId()) {
				assert false;
			}
		}
    	
        assert true;
    }

    @Test
    public void saveTicket() {
    	User user = new User();
    	user.setId(1L);
    	
    	Ticket ticket = new Ticket();
        ticket.setPriority(Priority.NA);
        ticket.setStatus(Status.ONHOLD);
        ticket.setCreatedBy(user);
        
        ticket = ticketDao.saveTicket(ticket);
        
        assert ticket.getId() != null;
    }
    

    @Test
    public void getTicketsByUnit() {
    	Unit unit = new Unit();
    	unit.setId(1L);
    	
    	// Query for the tickets.
    			List<Ticket> tickets = ticketDao.getTicketsByUnit(unit);
    			
    			// There should be at least 2 tickets requested by user 2, which were added by the sql create script.
    			if (tickets.size() < 2) {
    				assert false;
    			}
    					
    			// Check if the users's ID in each of the tickets match that of the queried user.
    			for (Ticket ticket : tickets) {
    				if (ticket.getUnit() == null || ticket.getUnit().getId() != unit.getId()) {
    					assert false;
    				}
    			}
    	    	
    	        assert true;
    }
}
