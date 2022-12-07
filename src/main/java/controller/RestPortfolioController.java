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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/portfolio/all/")
public class RestPortfolioController {
	private PortfolioDao portfolioDao;

	public void setPortfolioDao(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}

	// 포트폴리오 목록 보기
	@GetMapping()
	@ApiOperation(value = "전체 포트폴리오 조회")
	public List<Portfolio> Allportfolio() {
		return portfolioDao.selectAll();
	}
	
	// 특정 포트폴리오 목록 보기
	@GetMapping("/{portfolio_id}")
	@ApiOperation(value = "특정 포트폴리오 조회")
	public ResponseEntity<Object> portfolio(@PathVariable Long portfolio_id) {
		Portfolio portfolio = portfolioDao.selectById(portfolio_id);
		if (portfolio == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("no portfolio"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(portfolio);
	}
	
	// 포트폴리오 검색하기
	@GetMapping("/search")
	@ApiOperation(value ="포트폴리오 검색")
	public List<Portfolio> portfolioSearch(@RequestParam(value="query") String query) {
		return portfolioDao.selectBySearch(query);
	}
}
