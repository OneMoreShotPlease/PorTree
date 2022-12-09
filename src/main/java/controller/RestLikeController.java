package controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import authentication.User;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.ApiOperation;
import authentication.SimpleUser;
import authentication.SimpleUserDao;
import like.LikeDao;
import like.LikeRegisterService;
import portfolio.Portfolio;
import portfolio.PortfolioDao;

@RestController
@RequestMapping("/api/like/")
public class RestLikeController {
	private LikeDao likeDao;
	private LikeRegisterService registerService;
	private PortfolioDao portfolioDao;
	private SimpleUserDao simpleUserDao;
	
	public void setLikeDao(LikeDao likeDao) {
		this.likeDao = likeDao;
	}
	public void setLikeRegisterService(LikeRegisterService registerService) {
		this.registerService = registerService;
	}
	public void setPortfolioDao(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}
	public void setSimpleUserDao(SimpleUserDao userDao) {
		this.simpleUserDao = userDao;
	}
	
	@GetMapping("/auth")
	@ApiOperation(value = "좋아요 등록/취소 (권한)")
	public ResponseEntity<Object> newLike(@RequestBody HashMap<String, String> portfolio) {
		if (portfolio.get("portfolio_id") == null || portfolio.size() > 1) {
			String errorCodes = "no proper request body";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));		
		}
		Portfolio liked_portfolio;
		try {
			liked_portfolio = portfolioDao.selectById(Long.parseLong(portfolio.get("portfolio_id")));
		} catch(NullPointerException e) {
			String errorCodes = "Portfolio doesn't exist";
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
		Long like_id = registerService.press(liked_portfolio.getPortfolio_id(), user_id);
		if (like_id != null)
			return ResponseEntity.status(HttpStatus.CREATED).build();
		else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	// 특정 portfolio의 좋아요 조회
	@GetMapping("/all/{portfolio_id}")
	@ApiOperation(value = "특정 portfolio의 좋아요 목록 조회")
	public ResponseEntity<Object> likeByPortfolio(@PathVariable Long portfolio_id) {
		Portfolio portfolio = portfolioDao.selectById(portfolio_id);
		if (portfolio == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no portfolio"));
		}
		List<Integer> like = likeDao.selectByPortfolio(portfolio_id);
		List<SimpleUser> user = simpleUserDao.selectByIdList(like);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
