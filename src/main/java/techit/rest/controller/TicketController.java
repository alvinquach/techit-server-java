package techit.rest.controller;


import java.util.Date;
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
import techit.model.Unit;
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

	/** Get all tickets. */
	@AllowedUserPositions({Position.SYS_ADMIN, Position.SUPERVISING_TECHNICIAN})
	@RequestMapping(method = RequestMethod.GET)
	public List<Ticket> getTickets() {
		return ticketDao.getTickets();
	}

	
	/** Create a new ticket. */
	@RequestMapping(method = RequestMethod.POST)
	public Ticket createTicket(HttpServletRequest request, @RequestBody Ticket ticket) {

		if (ticket.getPriority() == null || StringUtils.isNullOrEmpty(ticket.getSubject())) {
			throw new MissingFieldsException(ticket);
		}

		// Auto-populate the created by and created date fields.
		ticket.setCreatedBy(tokenAuthenticationService.getUserFromRequest(request));
		ticket.setCreatedDate(new Date());

		// Set ID to null so that we don't accidentally override any existing entries.
		// Hibernate/database will automatically generate an ID for the new entry.
		ticket.setId(null);
		
		return ticketDao.saveTicket(ticket);
	}
	
	
	/** Get a ticket by id. */
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
		Ticket ticket = ticketDao.getTicketWithTechnicians(ticketId);
		if (ticket == null) {
			throw new EntityDoesNotExistException(Ticket.class);
		}
		return ticket.getTechnicians();
	}

	
	/**
	 * Assigns/removes a technician to a ticket.
	 * If the technician ID does not yet exist on the ticket, then they will be added.
	 * If the technician ID already exists on the ticket, then they will be removed.
	 * A technician can only be added if they belong to the unit that is assigned to the ticket.
	 */
	@RequestMapping(value = "/{ticketId}/technicians/{userId}" , method=RequestMethod.PUT)
	public Ticket assignTechnicianToTicket(@PathVariable Long ticketId, @PathVariable Long userId) {
		
		Ticket ticket = ticketDao.getTicketWithTechnicians(ticketId);
		if (ticket == null) {
			throw new EntityDoesNotExistException(Ticket.class);
		}
		
		// Check if the user is already assigned to the ticket.
		User technician = ticket.getTechnicians().stream()
				.filter(user -> user.getId().equals(userId))
				.findFirst()
				.orElse(null);
		
		// If they are, then remove them from the ticket and save the ticket.
		if (technician != null) {
			ticket.getTechnicians().remove(technician);
			return ticketDao.saveTicket(ticket);
		}
		
		// To add a technician, we must first check if the technician is a technician and belongs to the unit.
		Unit unit = ticket.getUnit();
		if (unit != null) {
			List<User> unitTechnicians = userDao.getTechniciansByUnit(unit);
			
			technician = unitTechnicians.stream()
					.filter(user -> user.getId().equals(userId))
					.findFirst()
					.orElse(null);
			
			if (technician == null) {
				throw new RestException(400, "User does not belong to the unit that is assigned to the ticket.");
			}
			
			if (technician.getPosition() != Position.TECHNICIAN) {
				throw new RestException(400, "User is not a technician.");
			}
			
			ticket.getTechnicians().add(technician);
			return ticketDao.saveTicket(ticket);
			
		}
		
		// TODO Is the the correct way to handle this case?
		throw new RestException(400, "The ticket is not assigned to any unit.");
		
	}

	
	/** 
	 * Set the status of a ticket.
	 * Some status changes require a message explaining the reason of the change - 
	 * this message should be included in the response body.
	 * Each status change automatically adds an Update to the ticket.
	 */
	@AllowedUserPositions({Position.SYS_ADMIN, Position.SUPERVISING_TECHNICIAN})
	@RequestMapping(value = "/{ticketId}/status/{status}" , method=RequestMethod.PUT)
	public Ticket setTicketStatus(HttpServletRequest request, @PathVariable Long ticketId, @PathVariable Status status, @RequestBody String description) {

		User requestor = tokenAuthenticationService.getUserFromRequest(request);

		Ticket ticket = ticketDao.getTicket(ticketId);
		if (ticket == null) {
			throw new EntityDoesNotExistException(Ticket.class);
		}
		
		Date current = new Date();

		// Update the ticket properties.
		ticket.setStatus(status);
		ticket.setLastUpdated(current);

		// Create a new update to document the status change and add it to the ticket.
		Update update = new Update(); 
		update.setUpdateDetails(description); // TODO Add status change to the description
		update.setTicket(ticket);
		update.setModifiedDate(current);
		update.setModifiedBy(requestor);
		ticket.getUpdates().add(update);

		return ticketDao.saveTicket(ticket);

	}

	
	/** Set the priority of a ticket. */
	@RequestMapping(value = "/{ticketId}/priority/{priority}" , method=RequestMethod.PUT)
	public Ticket setTicketPriority(@PathVariable Long ticketId, @PathVariable Priority priority) {
		
		Ticket ticket = ticketDao.getTicket(ticketId);
		if (ticket == null) {
			throw new EntityDoesNotExistException(Ticket.class);
		}
		
		ticket.setPriority(priority);
		ticket.setLastUpdated(new Date());
		
		return ticketDao.saveTicket(ticket);
		
	}


	/** Add an Update to a ticket. */
	@RequestMapping(value = "/{ticketId}/update" , method=RequestMethod.POST)
	public Ticket addUpdateToTicket(HttpServletRequest request, @PathVariable Long ticketId, @RequestBody Update update) {

		User requestor = tokenAuthenticationService.getUserFromRequest(request);

		Ticket ticket = ticketDao.getTicket(ticketId);
		if (ticket == null) {
			throw new EntityDoesNotExistException(Ticket.class);
		}
		
		Date current = new Date();

		// Update the ticket properties.
		ticket.setLastUpdated(current);

		// Set ID to null so that we don't accidentally override any existing entries.
		// Hibernate/database will automatically generate an ID for the new entry.
		update.setId(null);
		
		update.setModifiedDate(current);
		update.setModifiedBy(requestor);
		
		update.setTicket(ticket);
		ticket.getUpdates().add(update);
		
		return ticketDao.saveTicket(ticket);

	}


}
