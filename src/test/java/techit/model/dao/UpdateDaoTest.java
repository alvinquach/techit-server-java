package techit.model.dao;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import techit.model.Priority;
import techit.model.Progress;
import techit.model.Update;
import techit.model.User;
import techit.model.Ticket;
import techit.model.dao.UpdateDao;
import java.util.Date;  
//import java.sql.Date;


@Test(groups = "UpdateDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UpdateDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UpdateDao updateDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TicketDao ticketDao;
   
	@Test
	public void getUpdate() {
		assert updateDao.getUpdate(1L).getId().toString().equals("1");
	}

//	@Test
//    public void saveUpdate(){
//	  	Ticket ticket = new Ticket();
//		User ticketUser = new User();
//		Update update = new Update();
//		
//	  	ticket = ticketDao.getTicket( (long) 1 );
//		String string_date = "2019-11-11";
//	  	update.setModifiedDate(java.sql.Date.valueOf(string_date));
//	  	update.setUpdateDetails("Fixed Persistence and Hibernate...");
//	  	ticketUser = ticket.getRequester();
//	  
//
//		update.setModifiedBy(ticketUser);
//	  	update = updateDao.saveUpdate(update);
//		assert update.getId() != null;
//	  } 
//	
}
