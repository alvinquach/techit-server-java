package techit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.authentication.AllowedUserPositions;
import techit.authentication.TokenAuthenticationService;
import techit.model.Position;
import techit.model.Ticket;
import techit.model.Unit;
import techit.model.User;
import techit.model.dao.TicketDao;
import techit.model.dao.UnitDao;
import techit.model.dao.UserDao;
import techit.rest.error.MissingFieldsException;
import techit.util.StringUtils;

@RestController
@RequestMapping("/units")
public class UnitController {
	
	@Autowired
	private UnitDao unitDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	
	@AllowedUserPositions(Position.SYS_ADMIN)
	@RequestMapping(method = RequestMethod.GET)
	public List<Unit> getUnits(){
		return unitDao.getUnits();
	}
	
	@AllowedUserPositions(Position.SYS_ADMIN)
	@RequestMapping(method = RequestMethod.POST)
	public Unit addUnit(@RequestBody Unit unit) {
		
		if (StringUtils.isNullOrEmpty(unit.getName())) {
			throw new MissingFieldsException(unit);
		}
		
		// Set ID to null so that we don't accidentally override any existing entries.
		// Hibernate/database will automatically generate an ID for the new entry.
		unit.setId(null); 
		
		return unitDao.saveUnit(unit);
	}
	
	@AllowedUserPositions(Position.SYS_ADMIN)
	@RequestMapping(value = "/{unitId}/technicians", method = RequestMethod.GET)
	public List<User> getTechniciansByUnit(@PathVariable Long unitId){
		
		// TODO Allow supervisors to get technicians for their own units?
		// Do supervisors also count as a technician?
		
		return userDao.getTechniciansByUnit(new Unit(unitId));
	}
	
	@AllowedUserPositions(Position.SYS_ADMIN)
	@RequestMapping(value = "/{unitId}/tickets", method = RequestMethod.GET)
	public List<Ticket> getTicketsByUnit(@PathVariable Long unitId){
		
		// TODO Allow supervisors to get tickets for their own units?
		
		return ticketDao.getTicketsByUnit(new Unit(unitId));
	}
}
