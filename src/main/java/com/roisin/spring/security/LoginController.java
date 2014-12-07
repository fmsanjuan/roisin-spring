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

import com.roisin.spring.forms.ChangePasswordForm;
import com.roisin.spring.forms.PasswordRecoveryRequestForm;

import exception.RoisinException;

@Controller
@RequestMapping("/security")
public class LoginController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService service;

	@Autowired
	private MailService mailService;

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

	// Forgot password?
	@RequestMapping("/forgot")
	public ModelAndView forgotPassword() {
		PasswordRecoveryRequestForm form = new PasswordRecoveryRequestForm();
		ModelAndView result = new ModelAndView("security/forgot");
		result.addObject("form", form);
		return result;
	}

	@RequestMapping(value = "/request/recovery", method = RequestMethod.POST)
	public ModelAndView requestRecovery(@ModelAttribute PasswordRecoveryRequestForm form) {
		UserAccount userAccount = service.findByUsername(form.getEmail());
		if (userAccount != null) {
			// Envío de correo para la recuperación de contraseña
			mailService.sendPasswordRecoverEmail(userAccount);
			ModelAndView result = new ModelAndView("security/forgot");
			result.addObject("success", true);
			return result;
		} else {
			ModelAndView result = new ModelAndView("security/forgot");
			result.addObject("message", "welcome.incorrect.user");
			result.addObject("form", form);
			return result;
		}
	}

	// Activation
	@RequestMapping(value = "/recover/{id}/{requestKey}", method = RequestMethod.GET)
	public ModelAndView passwordChangeRequest(@PathVariable Integer id,
			@PathVariable String requestKey) throws RoisinException {
		UserAccount userAccount = service.findOne(id);
		if (userAccount != null) {
			String correctRequestKey = service.generatePasswordRecoveryKey(userAccount);
			if (correctRequestKey.equals(requestKey)) {
				// Se devuelve el formulario de cambio de contraseña
				ChangePasswordForm form = new ChangePasswordForm();
				form.setKey(requestKey);
				form.setUserAccountId(id);
				ModelAndView result = new ModelAndView("security/change");
				result.addObject("form", form);
				return result;
			} else {
				throw new RoisinException("Petición de cambio de contraseña no válida");
			}
		} else {
			throw new RoisinException("Petición de cambio de contraseña no válida");
		}
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView changePassword(@ModelAttribute ChangePasswordForm form)
			throws RoisinException {
		if (!form.getNewPassword().equals(form.getRepeatNewPassword())) {
			ModelAndView result = new ModelAndView("security/change");
			result.addObject("form", form);
			result.addObject("message", "welcome.passwords.incorrect");
			return result;
		} else {
			UserAccount userAccount = service.findOne(form.getUserAccountId());
			if (userAccount != null) {
				String correctRequestKey = service.generatePasswordRecoveryKey(userAccount);
				if (correctRequestKey.equals(form.getKey())) {
					service.changePassword(userAccount, form.getNewPassword());
					ModelAndView result = new ModelAndView("security/change");
					result.addObject("form", form);
					result.addObject("success", true);
					return result;
				} else {
					throw new RoisinException("Petición de cambio de contraseña no válida");
				}
			} else {
				throw new RoisinException("Petición de cambio de contraseña no válida");
			}
		}
	}
}