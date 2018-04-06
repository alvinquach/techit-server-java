package techit.rest.controller.ticket;

import java.util.List;

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
import techit.model.User;

@Test(groups = "AssignTechnicianToTicketTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AssignTechnicianToTicketTest extends AbstractTransactionalTestNGSpringContextTests {

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
		
		User testTechnician = new User(5L);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}/technicians/{userId}", 2L, testTechnician.getId())
				.header("Authorization", jwt);
		
		String res = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		List<User> responseObject = objectMapper.readValue(res, new TypeReference<List<User>>() {});
		
		assert responseObject.contains(testTechnician);
		
	}
	
	
	/** Forbidden result due to insufficient permissions. */ 
	@Test
	public void testForbidden() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("jdoe", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}/technicians/{userId}", 2L, 5L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());

	}
	
	
	/** Failure due to requestor unit mismatch. */ 
	@Test
	public void testWrongRequestorUnit() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("jcota", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}/technicians/{userId}", 2L, 5L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	
	/** Failure due to technician unit mismatch. */ 
	@Test
	public void testWrongTechnicianUnit() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}/technicians/{userId}", 3L, 5L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	
	/** Failure due to technician being already assigned. */ 
	@Test
	public void testAlreadyAssigned() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}/technicians/{userId}", 2L, 2L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	
	/** Failure due to assignee not being a technician. */ 
	@Test
	public void testNotTechnician() throws Exception {
		
		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/tickets/{ticketId}/technicians/{userId}", 2L, 7L)
				.header("Authorization", jwt);
		
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
}