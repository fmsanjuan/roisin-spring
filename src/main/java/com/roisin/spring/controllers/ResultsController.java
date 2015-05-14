package com.roisin.spring.controllers;

import static com.roisin.spring.utils.ModelViewConstants.CHART_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.REMOVED_RULES;
import static com.roisin.spring.utils.ModelViewConstants.REQUEST_URI;
import static com.roisin.spring.utils.ModelViewConstants.RESULTS_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.RULES_LOWER_CASE;

import java.util.Collection;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	/**
	 * Results service
	 */
	@Autowired
	private transient ResultsService resultsService;

	/**
	 * Rule service
	 */
	@Autowired
	private transient RuleService ruleService;

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportResults(@ModelAttribute final Results results) {

		final ByteArrayOutputStream document = resultsService.getExcelResults(results);

		// Create and configure headers to return the file
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/xls"));
		headers.setContentDispositionFormData("roisin_exported_data", "roisin_exported_data.xls");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(document.toByteArray(),
				headers, HttpStatus.OK);

		return response;
	}

	@RequestMapping(value = "/exportoptimization", method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportOptimization(@ModelAttribute final Results results) {

		final ByteArrayOutputStream document = resultsService.getOptimizationExcelResults(results);

		// Create and configure headers to return the file
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/xls"));
		headers.setContentDispositionFormData("roisin_exported_data", "roisin_exported_data.xls");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(document.toByteArray(),
				headers, HttpStatus.OK);

		return response;
	}

	@RequestMapping(value = "/optimization", method = RequestMethod.POST)
	public ModelAndView optimization(@ModelAttribute final Results results) {

		final Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		final Collection<Rule> removedRules = RoisinUtils.getAucOptimizationRemovedRules(rules);
		rules.removeAll(removedRules);

		final XYLineChart chart = RoisinUtils.getAucChart(rules,
				RoisinUtils.calculateRulesAuc(rules));

		final ModelAndView res = new ModelAndView("results/optimization");
		res.addObject(RULES_LOWER_CASE, rules);
		res.addObject(REMOVED_RULES, removedRules);
		res.addObject(CHART_LOWER_CASE, chart.toURLString());
		res.addObject(RESULTS_LOWER_CASE, results);

		return res;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam final int resultsId) {

		final Collection<Rule> rules = ruleService.findRulesByResultsId(resultsId);
		final XYLineChart chart = RoisinUtils.getAucChart(rules, resultsId);
		final Results results = resultsService.findOne(resultsId);

		final ModelAndView res = new ModelAndView("results/view");
		res.addObject(RULES_LOWER_CASE, rules);
		res.addObject(REQUEST_URI, "results/view?=resultsId=" + resultsId);
		res.addObject(CHART_LOWER_CASE, chart.toURLString());
		res.addObject(RULES_LOWER_CASE, rules);
		res.addObject(RESULTS_LOWER_CASE, results);

		return res;
	}

}
