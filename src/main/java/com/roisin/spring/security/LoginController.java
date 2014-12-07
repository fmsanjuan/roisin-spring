/* LoginController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package com.roisin.spring.security;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/security")
public class LoginController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService service;

	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	@RequestMapping("/login")
	public ModelAndView login(@Valid @ModelAttribute Credentials credentials,
			BindingResult bindingResult, @RequestParam(required = false) boolean showError) {
		Assert.notNull(credentials);
		Assert.notNull(bindingResult);

		ModelAndView result;

		result = new ModelAndView("welcome/home");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);

		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/credentials")
	public ModelAndView credentialFailure() {
		Credentials credentials = new Credentials();
		ModelAndView result = new ModelAndView("welcome/home");
		result.addObject("errorMessage", true);
		result.addObject("credentials", credentials);
		return result;
	}

	@RequestMapping("/activation")
	public ModelAndView activationRequired() {
		ModelAndView result = new ModelAndView("security/activation");
		result.addObject("notActivated", true);
		result.addObject("message", "welcome.activation.not.activated");
		return result;
	}

	// Activation
	@RequestMapping(value = "/activate/{id}/{activationKey}", method = RequestMethod.GET)
	public ModelAndView activateAccount(@PathVariable Integer id, @PathVariable String activationKey) {
		Boolean activationSuccess = true;
		String message = StringUtils.EMPTY;

		// Comprobaciones
		// 1: La cuenta ya está activada
		// 2: No han pasado más de 24h desde la fecha de envío del email de
		// activación

		UserAccount userAccount = service.findOne(id);
		if (userAccount.getEnabled()) {
			// La cuenta ya está activada
			activationSuccess = false;
			message = "welcome.activation.already.activated";
		} else if (DateUtils.addDays(userAccount.getActivation(), 1).before(new Date())) {
			// Han pasado más de 24h desde el envío del email de activación
			activationSuccess = false;
			message = "welcome.activation.expired";
		} else {
			activationSuccess = service.activateAccount(id, activationKey);
			if (activationSuccess) {
				message = "welcome.activation.success";
			} else {
				message = "welcome.activation.incorrect.key";
			}
		}

		ModelAndView result = new ModelAndView("security/activation");
		result.addObject("activationSuccess", activationSuccess);
		result.addObject("message", message);
		return result;
	}
}