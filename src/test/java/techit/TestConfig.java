package techit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import techit.authentication.AuthenticationHandlerInterceptor;
import techit.authentication.TokenAuthenticationService;

@Configuration
@EnableWebMvc
class TestConfig implements WebMvcConfigurer {
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	/** Registers the {@code AuthenticationHandlerInterceptor} as an interceptor to allow it to run inside the unit test. */
	@Override public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationHandlerInterceptor(tokenAuthenticationService)).addPathPatterns("/**");
	}

}