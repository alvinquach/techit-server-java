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
import techit.model.Ticket;
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
		ticket.setPriority(Priority.HIGH);
		ticket.setSubject(StringUtils.random(10));

		// TODO Add more fields for testing.

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/tickets")
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
				responseObject.getSubject().equals(ticket.getSubject());

	}

	@Test
	public void testMissingFields() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/tickets")
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(new Ticket()));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

}