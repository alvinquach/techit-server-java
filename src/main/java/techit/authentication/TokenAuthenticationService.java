package techit.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import techit.model.Position;
import techit.model.Unit;
import techit.model.User;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

@Service
public class TokenAuthenticationService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public final String jwtPrefix = "Bearer ";

	public final String jwtSecret = "secret";
	
	public boolean validateToken(String token, Set<Position> allowedPositions) {
		if (token != null && token.startsWith(jwtPrefix)) {
			token = token.substring(jwtPrefix.length());
			try {
				User user = getUserFromToken(token);
				if (user.getUsername() != null && user.getPosition() != null) {

					// If types of users allowed were specified, then check against user's type.
					if (!allowedPositions.isEmpty()) {
						Position type = user.getPosition();
						if (allowedPositions.contains(type)) {
							return true;
						}
						throw new RestException(403, "You do not have access to this API");
					}
					else {
						return true;
					}
				}
			}
			catch (ExpiredJwtException | SignatureException e) {
				// Do nothing...let it throw the exception at the end.
			}
		}
		throw new RestException(401, "Authorization token is missing or invalid");
	}

	public String generateToken(String username, String password) {
		if(username != null && password != null) {
			User user = userDao.getUserByUsername(username);
			if (user != null && passwordEncoder.matches(password, user.getHash())) {
				Map<String, Object> claims = new HashMap<>();
				claims.put("id", user.getId());
				claims.put("username", username);
				claims.put("position", user.getPosition());
				if (user.getUnit() != null) {
					claims.put("unitId", user.getUnit().getId());
				}
				String jwt = Jwts.builder()
						.setClaims(claims)
						.signWith(SignatureAlgorithm.HS512, jwtSecret)
						.compact();
				return jwtPrefix + jwt;
			}
		}
		throw new RestException(401, "Invalid username or password.");
	}

	/**
	 * Constructs a {@code User} object based on JWT data contained in the request header.
	 * Note that the returned {@code User} will only have the ID, username, and type fields populated.
	 */
	public User getUserFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith(jwtPrefix)) {
			token = token.substring(jwtPrefix.length());
			return getUserFromToken(token);
		}
		return null;
	}

	/**
	 * Constructs a {@code User} object based on JWT data and returns it.
	 * Note that the returned {@code User} will only have the ID, username, and type fields populated.
	 */
	public User getUserFromToken(String token) throws ExpiredJwtException, SignatureException {
		User user = new User();
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		user.setUsername((String)claims.get("username"));
		user.setPosition(Position.valueOf((String)claims.get("position")));
		user.setId(Long.valueOf(claims.get("id").toString()));
		if (claims.get("unitId") != null) {
			user.setUnit(new Unit(Long.valueOf(claims.get("unitId").toString())));
		}
		return user;
	}

}