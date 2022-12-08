package controller;

import java.net.URI;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comment.RequestValidator;
import comment.Comment;
import comment.CommentDao;
import comment.CommentRegisterService;
import comment.CommentRequest;
import portfolio.Portfolio;
import portfolio.PortfolioDao;

@RestController
@RequestMapping("/api/comment/")
public class RestCommentController {
	private CommentDao commentDao;
	private CommentRegisterService registerService;
	private PortfolioDao portfolioDao;
	
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	public void setRegisterService(CommentRegisterService registerService) {
		this.registerService = registerService;
	}
	public void setPortfolioDao(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}
	
	// 댓글 작성
	@PostMapping("/auth")
	@ApiOperation(value = "댓글 작성")
	public ResponseEntity<Object> newComment(
			@RequestBody CommentRequest req, Errors errors) {
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
		Portfolio portfolio = portfolioDao.selectById(req.getPortfolio_id());
		if (portfolio == null) {
			String errorCodes = "Portfolio is not existed";
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		Long comment_id = registerService.regist(req.getPortfolio_id(), user_id, req.getContents());
		URI uri = URI.create("/api/comment/all/" + comment_id);
		return ResponseEntity.created(uri).build();
	}
	
	// 댓글 보기
	@PostMapping("/auth/{comment_id}")
	@ApiOperation(value = "댓글 수정")
	public ResponseEntity<Object> updateComment(@PathVariable Long comment_id, @RequestBody HashMap<String, String> contents) {
		if (contents.get("contents") == null || contents.size() > 1) {
			String errorCodes = "no proper request body";
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
		Comment comment = commentDao.selectById(comment_id);
		if (comment == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no comment"));
		}
		
		Long comment_user_id = comment.getUser_id();
		if (comment_user_id != user_id) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("not proper user"));
		}
		
		Long update_comment_id = registerService.update(comment_id, comment.getPortfolio_id(), user_id, contents.get("contents"));
		URI uri = URI.create("/api/comment/all/" + update_comment_id);
		return ResponseEntity.created(uri).build();
	}
	
	// 댓글 삭제
	@DeleteMapping("/auth/{comment_id}")
	@ApiOperation(value = "댓글 삭제")
	public ResponseEntity<Object> deleteComment(@PathVariable Long comment_id) {
		Comment comment = commentDao.selectById(comment_id);
		if (comment == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no comment"));
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long user_id;
		try {
			user_id = (Long) authentication.getPrincipal();
		} catch (NullPointerException e) {
			String errorCodes = "Token Expired";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		
		Long comment_user_id = comment.getUser_id();
		if (comment_user_id != user_id) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("not proper user"));
		}
		
		registerService.delete(comment_id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
	}
	
	// 특정 portfolio의 댓글 조회
	@GetMapping("/all/portfolio/{portfolio_id}")
	@ApiOperation(value = "특정 portfolio의 댓글 목록 조회")
	public ResponseEntity<Object> commentByPortfolio(@PathVariable Long portfolio_id) {
		Portfolio portfolio = portfolioDao.selectById(portfolio_id);
		if (portfolio == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no portfolio"));
		}
		List<Comment> comment = commentDao.selectByPortfolio(portfolio_id);
		return ResponseEntity.status(HttpStatus.OK).body(comment);
	}
	
	// 특정 댓글 보기
	@GetMapping("/all/{comment_id}")
	@ApiOperation(value = "특정 댓글 조회")
	public ResponseEntity<Object> comment(@PathVariable Long comment_id) {
		Comment comment = commentDao.selectById(comment_id);
		if (comment == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no comment"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(comment);
	}
}
