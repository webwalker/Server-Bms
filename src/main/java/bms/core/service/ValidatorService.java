package bms.core.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author xu.jian
 * 
 */
@Service
public class ValidatorService {

	static Validator validator;

	public ValidatorService() {
		// Initialize JSR-303 bean validation
		ValidatorFactory validatorFactory = Validation
				.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	public void validate(Object data, BindingResult result) {
		Set<ConstraintViolation<Object>> violations = validator.validate(data);
		// Loop over possible errors
		for (ConstraintViolation<Object> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			result.addError(new FieldError("", propertyPath, "Invalid "
					+ propertyPath + "(" + message + ")"));
		}
	}
}
