package com.roisin.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.charts4j.XYLineChart;
import com.roisin.spring.model.Rule;
import com.roisin.spring.services.RuleService;
import com.roisin.spring.utils.RoisinUtils;

@Controller
@RequestMapping("/rule")
public class RuleController {

	private static final Logger logger = LoggerFactory.getLogger(RuleController.class);

	@Autowired
	private RuleService ruleService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int ruleId) {

		Rule rule = ruleService.findOne(ruleId);

		XYLineChart chart = RoisinUtils.getSingleRuleAucChart(rule);

		ModelAndView res = new ModelAndView("rule/view");
		res.addObject("rule", rule);
		res.addObject("chart", chart.toURLString());

		return res;
	}

}
