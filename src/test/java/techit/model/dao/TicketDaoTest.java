package techit.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import techit.model.Priority;
import techit.model.Progress;
import techit.model.Ticket;
import techit.model.User;

@Test(groups = "TicketDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TicketDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    TicketDao ticketDao; 

    @Test
    public void getTicket() {
        assert ticketDao.getTicket(1L).getRequester().getId() == 1L;
    }

    @Test
    public void getTicketsByUser() {
    	
    	// Create a user for querying.
    	User user = new User();
    	user.setId(2L);
    	
    	// Query for the tickets.
		List<Ticket> tickets = ticketDao.getTicketsByUser(user);
		
		// There should be at least 2 tickets requested by user 2, which were added by the sql create script.
		if (tickets.size() < 2) {
			assert false;
		}
				
		// Check if the users's ID in each of the tickets match that of the queried user.
		for (Ticket ticket : tickets) {
			if (ticket.getRequester() == null || ticket.getRequester().getId() != user.getId()) {
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
        ticket.setCurrentPriority(Priority.NA);
        ticket.setCurrentProgress(Progress.ONHOLD);
        ticket.setRequester(user);
        
        ticket = ticketDao.saveTicket(ticket);
        
        assert ticket.getId() != null;
    }
}
