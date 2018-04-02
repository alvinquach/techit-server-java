package techit.rest.controller;


import java.sql.Date;
import java.util.Calendar;
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
import techit.model.Priority;
import techit.model.Progress;
import techit.model.Ticket;
import techit.model.Update;
import techit.model.User;
import techit.model.dao.TicketDao;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

@RestController
public class ticketController {
   
   
    @Autowired
    private TicketDao ticketDao;
    
	@Autowired
	private UserDao userDao;

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public List<Ticket> getTickets()
    {
        return ticketDao.getTickes();
    }
    
	@RequestMapping(value = "/tickets", method = RequestMethod.POST)
	public Ticket saveTicket(@RequestBody Ticket ticket, @RequestHeader("Authorization") String jwt) {

		jwt = jwt.replace(Token.JWT_PREFIX, "");
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Token.JWT_SECRET))
				.parseClaimsJws(jwt).getBody();

		User user = userDao.getUserByUsername((String) claims.get("username"));

		ticket.setRequestor(user);

		return ticketDao.saveTicket(ticket);

	}
	
    @RequestMapping(value = "/tickets/{ticketId}", method = RequestMethod.GET)
    public Ticket getTicket( @PathVariable Long ticketId )
    {
        return ticketDao.getTicket(ticketId);
    }

    
    @RequestMapping(value = "/tickets/{ticketId}", method = RequestMethod.PUT)
    public Ticket updateTicket( @PathVariable Long id, @RequestBody Ticket update)
    {
        Ticket ticket = ticketDao.getTicket(id);
        if (ticket == null) {
            throw new RestException(500, "Cannot find Ticket ID " + id);
        }
        ticket.setDetails(update.getDetails());
        ticket.setLastUpdated(update.getLastUpdated());
        ticket.setCurrentPriority(update.getCurrentPriority());
        ticket.setLocation(update.getLocation());
        return ticketDao.saveTicket(ticket);
    }
    
    
    
    @RequestMapping(value = "/tickets/{ticketId}/technicians", method = RequestMethod.GET)
    public List<User> getTechnicians( @PathVariable Long ticketId )
    {
 	   Ticket ticket = ticketDao.getTicket( ticketId );
 	   List<User> technicians = ticket.getTechnicians();

 	   return technicians;
    }
    
    
    @RequestMapping(value = "/tickets/{ticketId}/technicians/{userId}" , method=RequestMethod.PUT)
     public void putTicketTechnicians( @PathVariable Long ticketId,@PathVariable Long userId) {
    	Ticket ticket = ticketDao.getTicket(ticketId);
    	List <User> user= ticket.getTechnicians();
    	user.add(userDao.getUser(userId));
    	ticket.setTechnicians(user);
    }


    @RequestMapping(value = "/tickets/{ticketId}/status/{status}" , method=RequestMethod.PUT)
    public void putTicketStatus( @PathVariable Long ticketId,@PathVariable Progress status, @RequestBody String description,@RequestHeader("Authorization") String jwt ) {
    	
    	jwt = jwt.replace(Token.JWT_PREFIX, "");
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Token.JWT_SECRET))
				.parseClaimsJws(jwt).getBody();

		User user = userDao.getUserByUsername((String) claims.get("username"));
		
		
    	Ticket ticket = ticketDao.getTicket(ticketId);
        if (ticket == null) {
            throw new RestException(500, "Cannot find Ticket ID " + ticketId);
        }
    	
    	
        ticket.setCurrentProgress(status);

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
    
    @RequestMapping(value = "/tickets/{ticketId}/priority/{priority}" , method=RequestMethod.PUT)
    public void putTicketPriority( @PathVariable Long ticketId,@PathVariable Priority priority ) {
    	Ticket ticket = ticketDao.getTicket(ticketId);
    	ticket.setCurrentPriority(priority);
    	ticketDao.saveTicket(ticket);
    	
    }
    
    
    @RequestMapping(value = "/tickets/{ticketId}/update" , method=RequestMethod.POST)
    public void postUpdatesToTicket( @PathVariable Long ticketId, @RequestBody Update update, @RequestBody String description,@RequestHeader("Authorization") String jwt) {
    	
      	jwt = jwt.replace(Token.JWT_PREFIX, "");
    		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Token.JWT_SECRET))
    				.parseClaimsJws(jwt).getBody();

    		User user = userDao.getUserByUsername((String) claims.get("username"));
    		
    		
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

