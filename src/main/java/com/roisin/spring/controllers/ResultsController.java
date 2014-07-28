package com.roisin.spring.controllers;

import java.util.Collection;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.charts4j.XYLineChart;
import com.roisin.spring.model.Results;
import com.roisin.spring.model.Rule;
import com.roisin.spring.services.ResultsService;
import com.roisin.spring.services.RuleService;
import com.roisin.spring.utils.RoisinUtils;

@Controller
@RequestMapping("/results")
public class ResultsController {

	private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);

	@Autowired
	private ResultsService resultsService;

	@Autowired
	private RuleService ruleService;

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportResults(@ModelAttribute Results results) {

		ByteArrayOutputStream document = resultsService.getExcelResults(results.getId());

		// Create and configure headers to return the file
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/xls"));
		headers.setContentDispositionFormData("roisin_exported_data", "roisin_exported_data.xls");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(document.toByteArray(),
				headers, HttpStatus.OK);

		return response;
	}

	@RequestMapping(value = "/roc", method = RequestMethod.POST)
	public ModelAndView roc(@ModelAttribute Results results) {

		Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		Collection<Rule> removedRules = RoisinUtils.getRocAnalysisRemovedRules(rules);
		rules.removeAll(removedRules);

		XYLineChart chart = RoisinUtils.getAucChart(rules, results.getAuc());

		ModelAndView res = new ModelAndView("results/roc");
		res.addObject("rules", rules);
		res.addObject("removedRules", removedRules);
		res.addObject("chart", chart.toURLString());

		return res;
	}

}
