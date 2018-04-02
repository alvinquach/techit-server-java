package techit.rest.controller;

import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import techit.authentication.Token;
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

	@RequestMapping(value = "/users/", method = RequestMethod.GET)
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User addUser(@RequestBody User user, @RequestHeader("Authorization") String jwt) {
		jwt = jwt.replace(Token.JWT_PREFIX, "");
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Token.JWT_SECRET))
				.parseClaimsJws(jwt).getBody();
		User requester = userDao.getUserByUsername((String) claims.get("username"));

		if (!requester.getPosition().toString().equals("ADMIN"))
			throw new RestException(403, "You are not Authorized to do this task");

		return userDao.saveUser(user);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable Long id, @RequestHeader("Authorization") String jwt) {
		jwt = jwt.replace(Token.JWT_PREFIX, "");
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Token.JWT_SECRET))
				.parseClaimsJws(jwt).getBody();

		User user = userDao.getUserByUsername((String) claims.get("username"));
		String requesterUsername = user.getUsername();
		String requesterType = user.getPosition().toString();

		User queryResult;

		queryResult = userDao.getUser(id);
		if (queryResult == null)
			throw new RestException(404, "User not found!");

		if (requesterType.equals("ADMIN")) {

			return queryResult;

		} else if (queryResult.getUsername().equalsIgnoreCase(requesterUsername)) {
			return queryResult;
		} else
			throw new RestException(403, "You are not Authorized to view this user");

	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable Long id, @RequestBody User update) {
		User user = userDao.getUser(id);
		if (user == null) {
			throw new RestException(500, "Cannot find user ID " + id);
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
