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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import techit.authentication.TokenAuthenticationService;

@Test(groups = "GetUserTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GetUserTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;

	@Configuration
	@EnableWebMvc
	static class AppConfig implements WebMvcConfigurer {
		
		// This test does not require the AuthenticationHandlerInterceptor.
		
	}

	@BeforeClass
	private void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testOk() throws Exception {

		String username = "amgarcia";
		String jwt = tokenAuthenticationService.generateToken(username, "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/users/{userId}", 2)
				.header("Authorization", jwt);

		String res = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Map<String, Object> user = objectMapper.readValue(res, new TypeReference<Map<String, Object>>() {});
		assert user.get("username").equals(username);
			
	}

	@Test
	public void testForbidden() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("amgarcia", "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/users/{userId}", 1)
				.header("Authorization", jwt);

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());

	}
	
	@Test
	public void testNotFound() throws Exception {

		String jwt = tokenAuthenticationService.generateToken("techit", "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/users/{userId}", 999)
				.header("Authorization", jwt);

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

}