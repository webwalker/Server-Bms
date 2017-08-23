package bms.core.auth;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import bms.core.model.User;

@Component
public class UserValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courtName",
				"required.courtName", "Court name is required.");
		ValidationUtils.rejectIfEmpty(errors, "date", "required.date",
				"Date is required.");

		User user = (User) target;
		// errors.reject("invalid.holidayHour", "Invalid holiday hour.");
	}

	public void login() {
		
	}
}
