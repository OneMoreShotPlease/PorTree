package portfolio;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RequestValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return PortfolioRequest.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
	}
}
