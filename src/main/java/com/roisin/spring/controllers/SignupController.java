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
import com.roisin.spring.services.UserService;
import com.roisin.spring.validator.SignupFormValidator;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private UserService userService;

	@Autowired
	private SignupFormValidator formValidator;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView newUser() {
		SignupForm form = userService.constructNew();
		ModelAndView res = new ModelAndView("signup/new");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, false);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute(FORM_LOWER_CASE) SignupForm form, BindingResult result) {

		formValidator.validate(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("signup/new");
			res.addObject(FORM_LOWER_CASE, form);
			res.addObject(ERROR_LOWER_CASE, true);
			return res;
		} else {
			try {
				User user = userService.reconstruct(form);
				userService.save(user, true);

				ModelAndView res = new ModelAndView("welcome/home");
				res.addObject(CREDENTIALS, new Credentials());
				res.addObject(SUCCESS_MESSAGE, user.getEmail());
				return res;
			} catch (Throwable oops) {
				ModelAndView res;
				if (oops instanceof DataIntegrityViolationException) {
					res = createEditModelAndViewCustomer(form, "sign.error.duplicated.email");
				} else {
					res = createEditModelAndViewCustomer(form, "sign.error");
				}
				return res;
			}
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser() {
		EditProfileForm form = userService.constructEditForm();
		ModelAndView res = new ModelAndView("profile/edit");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, false);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute(FORM_LOWER_CASE) EditProfileForm form,
			BindingResult result) {

		formValidator.validateEditForm(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("profile/edit");
			res.addObject(FORM_LOWER_CASE, form);
			res.addObject(ERROR_LOWER_CASE, true);
			return res;
		} else {
			try {
				User user = userService.reconstruct(form);
				userService.save(user, StringUtils.isNotBlank(form.getNewPassword()));

				ModelAndView res = new ModelAndView("profile/edit");
				res.addObject(SUCCESS_MESSAGE, user.getEmail());
				return res;
			} catch (Throwable oops) {
				ModelAndView res;
				if (oops instanceof DataIntegrityViolationException) {
					res = createEditProfileModelAndViewCustomer(form, "sign.error.duplicated.email");
				} else {
					res = createEditProfileModelAndViewCustomer(form, "sign.error");
				}
				return res;
			}
		}
	}

	public ModelAndView createEditModelAndViewCustomer(SignupForm form, String message) {

		ModelAndView res = new ModelAndView("signup/new");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, true);
		res.addObject(ERROR_MESSAGE, message);
		return res;
	}

	public ModelAndView createEditProfileModelAndViewCustomer(EditProfileForm form, String message) {

		ModelAndView res = new ModelAndView("profile/edit");
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(ERROR_LOWER_CASE, true);
		res.addObject(ERROR_MESSAGE, message);
		return res;
	}

}
