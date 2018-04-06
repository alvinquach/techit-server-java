package techit.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import techit.model.Position;
import techit.model.Unit;
import techit.model.User;

@Test(groups = "UserDaoTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	UserDao userDao;

	@Test
	public void getUser() {
		assert userDao.getUser(1L).getUsername().equals("amgarcia");
	}

	@Test
	public void getUserByUsername() {
		assert userDao.getUserByUsername("amgarcia") != null;
	}

	@Test
	public void getUsers() {
		assert userDao.getUsers().size() >= 2;
	}

	@Test
	public void saveUser() {
		
		User user = new User();
		user.setUsername("asdf");
		user.setPassword("asdf");
		user.setFirstName("Ay Ess");
		user.setLastName("Dee Eff");

		user = userDao.saveUser(user);

		assert user.getId() != null;
	}
	
	@Test
	public void getUsersByUnit() {
		
		// Create a unit for querying.
		Unit unit = new Unit();
		unit.setId(1L);
		
		// Query for the users.
		List<User> users = userDao.getUsersByUnit(unit);
		
		// There should be at least 3 users in the unit, which were added by the sql create script.
		if (users.size() < 4) {
			assert false;
		}
		
		// Check if the unit's ID in each of the users match that of the queried unit.
		for (User user : users) {
			if (user.getUnit() == null || user.getUnit().getId() != unit.getId()) {
				assert false;
			}
		}
		
		assert true;
	}
	
	@Test
	public void getUsersByUnitAndPosition() {
		
		// Create a unit for querying.
		Unit unit = new Unit();
		unit.setId(1L);
		
		// Create a position for querying.
		Position position = Position.SYS_ADMIN;
	
		// Query for the users.
		List<User> users = userDao.getUsersByUnitAndPosition(unit, position);

		// There is only 1 sys_admin in current database
		if (users.size() <1 ) {
			assert false;
		}
		
		// Check if the unit's ID and position in each of the users match that of the queried unit.
		for (User user : users) {
			if ((user.getUnit() == null || user.getUnit().getId() != unit.getId()) || (user.getPosition() == null 
			    || user.getPosition().getValue() != position.getValue())){
				assert false;
			}
		}
		
		assert true;
	}
	
	@Test
	public void getTechniciansByUnit() {
		
		// Create a unit for querying.
		Unit unit = new Unit();
		unit.setId(1L);
		
		// Query for the users.
		List<User> users = userDao.getTechniciansByUnit(unit);
		
		// There should be at least 3 Technician users in the unit Id =1 , which were added by the sql create script.
		if (users.size() < 3) {
			assert false;
		}
		
		// Check if the unit's ID in each of the users match that of the queried unit.
		for (User user : users) {
			if (user.getUnit() == null || user.getUnit().getId() != unit.getId()) {
				assert false;
			}
		}
		
		assert true;
	}

}
