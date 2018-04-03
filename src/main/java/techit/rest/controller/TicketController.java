package techit.rest.controller;


import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.authentication.TokenAuthenticationService;
import techit.model.Priority;
import techit.model.Status;
import techit.model.Ticket;
import techit.model.Update;
import techit.model.User;
import techit.model.dao.TicketDao;
import techit.model.dao.UserDao;
import techit.rest.error.MissingFieldsException;
import techit.rest.error.RestException;
import techit.util.StringUtils;

@RestController
@RequestMapping("/tickets")
public class TicketController {


	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Ticket> getTickets() {
		return ticketDao.getTickes();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Ticket createTicket(HttpServletRequest request, @RequestBody Ticket ticket) {

		if (ticket.getPriority() == null || StringUtils.isNullOrEmpty(ticket.getSubject())) {
			throw new MissingFieldsException(ticket);
		}

		ticket.setCreatedBy(tokenAuthenticationService.getUserFromRequest(request));

		return ticketDao.saveTicket(ticket);
	}

	@RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
	public Ticket getTicket(@PathVariable Long ticketId) {
		return ticketDao.getTicket(ticketId);
	}


	@RequestMapping(value = "/{ticketId}", method = RequestMethod.PUT)
	public Ticket updateTicket( @PathVariable Long id, @RequestBody Ticket update)
	{
		Ticket ticket = ticketDao.getTicket(id);
		if (ticket == null) {
			throw new RestException(500, "Cannot find Ticket ID " + id);
		}
		ticket.setDetails(update.getDetails());
		ticket.setLastUpdated(update.getLastUpdated());
		ticket.setPriority(update.getPriority());
		ticket.setLocation(update.getLocation());
		return ticketDao.saveTicket(ticket);
	}



	@RequestMapping(value = "/{ticketId}/technicians", method = RequestMethod.GET)
	public List<User> getTechnicians( @PathVariable Long ticketId )
	{
		Ticket ticket = ticketDao.getTicket( ticketId );
		List<User> technicians = ticket.getTechnicians();

		return technicians;
	}


	@RequestMapping(value = "/{ticketId}/technicians/{userId}" , method=RequestMethod.PUT)
	public void putTicketTechnicians( @PathVariable Long ticketId,@PathVariable Long userId) {
		Ticket ticket = ticketDao.getTicket(ticketId);
		List <User> user= ticket.getTechnicians();
		user.add(userDao.getUser(userId));
		ticket.setTechnicians(user);
	}


	@RequestMapping(value = "/{ticketId}/status/{status}" , method=RequestMethod.PUT)
	public void putTicketStatus(HttpServletRequest request, @PathVariable Long ticketId, @PathVariable Status status, @RequestBody String description) {

		User user = tokenAuthenticationService.getUserFromRequest(request);

		Ticket ticket = ticketDao.getTicket(ticketId);
		if (ticket == null) {
			throw new RestException(500, "Cannot find Ticket ID " + ticketId);
		}


		ticket.setStatus(status);

		List<Update> updates = ticket.getUpdates();
		Update update= new Update(); 
		update.setUpdateDetails(description);
		update.setTicket(ticket);
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		java.sql.Date date = new java.sql.Date(currentDate.getTime());
		update.setModifiedDate(date);
		update.setModifiedBy(user);
		updates.add(update);
		ticket.setUpdates(updates);

		ticketDao.saveTicket(ticket);

	}

	@RequestMapping(value = "/{ticketId}/priority/{priority}" , method=RequestMethod.PUT)
	public void putTicketPriority( @PathVariable Long ticketId,@PathVariable Priority priority ) {
		Ticket ticket = ticketDao.getTicket(ticketId);
		ticket.setPriority(priority);
		ticketDao.saveTicket(ticket);

	}


	@RequestMapping(value = "/{ticketId}/update" , method=RequestMethod.POST)
	public void postUpdatesToTicket(HttpServletRequest request, @PathVariable Long ticketId, @RequestBody Update update) {

		User user = tokenAuthenticationService.getUserFromRequest(request);

		Ticket ticket = ticketDao.getTicket(ticketId);
		if (ticket == null) {
			throw new RestException(500, "Cannot find Ticket ID " + ticketId);
		}

		List<Update> updates = ticket.getUpdates();
		updates.add(update);
		ticket.setUpdates(updates);
		ticketDao.saveTicket(ticket);

	}



}

