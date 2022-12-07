package controller;

import java.net.URI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import authentication.User;
import authentication.UserDao;
import authentication.UserRequest;
import authentication.UserRegisterService;
import authentication.UserAuth;
import authentication.UserAuthService;
import authentication.DuplicateUserException;
import authentication.JwtManager;
import authentication.WrongIdPasswordException;
import io.swagger.annotations.ApiOperation;
import authentication.RequestValidator;
import authentication.AuthValidator;

@RestController
@RequestMapping("/api/user/")
public class RestUserController {
	private UserDao userDao;
	private UserRegisterService registerService;
	private UserAuthService authService;
	private JwtManager jwtManager;
	
	// setter
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void setRegisterService(UserRegisterService registerService) {
		this.registerService = registerService;
	}
	public void setAuthService(UserAuthService authService) {
		this.authService = authService;
	}
	public void setJwtManager(JwtManager jwtManager) {
		this.jwtManager = jwtManager;
	}
	
	// sign-up
	@PostMapping("/signup")
	@ApiOperation(value="회원가입")
	public ResponseEntity<Object> newUser(
			@RequestBody UserRequest userReq, Errors errors, HttpServletResponse response) {
		new RequestValidator().validate(userReq, errors);
		// 에러 처리
		if (errors.hasErrors()) {
			String errorCodes = errors.getAllErrors()
					.stream()
					.map(error -> error.getCodes()[0])
					.collect(Collectors.joining("."));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		
		try {
			Long newUserId = registerService.regist(userReq);
			URI uri = URI.create("/api/user/" + newUserId);
			
			return ResponseEntity.created(uri).build();
		} catch(DuplicateUserException dupEx) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	// user-detail
	@GetMapping("/{user_id}")
	@ApiOperation(value = "마이페이지")
	public ResponseEntity<Object> user(@PathVariable Long user_id) {
		User user = userDao.selectById(user_id);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("No User"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	// login-in
	@PostMapping("/login")
	@ApiOperation(value = "로그인")
	public ResponseEntity<Object> loginUser(
			@RequestBody UserAuth auth, Errors errors, HttpServletResponse response) {
		new AuthValidator().validate(auth, errors);
		if (errors.hasErrors()) {
			String errorCodes = errors.getAllErrors()
					.stream()
					.map(error -> error.getCodes()[0])
					.collect(Collectors.joining("."));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		try {
			User user = authService.authenticate(auth);
			
			// JWT Access Token
			final String access_token = jwtManager.generateJwtToken(user, true);
			// JWT Refresh Token
			final String refresh_token = jwtManager.generateJwtToken(user, false);
			
			// Refresh Token DB에 저장
			userDao.updateRefreshToken(user, refresh_token);
			
			/* Cookie에 보관
			Cookie cookie = new Cookie("token", token);
			cookie.setMaxAge(60 * 60 * 24 * 30); // 30일간 유지
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
			*/
			response.setHeader("access_token", access_token);
			response.setHeader("refresh_token", refresh_token);
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} catch (WrongIdPasswordException wrongEx) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	// log-out
	@GetMapping("/logout")
	@ApiOperation(value = "로그아웃")
	public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long user_id;
		try {
			user_id = (Long) authentication.getPrincipal();
			userDao.deleteRefreshToken(user_id);
		} catch (NullPointerException e) {
			String errorCodes = "Token Expired";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("errorCodes = " + errorCodes));
		}	
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}