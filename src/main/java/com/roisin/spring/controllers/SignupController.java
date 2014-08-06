package com.roisin.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.forms.SignupForm;
import com.roisin.spring.model.User;
import com.roisin.spring.security.Credentials;
import com.roisin.spring.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView newUser() {
		SignupForm form = userService.constructNew();
		ModelAndView res = new ModelAndView("signup/new");
		res.addObject("form", form);
		res.addObject("error", false);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute SignupForm form, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("signup/new");
			res.addObject("form", form);
			res.addObject("error", true);
			return res;
		} else {
			try {
				User user = userService.reconstruct(form);
				userService.save(user);

				ModelAndView res = new ModelAndView("welcome/home");
				res.addObject("credentials", new Credentials());
				return res;
			} catch (Throwable oops) {
				ModelAndView res;
				if (oops.getMessage().equals("Passwords are different")) {
					res = createEditModelAndViewCustomer(form, "Passwords are different");
				} else if (oops instanceof DataIntegrityViolationException) {
					res = createEditModelAndViewCustomer(form, "Duplicated email");
				} else {
					res = createEditModelAndViewCustomer(form, "Error");
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

}
