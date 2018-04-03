package techit.authentication;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import techit.model.Position;
import techit.rest.error.RestException;

public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private TokenAuthenticationService tokenAuthenticationService;
	
	public AuthenticationHandlerInterceptor(@Autowired TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		// Login endpoint does not need security check.
		if (request.getRequestURL().toString().endsWith("login")) {
			return true;
		}
		
		if (!(handler instanceof HandlerMethod)) {
			throw new RestException(500, "Internal server error.");
		}
		
		// Check if the target method only allows specific user types.
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		AllowedUserPositions allowedUserPositions = handlerMethod.getMethodAnnotation(AllowedUserPositions.class);
		Set<Position> allowedPositions = new HashSet<>();
		if (allowedUserPositions != null) {
			for (Position type : allowedUserPositions.value()) {
				allowedPositions.add(type);
			}
		}
		
		// Validate JWT.
		return tokenAuthenticationService.validateToken(request.getHeader("Authorization"), allowedPositions);
		
	}
	
}