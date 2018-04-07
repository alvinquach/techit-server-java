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
import techit.rest.error.EntityDoesNotExistException;
import techit.rest.error.MissingFieldsException;
import techit.rest.error.RestException;
import techit.util.StringUtils;

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

	@AllowedUserPositions(Position.SYS_ADMIN)
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@AllowedUserPositions(Position.SYS_ADMIN)
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) {

		boolean missingRequiredFields =
				StringUtils.isNullOrEmpty(user.getUsername()) ||
				StringUtils.isNullOrEmpty(user.getPassword()) ||
				StringUtils.isNullOrEmpty(user.getFirstName()) ||
				StringUtils.isNullOrEmpty(user.getLastName());

		if (missingRequiredFields) {
			throw new MissingFieldsException(user);
		}

		// Generate a hash of the user's password.
		user.setHash(passwordEncoder.encode(user.getPassword()));
		
		// Set ID to null so that we don't accidentally override any existing entries.
		// Hibernate/database will automatically generate an ID for the new entry.
		user.setId(null); 
		
		return userDao.saveUser(user);
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public User getUser(HttpServletRequest request, @PathVariable Long userId) {

		// TODO Should supervising technicians also be able to access users under their supervision?

		User requestor = tokenAuthenticationService.getUserFromRequest(request);
		if (requestor != null && (requestor.getPosition() == Position.SYS_ADMIN || requestor.getId().equals(userId))) {
			User result = userDao.getUser(userId);
			if (result != null) {
				return result;
			}
			throw new EntityDoesNotExistException(User.class);
		}
		throw new RestException(403, "You do not have access this user");
	}


	@RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable Long userId, @RequestBody User user) {

		User target = userDao.getUser(userId);

		if (target == null) {
			throw new EntityDoesNotExistException(User.class);
		}

		// Update the target user's fields.
		// TODO Add ability to change username and password?
		target.setDepartment(user.getDepartment());
		target.setFirstName(user.getFirstName());
		target.setLastName(user.getLastName());
		target.setPosition(user.getPosition());
		target.setEmail(user.getEmail());
		target.setPhoneNumber(user.getPhoneNumber());
		target.setUnit(user.getUnit());

		return userDao.saveUser(target);
	}

	@RequestMapping(value = "/users/{userId}/tickets", method = RequestMethod.GET)
	public List<Ticket> getTicketsByCreator(@PathVariable Long userId) {
		return ticketDao.getTicketsByCreator(new User(userId));
	}

	@AllowedUserPositions({Position.SYS_ADMIN, Position.SUPERVISING_TECHNICIAN, Position.TECHNICIAN})
	@RequestMapping(value = "/technicians/{userId}/tickets", method = RequestMethod.GET)
	public List<Ticket> getTicketsByTechnician(@PathVariable Long userId) {
		return ticketDao.getTicketsByTechnician(new User(userId));
	}
}
