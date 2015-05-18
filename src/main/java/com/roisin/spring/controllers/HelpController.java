package com.roisin.spring.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/help")
public class HelpController {

	@Value("${manual.en}")
	private String manualEn;

	@Value("${manual.es}")
	private String manualEs;

	@RequestMapping(value = "/manual", method = RequestMethod.GET)
	public ModelAndView help() {
		final ModelAndView res = new ModelAndView("help/manual");
		res.addObject("manualEs", manualEs);
		res.addObject("manualEn", manualEn);
		return res;
	}
}
