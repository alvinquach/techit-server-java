package techit.rest.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import techit.authentication.AuthenticationHandlerInterceptor;
import techit.authentication.TokenAuthenticationService;
import techit.model.User;

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

	@Configuration
	@EnableWebMvc
	static class AppConfig implements WebMvcConfigurer {
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
		    registry.addInterceptor(new AuthenticationHandlerInterceptor(new TokenAuthenticationService())).addPathPatterns("/**");
		}
		
	}

	@BeforeClass
	private void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testOk() throws Exception {

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("username", "techit");
		credentials.put("password", "abcd");
		String jwt = tokenAuthenticationService.generateToken(credentials);

		User user = new User(999L); // For some reason, the ID is not being uniquely generated.
		user.setUsername("asdf");
		user.setPassword("asdf");
		user.setFirstName("Ay Ess");
		user.setLastName("Dee Eff");

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

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("username", "amgarcia");
		credentials.put("password", "abcd");
		String jwt = tokenAuthenticationService.generateToken(credentials);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/users")
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(new User()));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());

	}

	@Test
	public void testMissingFields() throws Exception {

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("username", "techit");
		credentials.put("password", "abcd");
		String jwt = tokenAuthenticationService.generateToken(credentials);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/users")
				.header("Authorization", jwt)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(new User()));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

}