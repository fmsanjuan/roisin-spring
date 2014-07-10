package com.roisin.spring.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.core.results.RoisinResults;
import com.roisin.spring.forms.PreprocessingForm;
import com.roisin.spring.services.ProcessingService;
import com.roisin.spring.validator.PreprocessingFormValidator;

@Controller
@RequestMapping("/processing")
public class ProcessingController {

	@Autowired
	private ProcessingService processingService;

	@Autowired
	PreprocessingFormValidator formValidator;

	private static final Logger logger = LoggerFactory.getLogger(ProcessingController.class);

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);

		ModelAndView res = new ModelAndView("processing/create");

		return res;
	}

	@RequestMapping(value = "/ripper", method = RequestMethod.POST)
	public ModelAndView ripper(@ModelAttribute("form") PreprocessingForm form, BindingResult result) {

		formValidator.validateRipper(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("processing/create");
			res.addObject("error", "Ripper");
			res.addObject("form", form);
			return res;
		} else {
			RoisinResults results = processingService.getRipperResults(form);

			ModelAndView res = new ModelAndView("results/create");
			res.addObject("results", results);
			return res;
		}
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute("form") @Valid PreprocessingForm form,
			BindingResult result) {
		formValidator.validateSubgroup(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("processing/create");
			res.addObject("error", "Subgroup Discovery");
			res.addObject("form", form);
			return res;
		} else {
			RoisinResults results = processingService.getSubgroupResults(form);

			ModelAndView res = new ModelAndView("results/create");
			res.addObject("results", results);
			return res;
		}
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute("form") PreprocessingForm form, BindingResult result) {

		formValidator.validateTree2rules(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("processing/create");
			res.addObject("error", "Tree to Rules");
			res.addObject("form", form);
			return res;
		} else {
			RoisinResults results = processingService.getTreeToRulesResults(form);

			ModelAndView res = new ModelAndView("results/create");
			res.addObject("results", results);
			return res;
		}
	}

}
