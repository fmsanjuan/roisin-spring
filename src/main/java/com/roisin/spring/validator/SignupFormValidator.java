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
			errors.rejectValue("name", "sign.error.name.empty", "sign.error.name.empty");
		}

		if (StringUtils.isBlank(form.getEmail())) {
			errors.rejectValue("email", "sign.error.email.empty", "sign.error.email.empty");
		}

		if (StringUtils.isBlank(form.getCity())) {
			errors.rejectValue("city", "sign.error.city.empty", "sign.error.city.empty");
		}

		if (StringUtils.isBlank(form.getNationality())) {
			errors.rejectValue("nationality", "sign.error.nationality.empty",
					"sign.error.nationality.empty");
		}

		if (StringUtils.isBlank(form.getPassword())) {
			errors.rejectValue("password", "sign.error.password.empty", "sign.error.password.empty");
		}

		if (form.getPassword().length() < 5) {
			errors.rejectValue("password", "sign.error.password.characters",
					"sign.error.password.characters");
		}

		if (!form.getPassword().equals(form.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "sign.error.password.different",
					"sign.error.password.different");
		}

		EmailValidator eValidator = EmailValidator.getInstance(false);

		if (!eValidator.isValid(form.getEmail())) {
			errors.rejectValue("email", "sign.error.email.invalid", "sign.error.email.invalid");
		}

	}

	public void validateEditForm(Object target, Errors errors) {

		EditProfileForm form = (EditProfileForm) target;

		if (StringUtils.isBlank(form.getName())) {
			errors.rejectValue("name", "sign.error.name.empty", "sign.error.name.empty");
		}

		if (StringUtils.isBlank(form.getEmail())) {
			errors.rejectValue("email", "sign.error.email.empty", "sign.error.email.empty");
		}

		if (StringUtils.isBlank(form.getCity())) {
			errors.rejectValue("city", "sign.error.city.empty", "sign.error.city.empty");
		}

		if (StringUtils.isBlank(form.getNationality())) {
			errors.rejectValue("nationality", "sign.error.nationality.empty",
					"sign.error.nationality.empty");
		}

		if (!(StringUtils.isBlank(form.getOldPassword())
				&& StringUtils.isBlank(form.getNewPassword()) && StringUtils.isBlank(form
				.getRepeatNewPassword()))) {

			if (!HashUtils.passwordEncoder(form.getOldPassword()).equals(
					userService.findByPrincipal().getUserAccount().getPassword())) {
				errors.rejectValue("oldPassword", "sign.error.old.password",
						"sign.error.old.password");
			}

			if (StringUtils.isBlank(form.getNewPassword())) {
				errors.rejectValue("newPassword", "sign.error.password.empty",
						"sign.error.password.empty");
			}

			if (form.getNewPassword().length() < 5) {
				errors.rejectValue("newPassword", "sign.error.password.characters",
						"sign.error.password.characters");
			}

			if (!form.getNewPassword().equals(form.getRepeatNewPassword())) {
				errors.rejectValue("repeatNewPassword", "sign.error.password.different",
						"sign.error.password.different");
			}
		}

		EmailValidator eValidator = EmailValidator.getInstance(false);

		if (!eValidator.isValid(form.getEmail())) {
			errors.rejectValue("email", "sign.error.email.invalid", "sign.error.email.invalid");
		}
	}
}
