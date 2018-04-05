package techit.rest.controller;

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
import techit.model.User;

/** Tests the {@code login()} method in the {@code LoginController}. */
@Test(groups = "LoginTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LoginTest extends AbstractTransactionalTestNGSpringContextTests {

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
	public void testSuccess() throws Exception {

		String username = "techit";

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/login")
				.param("username", username)
				.param("password", "abcd");

		String jwt = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
	
		if (!jwt.startsWith("Bearer ")) {
			assert false;
		}
		
		User authenticatedUser = tokenAuthenticationService.getUserFromToken(jwt.substring(7));
		
		assert authenticatedUser.getUsername().equals(username);
		
	}
	
	@Test
	public void testFailure() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/login")
				.param("username", "techit")
				.param("password", "wrong password");

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	
	}

}