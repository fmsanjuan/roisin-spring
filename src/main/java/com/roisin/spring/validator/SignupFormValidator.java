package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.forms.SignupForm;

public class SignupFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		SignupForm form = (SignupForm) target;

		if (StringUtils.isBlank(form.getName())) {
			errors.rejectValue("name", "form.name", "Name cannot be empty");
		}

		if (StringUtils.isBlank(form.getEmail())) {
			errors.rejectValue("email", "form.email", "Email cannot be empty");
		}

		if (StringUtils.isBlank(form.getCity())) {
			errors.rejectValue("city", "form.city", "City cannot be empty");
		}

		if (StringUtils.isBlank(form.getNationality())) {
			errors.rejectValue("nationality", "form.nationality", "Nationality cannot be empty");
		}

		if (StringUtils.isBlank(form.getPassword())) {
			errors.rejectValue("password", "form.password", "Password cannot be empty");
		}

		if (form.getPassword().length() < 5) {
			errors.rejectValue("password", "form.password",
					"Password must have at least 5 characters");
		}

		if (!form.getPassword().equals(form.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "form.repeatPassword", "Passwords are different");
		}

		EmailValidator eValidator = EmailValidator.getInstance(false);

		if (!eValidator.isValid(form.getEmail())) {
			errors.rejectValue("email", "form.email", "Email address is not valid");
		}

	}
}
