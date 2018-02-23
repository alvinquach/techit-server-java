package techit.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import techit.model.Unit;
import techit.model.User;

@Test(groups = "UserDaoTest")
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
		user.setEnabled(true);
		user.setFirstName("Ay Ess");
		user.setLastName("Dee Eff");

		user = userDao.saveUser(user);

		assert user.getId() != null;
	}
	
	@Test
	public void getUsersByUnit() {
		Unit unit = new Unit();
		unit.setId(1L);
		assert userDao.getUsersByUnit(unit).size() >= 2;
	}
	
}
