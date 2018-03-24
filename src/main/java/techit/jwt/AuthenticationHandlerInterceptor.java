package techit.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import techit.model.User;
import techit.rest.error.RestException;

public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {
String userName;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURL().toString().endsWith("login")) {
            return true;
        }
        String jwt = request.getHeader("Authorization");


        if (isTokenValid(jwt)) {
            return true;
        }
        throw new RestException(401, "Authorization token is missing or invalid");
    }
    
    private boolean isTokenValid(String jwt) {
        if (jwt == null) {
            return false;
        }
        if (!jwt.startsWith("Bearer")) {
            return false;
        }
        try {
 		   jwt=  jwt.replace(Token.JWT_PREFIX, "");
 		    Claims claims = Jwts.parser()         
 		    		.setSigningKey(DatatypeConverter.parseBase64Binary(Token.JWT_SECRET))
 		    		.parseClaimsJws(jwt).getBody();

 		    
 		  if (((String) claims.get("username"))==null) return false;
 		   	
 		   	
 	   }
 	   catch (SignatureException ex)
 	   {
 	        throw new RestException(401, "Authorization token is missing or invalid");
 		   
 	   }
        
	   		return true;

    }
    
}