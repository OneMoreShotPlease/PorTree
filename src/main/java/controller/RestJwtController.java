package controller;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import authentication.JwtManager;
import authentication.JwtManager.JwtCode;
import authentication.User;
import authentication.UserDao;

@RestController
@RequestMapping("/api/jwt/")
public class RestJwtController {
	 private JwtManager jwtManager;
	 private UserDao userDao;
	 
	 // setter
	 public void setJwtManager(JwtManager jwtManager) {
		 this.jwtManager = jwtManager;
	 }
	 
	 public void setUserDao(UserDao userDao) {
		 this.userDao = userDao;
	 }
	 
	private String resolveToken(HttpServletRequest request, String header) {
		String authorization = request.getHeader(header);
		if (authorization != null && authorization.startsWith("Bearer")) {
			return authorization.substring(7, authorization.length());
		}
		return null;
	}
		
	 // Access Token 재발급 (Refresh Token 전달 필요)
	 @GetMapping("/access")
	 @ApiOperation(value = "Access Token 재발급")
	 public ResponseEntity<Object> accessToken(HttpServletRequest request, HttpServletResponse response) {
		// Refresh Token 만료 여부 검사
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String refresh_token = "";
		try {
			refresh_token = (String) authentication.getCredentials();
		} catch (NullPointerException e) {
			String errorCodes = "Token Expired";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		JwtCode code = jwtManager.isValidToken(refresh_token); 
		System.out.println(jwtManager.getExpiredDateFromToken(refresh_token));
		User user = null;
		// Refresh Token 유효
		if (refresh_token != null && code == JwtCode.ACCESS) {
			user = userDao.selectById(jwtManager.getIdFromToken(refresh_token));
			// DB 속 Refresh Token과 일치 여부 확인
			if (refresh_token.equals(userDao.getRefreshToken(user))) {
				Date expiration = jwtManager.getExpiredDateFromToken(refresh_token);
				Date now = new Date();
				Long gap = expiration.getTime() - now.getTime();
				System.out.println("Now: " + now);
				System.out.println(gap);
				// Refresh Token 기간이 별로 안 남았다면 (2일 미만) -> 재발급
				if (gap <= 1000L * 60 * 60 * 24 * 2) {
					String new_refresh_token = jwtManager.generateJwtToken(user, false);
					response.setHeader("refresh_token", new_refresh_token);
				}
				
				// 새로운 Access Token 발급
				String new_access_token = jwtManager.generateJwtToken(user, true);
				response.setHeader("access_token", new_access_token);
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		}
		else if (code == JwtCode.EXPIRED) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	 }
	 
	// Refresh Token 재발급 (Refresh Token 전달 필요)
	 @GetMapping("/refresh")
	 @ApiOperation(value = "Refresh Token 재발급")
	 public ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response) {
	 	String refresh_token = resolveToken(request, "refresh_token");
		JwtCode code = jwtManager.isValidToken(refresh_token); 
		User user = null;
		// Refresh Token 유효
		if (refresh_token != null && code == JwtCode.ACCESS) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}
		// Refresh Token이 만료 -> 재발급해서 넘겨줘야함
		else if (code == JwtCode.EXPIRED) {
			return ResponseEntity.status(null).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	 }

}
