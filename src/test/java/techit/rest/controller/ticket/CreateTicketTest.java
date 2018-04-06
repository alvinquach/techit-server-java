package techit.rest.controller.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;


import static org.junit.Assert.fail;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;



import techit.authentication.TokenAuthenticationService;
import techit.model.Priority;
import techit.model.Ticket;
import techit.model.Unit;
import techit.rest.controller.TicketController;
import techit.rest.error.RestException;
import techit.util.StringUtils;

@Test(groups = "CreateTicketTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CreateTicketTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeClass
	private void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testOk() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");

		Ticket ticket = new Ticket();
		ticket.setPriority(Priority.MEDIUM);
		ticket.setSubject(StringUtils.random(10));
		ticket.setDetails("IT Network problems");
		ticket.setLocation("ET320");
		Unit unit = new Unit();
		unit.setName(StringUtils.random(10));
		ticket.setUnit(unit);
		
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}", 1L)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(ticket));

		String res = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Ticket responseObject = objectMapper.readValue(res, Ticket.class);

		assert responseObject.getId() != null &&
				responseObject.getPriority() == ticket.getPriority() &&
				responseObject.getSubject().equals(ticket.getSubject()) &&
				responseObject.getDetails().equals(ticket.getDetails()) &&
				responseObject.getLocation().equals(ticket.getLocation());
	}

	@Test
	public void testMissingTicketFields() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");
		Ticket ticket = new Ticket();
		Unit unit = new Unit();
		unit.setName(StringUtils.random(10));
		ticket.setUnit(unit);
		

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/tickets")
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(new Ticket()));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	

//	We have in mind that the user must include unit within ticket. 
	@Test
	public void testMissingUnitFields() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");
		Unit unit = new Unit();
		unit = null;
		Ticket ticket = new Ticket();
		ticket.setPriority(Priority.MEDIUM);
		ticket.setSubject(StringUtils.random(10));
		ticket.setDetails("IT Network problems");
		ticket.setLocation("ET320");
		ticket.setUnit(unit);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/tickets")
				.header("Authorization", jwt)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Ticket()));		
		mockMvc.perform(builder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	

//www.springboottutorial.com/unit-testing-for-sprng-boot-rest-services

}