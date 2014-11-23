package com.roisin.spring.controllers;

import static com.roisin.spring.utils.ModelViewConstants.CHART_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.DATA_NAME;
import static com.roisin.spring.utils.ModelViewConstants.ERROR_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.PROCESSES_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.REQUEST_URI;
import static com.roisin.spring.utils.ModelViewConstants.RESULTS_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.RIPPER_SETTINGS;
import static com.roisin.spring.utils.ModelViewConstants.RULES_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.SUBGROUP_SETTINGS;
import static com.roisin.spring.utils.ModelViewConstants.TREE_SETTINGS;
import static com.roisin.spring.utils.ProcessConstants.RIPPER;
import static com.roisin.spring.utils.ProcessConstants.SUBGROUP_DISCOVERY;
import static com.roisin.spring.utils.ProcessConstants.TREE_TO_RULES;

import java.util.Collection;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.charts4j.XYLineChart;
import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.Process;
import com.roisin.spring.model.Results;
import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.model.Rule;
import com.roisin.spring.model.SubgroupSettings;
import com.roisin.spring.model.TreeToRulesSettings;
import com.roisin.spring.services.DeletedRowService;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.PreprocessedDataService;
import com.roisin.spring.services.ProcessService;
import com.roisin.spring.services.ResultsService;
import com.roisin.spring.services.RipperSettingsService;
import com.roisin.spring.services.RuleService;
import com.roisin.spring.services.SelectedAttributeService;
import com.roisin.spring.services.SubgroupSettingsService;
import com.roisin.spring.services.TreeToRulesSettingsService;
import com.roisin.spring.utils.RoisinUtils;
import com.roisin.spring.validator.RipperSettingsValidator;
import com.roisin.spring.validator.SubgroupSettingsValidator;
import com.roisin.spring.validator.TreeSettingsValidator;

@Controller
@RequestMapping("/process")
public class ProcessController {

	@Autowired
	private ProcessService processService;

	@Autowired
	private ResultsService resultsService;

	@Autowired
	private FileService fileService;

	@Autowired
	private SelectedAttributeService selectedAttributeService;

	@Autowired
	private DeletedRowService deletedRowService;

	@Autowired
	private RipperSettingsService ripperSettingsService;

	@Autowired
	private SubgroupSettingsService subgroupSettingsService;

	@Autowired
	private TreeToRulesSettingsService treeToRulesSettingsService;

	@Autowired
	private PreprocessedDataService preprocessedDataService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	private RipperSettingsValidator ripperValidator;

	@Autowired
	private SubgroupSettingsValidator subgroupValidator;

	@Autowired
	private TreeSettingsValidator treeValidator;

