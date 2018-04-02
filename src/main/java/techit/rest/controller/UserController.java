package techit.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.authentication.AllowedUserPositions;
import techit.authentication.TokenAuthenticationService;
import techit.model.Position;
import techit.model.Ticket;
import techit.model.User;
import techit.model.dao.TicketDao;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@AllowedUserPositions(Position.SYS_ADMIN)
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User addUser( @RequestBody User user ) {
		
		// TODO Check if all the non-nullable fields are filled.
		//		if (...) {
		//			throw new RestException(400, "Missing required data.");
		//		}

		user.setHash(passwordEncoder.encode(user.getPassword()));
		return userDao.saveUser(user);
	}

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User getUser(HttpServletRequest request, @PathVariable Long userId) {
    	User requester = tokenAuthenticationService.getUserFromRequest(request);
    	if (requester != null && (requester.getPosition() == Position.SYS_ADMIN || requester.getId().equals(userId))) {
    		User result = userDao.getUser(userId);
    		if (result != null) {
    			return result;
    		}
    		throw new RestException(404, "User does not exist");
    	}
    	throw new RestException(403, "You do not have access this user");
    }
    

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable Long userId, @RequestBody User update) {
		User user = userDao.getUser(userId);
		if (user == null) {
			throw new RestException(500, "Cannot find user ID " + userId);
		}
		if (!(update.getDepartment() == null))
			user.setDepartment(update.getDepartment());
		if (!(update.getFirstName() == null))
			user.setFirstName(update.getFirstName());
		if (!(update.getLastName() == null))
			user.setLastName(update.getLastName());
		if (!(update.getPosition() == null))
			user.setPosition(update.getPosition());
		if (!(update.getEmail() == null))
			user.setEmail(update.getEmail());
		if (!(update.getPhoneNumber() == null))
			user.setPhoneNumber(update.getPhoneNumber());
		if (!(update.getUnit() == null))
			user.setUnit(update.getUnit());
		return userDao.saveUser(user);
	}

	@RequestMapping(value = "/users/{userId}/tickets", method = RequestMethod.GET)
	public List<Ticket> getTickets(@PathVariable Long userId) {

		return ticketDao.getTicketsByRequestor(userDao.getUser(userId));
	}

	@RequestMapping(value = "/technicians/{userId}/tickets", method = RequestMethod.GET)
	public List<Ticket> getTechnicianTickets(@PathVariable Long userId) {

		return ticketDao.getTechnicianTickets(userDao.getUser(userId));
	}
}
