package techit.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.authentication.TokenAuthenticationService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@RequestMapping(method = RequestMethod.POST)
	public String login(String username, String password) {
		return tokenAuthenticationService.generateToken(username, password);
	}

}