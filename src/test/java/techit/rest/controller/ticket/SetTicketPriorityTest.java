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
import techit.model.Status;
import techit.util.StringUtils;

@Test(groups = "SetTicketPriorityTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SetTicketPriorityTest extends AbstractTransactionalTestNGSpringContextTests
{

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

	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
			.put("/{ticketId}/priority/{priority}", 2L, Status.COMPLETED)
			.header("Authorization", jwt);
	
	 mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk());
			
			/*.contentType("application/json")
			.content(StringUtils.random(300));*/
	
	}
	}
