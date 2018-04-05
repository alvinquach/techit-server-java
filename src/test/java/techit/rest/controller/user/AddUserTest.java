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

import com.fasterxml.jackson.databind.ObjectMapper;

import techit.authentication.TokenAuthenticationService;
import techit.model.User;
import techit.util.StringUtils;

@Test(groups = "AddUserTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AddUserTest extends AbstractTransactionalTestNGSpringContextTests {

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

		User user = new User();
		user.setUsername("some_username_that_does_not_exist_yet");
		user.setPassword(StringUtils.random(10));
		user.setFirstName(StringUtils.random(10));
		user.setLastName(StringUtils.random(10));

		// TODO Add more fields for testing.

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/users")
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user));

		String res = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		User responseObject = objectMapper.readValue(res, User.class);

		assert responseObject.getId() != null &&
				responseObject.getUsername().equals(user.getUsername()) &&
				responseObject.getFirstName().equals(user.getFirstName()) &&
				responseObject.getLastName().equals(user.getLastName());

	}

	@Test
	public void testForbidden() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/users")
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(new User()));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());

	}

	@Test
	public void testMissingFields() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/users")
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(new User()));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

}