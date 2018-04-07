package techit.rest.controller.user;

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

import techit.authentication.TokenAuthenticationService;

@Test(groups = "GetTicketsByTechnicianTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GetTicketsByTechnicianTest extends AbstractTransactionalTestNGSpringContextTests{

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	private MockMvc mockMvc;

	@BeforeClass
	private void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testOk() throws Exception{
		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/technicians/{userId}/tickets",4L)
				.header("Authorization", jwt);

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testForbidden() throws Exception{
		String jwt = tokenAuthenticationService.generateToken("jdoe", "abcd");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/technicians/{userId}/tickets",4L)
				.header("Authorization", jwt);

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}
