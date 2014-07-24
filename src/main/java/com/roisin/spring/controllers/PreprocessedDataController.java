package com.roisin.spring.controllers;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.services.PreprocessedDataService;
import com.roisin.spring.services.UserService;

@Controller
@RequestMapping("/data")
public class PreprocessedDataController {

	private static final Logger logger = LoggerFactory.getLogger(PreprocessedDataController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PreprocessedDataService preprocessedDataService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<PreprocessedData> dataList = preprocessedDataService.findAll();
		ModelAndView res = new ModelAndView("data/list");
		res.addObject("dataList", dataList);
		res.addObject("requestURI", "list");

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Collection<PreprocessedData> dataList = preprocessedDataService.findAll();
		ModelAndView res = new ModelAndView("data/list");
		res.addObject("dataList", dataList);
		res.addObject("requestURI", "list");

		return res;
	}

}
