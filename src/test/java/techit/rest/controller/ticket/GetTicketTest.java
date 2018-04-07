package techit.rest.controller.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import techit.authentication.AllowedUserPositions;
import techit.authentication.TokenAuthenticationService;
import techit.model.Position;
import techit.model.Ticket;
import techit.model.User;

@Test(groups = "GetTicketTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GetTicketTest extends AbstractTransactionalTestNGSpringContextTests{
	
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
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/tickets/{ticketId}",2L)
				.header("Authorization", jwt);

		mockMvc.perform(builder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testAccessUserInTicket() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("peter", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/tickets/{ticketId}",4L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	
	@Test
	public void testAccessAdminInTicket() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/tickets/{ticketId}",1L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void testAccessTechnicianInTicket() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("rsanchez", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/tickets/{ticketId}",3L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	@Test
	public void testForbidden() throws Exception{
		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/tickets/{ticketId}",2)
				.header("Authorization", jwt);

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}
