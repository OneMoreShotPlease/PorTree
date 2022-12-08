package comment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RequestValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return CommentRequest.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "portfolio_id", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contents", "required");
	}
}
