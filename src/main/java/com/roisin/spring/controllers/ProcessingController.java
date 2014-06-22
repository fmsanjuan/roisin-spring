package com.roisin.spring.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.tools.XMLException;
import com.roisin.core.processes.GenericProcesses;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.RoisinRule;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.utils.Constants;

@Controller
@RequestMapping("/processing")
public class ProcessingController {

	private static final Logger logger = LoggerFactory.getLogger(ProcessingController.class);

	/**
	 * Creates and returns the model and view.
	 * 
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/create1", method = RequestMethod.GET)
	public ModelAndView create1(Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);

		try {
			logger.info("Iniciando rapidminer");
			RapidMiner.setExecutionMode(ExecutionMode.APPSERVER);
			RapidMiner.init();
		} catch (Exception e) {
			logger.error("No ha sido posible iniciar Rapidminer. Revise la configuración.");
		}

		IOContainer container = null;
		try {
			Process process = new Process(
					new File(
							"/Users/felix/03.TFG/ws_spring_roisin/roisin-spring/src/main/resources/ripper-golf.xml"));
			container = process.run();
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RuleModel ruleModel = (RuleModel) container.asList().get(0);
		ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
		RipperResults results = new RipperResults(ruleModel, exampleSet);
		List<RoisinRule> rules = results.getRoisinRules();

		ModelAndView res = new ModelAndView("processing/processing");
		res.addObject("rules", rules);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);

		ModelAndView res = new ModelAndView("processing/create");

		return res;
	}

	@RequestMapping(value = "/ripper", method = RequestMethod.POST)
	public ModelAndView ripper(@ModelAttribute("form") PreprocessingForm form, BindingResult result) {

		Process process = GenericProcesses.getRipper(
				StringUtils.substringAfterLast(form.getFilePath(), Constants.DOT_SYMBOL),
				form.getFilePath(), form.getLabel(), form.getDeletedRows(),
				form.getFilterCondition(), form.getDeletedAttributes());
		RipperResults results = null;

		try {
			IOContainer container = process.run();
			RuleModel ruleModel = (RuleModel) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView res = new ModelAndView("processing/create");
		res.addObject("results", results);

		return res;
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute("form") PreprocessingForm form,
			BindingResult result) {

		ModelAndView res = new ModelAndView("processing/create");

		return res;
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute("form") PreprocessingForm form, BindingResult result) {

		ModelAndView res = new ModelAndView("processing/create");

		return res;
	}
}
