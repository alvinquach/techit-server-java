package techit.model.dao;

import java.util.List;

import techit.model.Position;
import techit.model.Unit;
import techit.model.User;

public interface UserDao {

    User getUser(Long id);
    
    User getUserByUsername(String username);

    List<User> getUsers();
    
    List<User> getUsersByUnit(Unit unit);
    
    List<User> getUsersByUnitAndPosition(Unit unit, Position position);

    List<User> getTechniciansByUnit(Unit unit);
    
    List<User> getSupervisorsByUnit(Unit unit);

    User saveUser(User user);

}
