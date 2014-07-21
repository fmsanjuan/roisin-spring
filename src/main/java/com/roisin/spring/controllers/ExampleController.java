package com.roisin.spring.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.model.Examples;
import com.roisin.spring.model.User;
import com.roisin.spring.services.ExamplesService;
import com.roisin.spring.services.UserService;

@Controller
@RequestMapping("/example")
public class ExampleController {

	@Autowired
	private ExamplesService exampleService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		User user = userService.findByPrincipal();
		Collection<Examples> examples = exampleService.findExamplesByUserId(user.getId());
		ModelAndView res = new ModelAndView("examples/list");
		res.addObject("examples", examples);

		return res;
	}

}
