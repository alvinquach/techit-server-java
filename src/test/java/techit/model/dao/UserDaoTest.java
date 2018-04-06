package techit.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import techit.model.Position;
import techit.model.Unit;
import techit.model.User;
import techit.util.StringUtils;

@Test(groups = "UserDaoTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	public void getUser() {
		assert userDao.getUser(2L).getUsername().equals("amgarcia");
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
		
		String username = "some_username_that_does_not_exist_yet";
		String password = StringUtils.random(10);
		String firstname = StringUtils.random(10);
		String lastname = StringUtils.random(10);
		
		User user = new User();
		user.setUsername(username);
		user.setHash(passwordEncoder.encode(password));
		user.setFirstName(firstname);
		user.setLastName(lastname);

		user = userDao.saveUser(user);

		assert user.getId() != null &&
				user.getUsername().equals(username) &&
				passwordEncoder.matches(password, user.getHash()) &&
				user.getFirstName().equals(firstname) &&
				user.getLastName().equals(lastname);
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
		
		// There should be at least 2 Technician users in the unit Id = 1, which were added by the sql create script.
		if (users.size() < 2) {
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
