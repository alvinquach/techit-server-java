package techit.model.dao;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import techit.model.Ticket;
import techit.model.Update;
import techit.model.User;  

@Test(groups = "UpdateDaoTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UpdateDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	UpdateDao updateDao;

	@Test
	public void getUpdate() {
		assert updateDao.getUpdate(1L).getId() == 1L;
	}

	@Test
	public void saveUpdate(){

		Ticket ticket = new Ticket();
		ticket.setId(1L);

		User ticketUser = new User();
		ticketUser.setId(1L);

		Update update = new Update();
		update.setTicket(ticket);
		update.setModifiedBy(ticketUser);
		update.setUpdateDetails("Fixed Persistence and Hibernate...");
		update.setModifiedDate(Date.valueOf("2019-11-11"));

		update = updateDao.saveUpdate(update);

		assert update.getId() != null;

	} 

}
