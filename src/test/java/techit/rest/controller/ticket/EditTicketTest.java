package techit.rest.controller.ticket;

import org.springframework.beans.factory.annotation.Autowired;
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

import techit.authentication.TokenAuthenticationService;
import techit.model.Priority;
import techit.model.Status;
import techit.model.Ticket;
import techit.model.Unit;
import techit.util.StringUtils;


@Test(groups = "EditTicketTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EditTicketTest extends AbstractTransactionalTestNGSpringContextTests{
	
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
	public void testOk() throws Exception{
		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");
		
		Ticket ticket = new Ticket();
		ticket.setPriority(Priority.MEDIUM);
		ticket.setSubject(StringUtils.random(10));
		ticket.setDetails("Fixed projector in A309");
		ticket.setLocation("A309");
		
		
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
	public void testForbidden() throws Exception{
		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");
		
		Ticket ticket = new Ticket();
		ticket.setPriority(Priority.MEDIUM);
		ticket.setSubject(StringUtils.random(10));
		ticket.setDetails("Fixed projector in A309");
		ticket.setLocation("A309");
		ticket.setUnit(new Unit(2L));
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}", 1L)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(ticket));
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	
	@Test
	public void ticketNotFound() throws Exception {
		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");
		
		Ticket ticket = new Ticket();
		ticket.setPriority(Priority.MEDIUM);
		ticket.setSubject(StringUtils.random(10));
		ticket.setDetails("Fixed projector in A309");
		ticket.setLocation("A309");
		
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}", Long.MAX_VALUE)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(ticket));
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