	@RequestMapping(value = "/ripper", method = RequestMethod.POST)
	public ModelAndView ripper(@ModelAttribute RipperSettings ripperSettings, BindingResult result)
			throws NamingException {

		ripperValidator.validate(ripperSettings, result);

		if (result.hasErrors()) {
			SubgroupSettings subgroupSettings = subgroupSettingsService.create();
			subgroupSettings.setProcess(ripperSettings.getProcess());
			TreeToRulesSettings treeSettings = treeToRulesSettingsService.create();
			treeSettings.setProcess(ripperSettings.getProcess());

			ModelAndView res = new ModelAndView("process/create");
			res.addObject(RIPPER_SETTINGS, ripperSettings);
			res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
			res.addObject(TREE_SETTINGS, treeSettings);
			res.addObject(ERROR_LOWER_CASE, RIPPER);

			return res;

		} else {

			Results results = resultsService.calculateRipperResults(ripperSettings);

			return createResultsModelAndView(results);
		}
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute SubgroupSettings subgroupSettings,
			BindingResult result) throws NamingException {

		subgroupValidator.validate(subgroupSettings, result);

		if (result.hasErrors()) {
			RipperSettings ripperSettings = ripperSettingsService.create();
			ripperSettings.setProcess(subgroupSettings.getProcess());
			TreeToRulesSettings treeSettings = treeToRulesSettingsService.create();
			treeSettings.setProcess(subgroupSettings.getProcess());

			ModelAndView res = new ModelAndView("process/create");
			res.addObject(RIPPER_SETTINGS, ripperSettings);
			res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
			res.addObject(TREE_SETTINGS, treeSettings);
			res.addObject(ERROR_LOWER_CASE, SUBGROUP_DISCOVERY);

			return res;
		} else {

			Results results = resultsService.calculateSubgroupResults(subgroupSettings);

			return createResultsModelAndView(results);
		}
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute TreeToRulesSettings treeSettings, BindingResult result)
			throws NamingException {

		treeValidator.validate(treeSettings, result);

		if (result.hasErrors()) {
			RipperSettings ripperSettings = ripperSettingsService.create();
			ripperSettings.setProcess(treeSettings.getProcess());
			SubgroupSettings subgroupSettings = subgroupSettingsService.create();
			subgroupSettings.setProcess(treeSettings.getProcess());

			ModelAndView res = new ModelAndView("process/create");
			res.addObject(RIPPER_SETTINGS, ripperSettings);
			res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
			res.addObject(TREE_SETTINGS, treeSettings);
			res.addObject(ERROR_LOWER_CASE, TREE_TO_RULES);

			return res;
		} else {

			Results results = resultsService.calculateTreeToRulesResults(treeSettings);

			return createResultsModelAndView(results);
		}
	}

	public ModelAndView createResultsModelAndView(Results results) {
		Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		XYLineChart chart = RoisinUtils.getAucChart(rules, results.getAuc());

		ModelAndView res = new ModelAndView("results/view");
		res.addObject(RULES_LOWER_CASE, rules);
		res.addObject(REQUEST_URI, "results/view?resultsId=" + results.getId());
		res.addObject(CHART_LOWER_CASE, chart.toURLString());
		res.addObject(RESULTS_LOWER_CASE, results);

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "ripper" })
	public ModelAndView ripperDetails(@RequestParam int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId, RIPPER);
		PreprocessedData data = preprocessedDataService.findOne(dataId);

		ModelAndView res = new ModelAndView("details/ripper");
		res.addObject(PROCESSES_LOWER_CASE, processes);
		res.addObject(DATA_NAME, data.getName());

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "subgroup" })
	public ModelAndView subgroupDetails(@RequestParam int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				SUBGROUP_DISCOVERY);
		PreprocessedData data = preprocessedDataService.findOne(dataId);

		ModelAndView res = new ModelAndView("details/subgroup");
		res.addObject(PROCESSES_LOWER_CASE, processes);
		res.addObject(DATA_NAME, data.getName());

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "tree" })
	public ModelAndView treeDetails(@RequestParam int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				TREE_TO_RULES);
		PreprocessedData data = preprocessedDataService.findOne(dataId);

		ModelAndView res = new ModelAndView("details/tree");
		res.addObject(PROCESSES_LOWER_CASE, processes);
		res.addObject(DATA_NAME, data.getName());

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute PreproSimpleForm form) throws NamingException {

		Process process = processService.createInitialProcessNoAlgorithm(form);

		// Creación de los formularios
		RipperSettings ripperSettings = ripperSettingsService.create();
		ripperSettings.setProcess(process);
		SubgroupSettings subgroupSettings = subgroupSettingsService.create();
		subgroupSettings.setProcess(process);
		TreeToRulesSettings treeToRulesSettings = treeToRulesSettingsService.create();
		treeToRulesSettings.setProcess(process);

		ModelAndView res = new ModelAndView("process/create");
		res.addObject(RIPPER_SETTINGS, ripperSettings);
		res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
		res.addObject(TREE_SETTINGS, treeToRulesSettings);

		return res;
	}
}
