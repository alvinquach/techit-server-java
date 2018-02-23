package techit.model.dao;

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
    @Autowired
    UserDao userDao;
   

    @Test
    public void getTicket()
    {
        assert ticketDao.getTicket( 1L ).getRequester().getId().toString().equals("1");
    }

    @Test
    public void getTicketsByUser()
    {
       	
        assert ticketDao.getTicketsByUser(userDao.getUser(1L)).size()>0;
    }


    
    @Test
    public void saveTicket()
    {
    	Priority priority = null ;
    	Progress progress = null ;
    	Ticket ticket = new Ticket();
        ticket.setCurrentPriority(priority.NA);
        ticket.setCurrentProgress(progress.ONHOLD);
        ticket.setRequester(userDao.getUser(1L));
        ticket = ticketDao.saveTicket(ticket);
        assert ticket.getId() != null;
    }
}
