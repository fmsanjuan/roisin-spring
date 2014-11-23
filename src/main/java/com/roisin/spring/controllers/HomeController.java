package com.roisin.spring.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.security.Credentials;

/**
 * Controllador de la pantalla de bienvenida.
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 */
@Controller
@RequestMapping("/")
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale) {
		ModelAndView res = new ModelAndView("welcome/home");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

		String formattedDate = dateFormat.format(date);

		res.addObject("serverTime", formattedDate);
		res.addObject("credentials", new Credentials());

		return res;
	}

}
