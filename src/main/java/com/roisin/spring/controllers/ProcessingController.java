package com.roisin.spring.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.charts4j.XYLineChart;
import com.roisin.core.results.RoisinResults;
import com.roisin.spring.forms.PreprocessingForm;
import com.roisin.spring.services.ProcessingService;
import com.roisin.spring.utils.ProcessConstants;
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
			res.addObject("error", ProcessConstants.RIPPER);
			res.addObject("form", form);
			return res;
		} else {
			RoisinResults results = processingService.getRipperResults(form);
			XYLineChart chart = processingService.getAucChart(results);
			form.setAlgorithm(ProcessConstants.RIPPER);

			ModelAndView res = new ModelAndView("results/create");
			res.addObject("results", results);
			res.addObject("form", form);
			res.addObject("chart", chart.toURLString());
			return res;
		}
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute("form") PreprocessingForm form,
			BindingResult result) {
		formValidator.validateSubgroup(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("processing/create");
			res.addObject("error", ProcessConstants.SUBGROUP_DISCOVERY);
			res.addObject("form", form);
			return res;
		} else {
			RoisinResults results = processingService.getSubgroupResults(form);
			XYLineChart chart = processingService.getAucChart(results);
			form.setAlgorithm(ProcessConstants.SUBGROUP_DISCOVERY);

			ModelAndView res = new ModelAndView("results/create");
			res.addObject("results", results);
			res.addObject("form", form);
			res.addObject("chart", chart.toURLString());
			return res;
		}
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute("form") PreprocessingForm form, BindingResult result) {

		formValidator.validateTree2rules(form, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("processing/create");
			res.addObject("error", ProcessConstants.TREE_TO_RULES);
			res.addObject("form", form);
			return res;
		} else {
			RoisinResults results = processingService.getTreeToRulesResults(form);
			XYLineChart chart = processingService.getAucChart(results);
			form.setAlgorithm(ProcessConstants.TREE_TO_RULES);

			ModelAndView res = new ModelAndView("results/create");
			res.addObject("results", results);
			res.addObject("form", form);
			res.addObject("chart", chart.toURLString());
			return res;
		}
	}

}
