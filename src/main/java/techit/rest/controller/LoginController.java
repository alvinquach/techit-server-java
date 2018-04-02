package techit.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import techit.authentication.Token;
import techit.model.User;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.POST)
	public String login(@RequestBody Map<String, Object> userObj) {

		Object username = userObj.get("username");
		Object password = userObj.get("password");

		if (username != null && username instanceof String && password != null && password instanceof String) {
			User user = userDao.getUserByUsername((String) username);
			if (user != null && passwordEncoder.matches((String) password, user.getHash())) {
				Map<String, Object> claims = new HashMap<>();
				claims.put("username", username);

				JwtBuilder jwt = Jwts.builder().setSubject((String) username).setClaims(claims)
						.signWith(SignatureAlgorithm.HS256, Token.JWT_SECRET);
				return Token.JWT_PREFIX + jwt.compact();
			}
		}
		throw new RestException(401, "You are not Authorized.");
	}

}