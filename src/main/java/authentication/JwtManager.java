package authentication;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

	private final String securityKey = "Hyunwoo Babo";
	private final Long expiredTime = 1000 * 60L * 60L * 3L; // 3시간
	
	// generate Token -> Login-in
	public String generateJwtToken(User user) {
		Date now = new Date();
		return Jwts.builder()
				.setSubject(user.getName())
				.setHeader(createHeader())
				.setClaims(createClaims(user))
				.setExpiration(new Date(now.getTime() + expiredTime))
				.signWith(SignatureAlgorithm.HS256, securityKey)
				.compact();
	}
	
	private Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();
		
		header.put("type",  "JWT");
		header.put("algorithm", "HS256");
		header.put("regDate", System.currentTimeMillis());
		
		return header;
	}
	
	private Map<String, Object> createClaims(User user) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("id", user.getId());
		claims.put("email", user.getEmail());
		claims.put("name", user.getName());
		
		return claims;
	}
	
	// use Token (get Information)
	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody();
	}
	
	private String getIdFromToken(String token) {
		return (String) getClaims(token).get("id");
	}
	
	private String getUsernameFromToken(String token) {
		return (String) getClaims(token).get("name");
	}
	
	private String getEmailFromToken(String token) {
		return (String) getClaims(token).get("email");
	}
}

