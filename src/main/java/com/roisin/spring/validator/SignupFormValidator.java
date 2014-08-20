package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.forms.EditProfileForm;
import com.roisin.spring.forms.SignupForm;
import com.roisin.spring.services.UserService;
import com.roisin.spring.utils.HashUtils;

public class SignupFormValidator implements Validator {

	@Autowired
	UserService userService;

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

	public void validateEditForm(Object target, Errors errors) {

		EditProfileForm form = (EditProfileForm) target;

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

		if (!(StringUtils.isBlank(form.getOldPassword())
				&& StringUtils.isBlank(form.getNewPassword()) && StringUtils.isBlank(form
				.getRepeatNewPassword()))) {

			if (!HashUtils.passwordEncoder(form.getOldPassword()).equals(
					userService.findByPrincipal().getUserAccount().getPassword())) {
				errors.rejectValue("oldPassword", "form.oldPassword", "Old password is not correct");
			}

			if (StringUtils.isBlank(form.getNewPassword())) {
				errors.rejectValue("newPassword", "form.newPassword", "Password cannot be empty");
			}

			if (form.getNewPassword().length() < 5) {
				errors.rejectValue("newPassword", "form.newPassword",
						"Password must have at least 5 characters");
			}

			if (!form.getNewPassword().equals(form.getRepeatNewPassword())) {
				errors.rejectValue("repeatNewPassword", "form.repeatNewPassword",
						"Passwords are different");
			}
		}

		EmailValidator eValidator = EmailValidator.getInstance(false);

		if (!eValidator.isValid(form.getEmail())) {
			errors.rejectValue("email", "form.email", "Email address is not valid");
		}
	}
}
