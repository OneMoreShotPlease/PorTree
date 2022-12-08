package controller;

import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import authentication.JwtManager;
import portfolio.RequestValidator;
import portfolio.Portfolio;
import portfolio.PortfolioDao;
import portfolio.PortfolioRegisterService;
import portfolio.PortfolioRequest;

@RestController
@RequestMapping("/api/portfolio/auth/")
public class RestPortfolioAuthController {
	private PortfolioDao portfolioDao;
	private PortfolioRegisterService registerService;
	
	public void setPortfolioDao(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}
	public void setRegisterService(PortfolioRegisterService registerSerivce) {
		this.registerService = registerSerivce;
	}
	
	// 포트폴리오 작성
	@PostMapping()
	@ApiOperation(value = "포트폴리오 작성")
	public ResponseEntity<Object> newPortfolio(
			@RequestBody PortfolioRequest req, Errors errors) {
		new RequestValidator().validate(req, errors);
		if (errors.hasErrors()) {
			String errorCodes = errors.getAllErrors()
					.stream()
					.map(error -> error.getCodes()[0])
					.collect(Collectors.joining("."));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));		
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long user_id;
		try {
			user_id = (Long) authentication.getPrincipal();
		} catch (NullPointerException e) {
			String errorCodes = "Token Expired";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		Long newPortfolio_id = registerService.regist(user_id, req);
		URI uri = URI.create("/api/portfolio/all/" + newPortfolio_id);
		return ResponseEntity.created(uri).build();
	}	
	
	// 특정 포트폴리오 수정
	@PostMapping("/{portfolio_id}")
	@ApiOperation(value = "특정 포트폴리오 수정")
	public ResponseEntity<Object> updatePortfolio(@PathVariable Long portfolio_id, @RequestBody PortfolioRequest req, Errors errors) {
		new RequestValidator().validate(req, errors);
		if (errors.hasErrors()) {
			String errorCodes = errors.getAllErrors()
					.stream()
					.map(error -> error.getCodes()[0])
					.collect(Collectors.joining("."));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));		
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long user_id;
		try {
			user_id = (Long) authentication.getPrincipal();
		} catch (NullPointerException e) {
			String errorCodes = "Token Expired";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		
		Portfolio portfolio = portfolioDao.selectById(portfolio_id);
		if (portfolio == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no portfolio"));
		}
		
		Long portfolio_user_id = portfolio.getUser_id();
		if (portfolio_user_id != user_id) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("not proper user"));
		}
		
 		Long updatePortfolio_id = registerService.update(user_id, portfolio_id, req);
		URI uri = URI.create("/api/portfolio/all/" + updatePortfolio_id);
		return ResponseEntity.created(uri).build();
	}
	
	// 특정 포트폴리오 삭제
	@DeleteMapping("/{portfolio_id}")
	@ApiOperation(value = "특정 포트폴리오 삭제")
	public ResponseEntity<Object> deletePortfolio(@PathVariable Long portfolio_id) {
		Portfolio portfolio = portfolioDao.selectById(portfolio_id);
		if (portfolio == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no portfolio"));
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long user_id;
		try {
			user_id = (Long) authentication.getPrincipal();
		} catch (NullPointerException e) {
			String errorCodes = "Token Expired";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		
		Long portfolio_user_id = portfolio.getUser_id();
		if (portfolio_user_id != user_id) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("not proper user"));
		}
		
		registerService.delete(portfolio_id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
	}
}
