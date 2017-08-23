package bms.core.test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import bms.core.controller.UserController;
import bms.core.model.User;

/**
 * @author xu.jian
 * 
 */
public class testUser extends BaseTestCase {

	@Autowired(required = false)
	private UserController controller;

	@Test
	public void test() {

		System.out.println("-------------------");

		User user = new User();

		Validator validator;
		// Initialize JSR-303 bean validation
		ValidatorFactory validatorFactory = Validation
				.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		// Get JSR-303 errors ( See domain Member class for annotations
		// validation)
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		for (ConstraintViolation<User> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			System.out.println(message);
		}
	}
}
