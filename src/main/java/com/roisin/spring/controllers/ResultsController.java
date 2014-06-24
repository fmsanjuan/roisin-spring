package com.roisin.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.model.UploadedFile;

@Controller
@RequestMapping("/results")
public class ResultsController {

	private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {

		ModelAndView res = new ModelAndView("preprocessing/create");
		res.addObject("uploaded", false);

		return res;
	}

}
