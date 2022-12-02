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
	private final Long Access_Token_Duration = 1000 * 60L * 60L * 1L; // 1시간
	private final Long Refresh_Token_Duration = 1000 * 60L * 60L * 1L * 24L * 14L; // 2주
	
	// generate Token -> Login-in
	public String generateJwtToken(User user, boolean isAccess) {
		Date now = new Date();
		Long duration = 0L;
		if (isAccess) duration = Access_Token_Duration;
		else duration = Refresh_Token_Duration;
		
		return Jwts.builder()
				.setSubject(user.getName())
				.setHeader(createHeader())
				.setClaims(createClaims(user))
				.setExpiration(new Date(now.getTime() + duration))
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
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody();
	}
	
	public Long getIdFromToken(String token) {
		return ((Number) getClaims(token).get("id")).longValue();
	}
	
	public String getUsernameFromToken(String token) {
		return (String) getClaims(token).get("name");
	}
	
	public String getEmailFromToken(String token) {
		return (String) getClaims(token).get("email");
	}
}

