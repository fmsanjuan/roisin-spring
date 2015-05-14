package com.roisin.spring.validator;

import static com.roisin.spring.utils.ModelViewConstants.CITY;
import static com.roisin.spring.utils.ModelViewConstants.EMAIL;
import static com.roisin.spring.utils.ModelViewConstants.NAME;
import static com.roisin.spring.utils.ModelViewConstants.NATIONALITY;
import static com.roisin.spring.utils.ModelViewConstants.NEW_PASSWORD;
import static com.roisin.spring.utils.ModelViewConstants.OLD_PASSWORD;
import static com.roisin.spring.utils.ModelViewConstants.PASSWORD;
import static com.roisin.spring.utils.ModelViewConstants.REPEAT_NEW_PASSWORD;

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

	/**
	 * Max password length
	 */
	private static final int MAX_PASSWORD_LGT = 5;

	/**
	 * i18n message for invalid email
	 */
	private static final String MSG_INVALID_EMAIL = "sign.error.email.invalid";

	/**
	 * i18n message for different passwords in sign in process
	 */
	private static final String MSG_DIFF_PASSWORD = "sign.error.password.different";

	/**
	 * i18n message for minimum password characters in sign in process
	 */
	private static final String MSG_CHARACTERS = "sign.error.password.characters";

	/**
	 * i18n message for empty password in sign in process
	 */
	private static final String MSG_EMPTY_PASSW = "sign.error.password.empty";

	/**
	 * i18n message for empty nationality in sign in process
	 */
	private static final String MSG_EMPTY_NATION = "sign.error.nationality.empty";

	/**
	 * i18n message for empty city in sign in process
	 */
	private static final String MSG_EMPTY_CITY = "sign.error.city.empty";

	/**
	 * i18n message for empty email in sign in process
	 */
	private static final String MSG_EMPTY_EMAIL = "sign.error.email.empty";

	/**
	 * i18n message for empty name in sign in process
	 */
	private static final String MSG_EMPTY_NAME = "sign.error.name.empty";

	/**
	 * User service
	 */
	@Autowired
	private transient UserService userService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final SignupForm form = (SignupForm) target;

		if (StringUtils.isBlank(form.getName())) {
			errors.rejectValue(NAME, MSG_EMPTY_NAME, MSG_EMPTY_NAME);
		}

		if (StringUtils.isBlank(form.getEmail())) {
			errors.rejectValue(EMAIL, MSG_EMPTY_EMAIL, MSG_EMPTY_EMAIL);
		}

		if (StringUtils.isBlank(form.getCity())) {
			errors.rejectValue(CITY, MSG_EMPTY_CITY, MSG_EMPTY_CITY);
		}

		if (StringUtils.isBlank(form.getNationality())) {
			errors.rejectValue(NATIONALITY, MSG_EMPTY_NATION, MSG_EMPTY_NATION);
		}

		if (StringUtils.isBlank(form.getPassword())) {
			errors.rejectValue(PASSWORD, MSG_EMPTY_PASSW, MSG_EMPTY_PASSW);
		}

		if (form.getPassword().length() < MAX_PASSWORD_LGT) {
			errors.rejectValue(PASSWORD, MSG_CHARACTERS, MSG_CHARACTERS);
		}

		if (!form.getPassword().equals(form.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", MSG_DIFF_PASSWORD, MSG_DIFF_PASSWORD);
		}

		final EmailValidator eValidator = EmailValidator.getInstance(false);

		if (!eValidator.isValid(form.getEmail())) {
			errors.rejectValue(EMAIL, MSG_INVALID_EMAIL, MSG_INVALID_EMAIL);
		}

	}

	public void validateEditForm(final Object target, final Errors errors) {

		final EditProfileForm form = (EditProfileForm) target;

		if (StringUtils.isBlank(form.getName())) {
			errors.rejectValue(NAME, MSG_EMPTY_NAME, MSG_EMPTY_NAME);
		}

		if (StringUtils.isBlank(form.getEmail())) {
			errors.rejectValue(EMAIL, MSG_EMPTY_EMAIL, MSG_EMPTY_EMAIL);
		}

		if (StringUtils.isBlank(form.getCity())) {
			errors.rejectValue(CITY, MSG_EMPTY_CITY, MSG_EMPTY_CITY);
		}

		if (StringUtils.isBlank(form.getNationality())) {
			errors.rejectValue(NATIONALITY, MSG_EMPTY_NATION, MSG_EMPTY_NATION);
		}

		if (!(StringUtils.isBlank(form.getOldPassword())
				&& StringUtils.isBlank(form.getNewPassword()) && StringUtils.isBlank(form
				.getRepeatNewPassword()))) {

			if (!HashUtils.passwordEncoder(form.getOldPassword()).equals(
					userService.findByPrincipal().getUserAccount().getPassword())) {
				errors.rejectValue(OLD_PASSWORD, "sign.error.old.password",
						"sign.error.old.password");
			}

			if (StringUtils.isBlank(form.getNewPassword())) {
				errors.rejectValue(NEW_PASSWORD, MSG_EMPTY_PASSW, MSG_EMPTY_PASSW);
			}

			if (form.getNewPassword().length() < MAX_PASSWORD_LGT) {
				errors.rejectValue(NEW_PASSWORD, MSG_CHARACTERS, MSG_CHARACTERS);
			}

			if (!form.getNewPassword().equals(form.getRepeatNewPassword())) {
				errors.rejectValue(REPEAT_NEW_PASSWORD, MSG_DIFF_PASSWORD, MSG_DIFF_PASSWORD);
			}
		}

		final EmailValidator eValidator = EmailValidator.getInstance(false);

		if (!eValidator.isValid(form.getEmail())) {
			errors.rejectValue(EMAIL, MSG_INVALID_EMAIL, MSG_INVALID_EMAIL);
		}
	}
}
