package authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import authentication.JwtManager.JwtCode;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

	private JwtManager jwtManager;
	
	public JwtFilter(JwtManager jwtManager) {
		this.jwtManager = jwtManager;
	}
	
	@Value("${origin.frontOrigin}")
	private String frontOrigin;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
			response.setStatus(HttpServletResponse.SC_OK);
		else {
			
			String servletPath = request.getServletPath();
			String[] no_auth_urls = {"/api/user/login", "/api/user/signup", "/api/portfolio/all", "/api/comment/all", "/api/like/all"};
			boolean isAccessFilter = true;
			for (String url: no_auth_urls) {
				if (servletPath.contains(url)) {
					isAccessFilter = false;
					break;
				}
			}
			
			if (isAccessFilter) {
				String token = resolveToken(request, "Authorization");
				JwtCode code =  jwtManager.isValidToken(token);
				if (token != null && code == JwtCode.ACCESS) {
					Authentication authentication = jwtManager.getAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
	
	private String resolveToken(HttpServletRequest request, String header) {
		String authorization = request.getHeader(header);
		if (authorization != null && authorization.startsWith("Bearer")) {
			return authorization.substring(7, authorization.length());
		}
		return null;
	}

}
