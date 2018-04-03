package techit.rest.controller;


import java.util.Date;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.authentication.AllowedUserPositions;
import techit.authentication.TokenAuthenticationService;
import techit.model.Position;
import techit.model.Priority;
import techit.model.Status;
import techit.model.Ticket;
import techit.model.Update;
import techit.model.User;
import techit.model.dao.TicketDao;
import techit.model.dao.UserDao;
import techit.rest.error.EntityDoesNotExistException;
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

	
	/** Create a new ticket. */
	@RequestMapping(method = RequestMethod.POST)
	public Ticket createTicket(HttpServletRequest request, @RequestBody Ticket ticket) {

		if (ticket.getPriority() == null || StringUtils.isNullOrEmpty(ticket.getSubject())) {
			throw new MissingFieldsException(ticket);
		}

		ticket.setCreatedBy(tokenAuthenticationService.getUserFromRequest(request));
		ticket.setCreatedDate(new Date());

		return ticketDao.saveTicket(ticket);
	}

	
	/** Get all tickets. */
	@AllowedUserPositions({Position.SYS_ADMIN, Position.SUPERVISING_TECHNICIAN})
	@RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
	public Ticket getTicket(@PathVariable Long ticketId) {
		return ticketDao.getTicket(ticketId);
	}

	
	/** 
	 * Edit the ticket with the id.
	 * Excludes fields that have their own API and fields that
	 * should not be changed (ie. createdBy and createdDate).
	 */
	@RequestMapping(value = "/{ticketId}", method = RequestMethod.PUT)
	public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
		
		// TODO Limit the users that can edit a ticket.
		
		Ticket target = ticketDao.getTicket(id);
		
		if (target == null) {
			throw new EntityDoesNotExistException(Ticket.class);
		}
		
		// Copy fields over to the target ticket object.
		// TODO Do we need technicians, status, and priority?
		target.setStartDate(ticket.getStartDate());
		target.setEndDate(ticket.getEndDate());
		target.setSubject(ticket.getSubject());
		target.setDetails(ticket.getDetails());
		target.setLocation(ticket.getLocation());
		target.setLastUpdated(ticket.getLastUpdated());
		target.setPriority(ticket.getPriority());
		target.setLocation(ticket.getLocation());
		target.setCompletionDetails(ticket.getCompletionDetails());
		target.setUnit(ticket.getUnit()); // TODO We need to reinforce assigned technicians when the assigned unit changes.
		
		// Update the last updated field.
		target.setLastUpdated(new Date());
		
		return ticketDao.saveTicket(target);
	}


	/** Get the technicians assigned to a ticket. */
	@RequestMapping(value = "/{ticketId}/technicians", method = RequestMethod.GET)
	public List<User> getTicketTechnicians(@PathVariable Long ticketId) {
		Ticket ticket = ticketDao.getTicket(ticketId);
		if (ticket == null) {
			throw new EntityDoesNotExistException(Ticket.class);
		}
		
		// TODO Check if this gives an error due to lazy loading.
		// If it does, then we may need a new DAO method just for getting technicians.
		return ticket.getTechnicians();
	}

	
	/** Assign a technician to a ticket. */
	@RequestMapping(value = "/{ticketId}/technicians/{userId}" , method=RequestMethod.PUT)
	public void addTechnicianToTicket(@PathVariable Long ticketId, @PathVariable Long userId) {
		Ticket ticket = ticketDao.getTicket(ticketId);
		
		// TODO Check if this gives an error due to lazy loading.
		// If it does, then we may need a new DAO method just for getting technicians.
		List <User> user = ticket.getTechnicians();
		
		// TODO Make sure the technician belongs to the Unit before adding.
		user.add(userDao.getUser(userId));
		ticket.setTechnicians(user);
	}


	/** 
	 * Set the status of a ticket.
	 * Some status changes require a message explaining the reason of the change - 
	 * this message should be included in the response body.
	 * Each status change automatically adds an Update to the ticket.
	 */
	@RequestMapping(value = "/{ticketId}/status/{status}" , method=RequestMethod.PUT)
	public void setTicketStatus(HttpServletRequest request, @PathVariable Long ticketId, @PathVariable Status status, @RequestBody String description) {

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

	
	/** Set the priority of a ticket. */
	@RequestMapping(value = "/{ticketId}/priority/{priority}" , method=RequestMethod.PUT)
	public void setTicketPriority(@PathVariable Long ticketId, @PathVariable Priority priority) {
		Ticket ticket = ticketDao.getTicket(ticketId);
		ticket.setPriority(priority);
		ticketDao.saveTicket(ticket);

	}


	/** Add an Update to a ticket. */
	@RequestMapping(value = "/{ticketId}/update" , method=RequestMethod.POST)
	public void addUpdateToTicket(HttpServletRequest request, @PathVariable Long ticketId, @RequestBody Update update) {

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

