package techit.model.dao;

import java.util.List;

import techit.model.Unit;
import techit.model.User;

public interface UserDao {

    User getUser(Long id);
    
    User getUserByUsername(String username);

    List<User> getUsers();
    
    List<User> getUsersByUnit(Unit unit);

    User saveUser(User user);

}
