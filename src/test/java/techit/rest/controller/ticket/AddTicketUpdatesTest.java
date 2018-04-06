package techit.rest.controller.ticket;

import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import techit.authentication.TokenAuthenticationService;
import techit.model.Ticket;
import techit.model.Update;
import techit.model.dao.TicketDao;

@Test(groups = "AddTicketUpdatesTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AddTicketUpdatesTest extends AbstractTransactionalTestNGSpringContextTests{

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	
	@Autowired
	private TicketDao ticketDao;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeClass
	private void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		objectMapper = new ObjectMapper();
	}
	
	@Test
	public void testOk() throws Exception{
		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");
		Ticket ticket = ticketDao.getTicket(1L);
		int initialUpdatesSize = ticket.getUpdates().size();
		
		Update update = new Update();
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/tickets/{ticketId}/update", 1L)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(update));

		String res = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		List<Update> responseObject = objectMapper.readValue(res, new TypeReference<List<Update>>() {});
		assert responseObject.size() == initialUpdatesSize + 1;
	}
	
	@Test
	public void testFailure() throws Exception {
		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");
		
		Update update = new Update();
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/tickets/{ticketId}/update", 4L)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(update));
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
