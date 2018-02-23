package techit.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import techit.model.User;

@Test(groups = "UserDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Test
    public void getUser()
    {
        assert userDao.getUser( 5L ).getUsername().equalsIgnoreCase( "admin" );
    }

    @Test
    public void getUsers()
    {
        assert userDao.getUsers().size() >= 2;
    }

    @Test
    public void saveUser()
    {
        User user = new User();
        user.setUsername( "Tom" );
        user.setPassword( "abcd" );
        user.setFirstName("Tom");
        user.setLastName("Sawyer");
        user = userDao.saveUser( user );

        assert user.getId() != null;
    }

}
