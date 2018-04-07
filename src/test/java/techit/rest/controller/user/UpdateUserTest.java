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
import techit.model.Position;
import techit.model.User;
import techit.util.StringUtils;

@Test(groups = "UpdateUserTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UpdateUserTest extends AbstractTransactionalTestNGSpringContextTests {

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
	public void testOk1() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");

		String firstname = StringUtils.random(10);
		String lastname = StringUtils.random(10);

		User user = new User();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setPosition(Position.SYS_ADMIN);

		// TODO Add more fields for testing.

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/users/{userId}", 2)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user));

		String res = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		User responseObject = objectMapper.readValue(res, User.class);

		assert responseObject.getFirstName().equals(firstname) &&
				responseObject.getLastName().equals(lastname) &&
				responseObject.getPosition() == Position.SYS_ADMIN;

	}
	
	public void testOk2() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");

		String firstname = StringUtils.random(10);
		String lastname = StringUtils.random(10);

		User user = new User();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setPosition(Position.SYS_ADMIN); // This shound not change, since our request is not sent through an admin.

		// TODO Add more fields for testing.

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/users/{userId}", 2)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user));

		String res = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		User responseObject = objectMapper.readValue(res, User.class);

		assert responseObject.getFirstName().equals(firstname) &&
				responseObject.getLastName().equals(lastname) &&
				responseObject.getPosition() != Position.SYS_ADMIN;

	}

	@Test
	public void testForbidden() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/users/{userId}", 1)
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(new User()));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());

	}


}