package com.roisin.spring.controllers;

import static com.roisin.spring.utils.ModelViewConstants.CHART_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.RULE_LOWER_CASE;

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

	@Autowired
	private RuleService ruleService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int ruleId) {

		Rule rule = ruleService.findOne(ruleId);

		XYLineChart chart = RoisinUtils.getSingleRuleAucChart(rule);

		ModelAndView res = new ModelAndView("rule/view");
		res.addObject(RULE_LOWER_CASE, rule);
		res.addObject(CHART_LOWER_CASE, chart.toURLString());

		return res;
	}

}
