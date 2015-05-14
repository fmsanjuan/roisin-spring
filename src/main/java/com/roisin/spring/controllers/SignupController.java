package com.roisin.spring.controllers;

import static com.roisin.spring.utils.ModelViewConstants.CREDENTIALS;
import static com.roisin.spring.utils.ModelViewConstants.ERROR_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.ERROR_MESSAGE;
import static com.roisin.spring.utils.ModelViewConstants.FORM_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.SUCCESS_MESSAGE;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.forms.EditProfileForm;
import com.roisin.spring.forms.SignupForm;
import com.roisin.spring.model.User;
import com.roisin.spring.security.Credentials;
import com.roisin.spring.security.MailService;
import com.roisin.spring.services.UserService;
import com.roisin.spring.validator.SignupFormValidator;

@Controller
@RequestMapping("/signup")
public class SignupController {

	/**
	 * User service
	 */
	@Autowired
	private transient UserService userService;

	/**
	 * Sign up form validator
	 */
	@Autowired
	private transient SignupFormValidator formValidator;

	/**
	 * Mail service
	 */
	@Autowired
	private transient MailService mailService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView newUser() {
		final SignupForm form = userService.constructNew();
		final ModelAndView res = new ModelAndView("signup/new");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, false);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("form") final SignupForm form,
			final BindingResult result) {

		formValidator.validate(form, result);

		ModelAndView res;

		if (result.hasErrors()) {
			res = new ModelAndView("signup/new");
			res.addObject(FORM_LOWER_CASE, form);
			res.addObject(ERROR_LOWER_CASE, true);
		} else {
			try {
				User user = userService.reconstruct(form);
				user = userService.save(user, true);

				mailService.sendActivationEmail(user);

				res = new ModelAndView("welcome/home");
				res.addObject(CREDENTIALS, new Credentials());
				res.addObject(SUCCESS_MESSAGE, user.getEmail());
			} catch (Throwable oops) {
				if (oops instanceof DataIntegrityViolationException) {
					res = createEditModelAndViewCustomer(form, "sign.error.duplicated.email");
				} else {
					oops.printStackTrace();
					res = createEditModelAndViewCustomer(form, "sign.error");
				}
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser() {
		final EditProfileForm form = userService.constructEditForm();
		final ModelAndView res = new ModelAndView("profile/edit");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, false);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute(FORM_LOWER_CASE) final EditProfileForm form,
			final BindingResult result) {

		formValidator.validateEditForm(form, result);

		ModelAndView res;

		if (result.hasErrors()) {
			res = new ModelAndView("profile/edit");
			res.addObject(FORM_LOWER_CASE, form);
			res.addObject(ERROR_LOWER_CASE, true);
		} else {
			try {
				final User user = userService.reconstruct(form);
				userService.save(user, StringUtils.isNotBlank(form.getNewPassword()));

				res = new ModelAndView("profile/edit");
				res.addObject(SUCCESS_MESSAGE, user.getEmail());
			} catch (Throwable oops) {
				if (oops instanceof DataIntegrityViolationException) {
					res = createEditProfileModelAndViewCustomer(form, "sign.error.duplicated.email");
				} else {
					res = createEditProfileModelAndViewCustomer(form, "sign.error");
					oops.printStackTrace();
				}
			}
		}
		return res;
	}

	public ModelAndView createEditModelAndViewCustomer(final SignupForm form, final String message) {

		final ModelAndView res = new ModelAndView("signup/new");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, true);
		res.addObject(ERROR_MESSAGE, message);
		return res;
	}

	public ModelAndView createEditProfileModelAndViewCustomer(final EditProfileForm form,
			final String message) {

		final ModelAndView res = new ModelAndView("profile/edit");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, true);
		res.addObject(ERROR_MESSAGE, message);
		return res;
	}

}
