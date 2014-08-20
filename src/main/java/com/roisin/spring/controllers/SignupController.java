package com.roisin.spring.controllers;

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
		res.addObject("form", form);
		res.addObject("error", false);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("form") SignupForm form, BindingResult result) {

		formValidator.validate(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("signup/new");
			res.addObject("form", form);
			res.addObject("error", true);
			return res;
		} else {
			try {
				User user = userService.reconstruct(form);
				userService.save(user, true);

				ModelAndView res = new ModelAndView("welcome/home");
				res.addObject("credentials", new Credentials());
				res.addObject("successMessage", "The user " + user.getEmail()
						+ " has been registered");
				return res;
			} catch (Throwable oops) {
				ModelAndView res;
				if (oops instanceof DataIntegrityViolationException) {
					res = createEditModelAndViewCustomer(form, "Duplicated email");
				} else {
					res = createEditModelAndViewCustomer(form, "Error");
				}
				return res;
			}
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser() {
		EditProfileForm form = userService.constructEditForm();
		ModelAndView res = new ModelAndView("profile/edit");
		res.addObject("form", form);
		res.addObject("error", false);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("form") EditProfileForm form, BindingResult result) {

		formValidator.validateEditForm(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("profile/edit");
			res.addObject("form", form);
			res.addObject("error", true);
			return res;
		} else {
			try {
				User user = userService.reconstruct(form);
				userService.save(user, StringUtils.isNotBlank(form.getNewPassword()));

				ModelAndView res = new ModelAndView("profile/edit");
				res.addObject("successMessage", "The user profile " + user.getEmail()
						+ " has been successfully edited");
				return res;
			} catch (Throwable oops) {
				ModelAndView res;
				if (oops instanceof DataIntegrityViolationException) {
					res = createEditProfileModelAndViewCustomer(form, "Duplicated email");
				} else {
					res = createEditProfileModelAndViewCustomer(form, "Error");
				}
				return res;
			}
		}
	}

	public ModelAndView createEditModelAndViewCustomer(SignupForm form, String message) {

		ModelAndView res = new ModelAndView("signup/new");
		res.addObject("form", form);
		res.addObject("error", true);
		res.addObject("errorMessage", message);
		return res;
	}

	public ModelAndView createEditProfileModelAndViewCustomer(EditProfileForm form, String message) {

		ModelAndView res = new ModelAndView("profile/edit");
		res.addObject("form", form);
		res.addObject("error", true);
		res.addObject("errorMessage", message);
		return res;
	}

}
