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
import com.roisin.spring.services.PreprocessedDataService;
import com.roisin.spring.services.ProcessService;
import com.roisin.spring.services.ResultsService;
import com.roisin.spring.services.RipperSettingsService;
import com.roisin.spring.services.RuleService;
import com.roisin.spring.services.SubgroupSettingsService;
import com.roisin.spring.services.TreeToRulesSettingsService;
import com.roisin.spring.utils.RoisinUtils;
import com.roisin.spring.validator.RipperSettingsValidator;
import com.roisin.spring.validator.SubgroupSettingsValidator;
import com.roisin.spring.validator.TreeSettingsValidator;

@Controller
@RequestMapping("/process")
public class ProcessController {

	/**
	 * Process service
	 */
	@Autowired
	private transient ProcessService processService;

	/**
	 * Results service
	 */
	@Autowired
	private transient ResultsService resultsService;

	/**
	 * Ripper settings service
	 */
	@Autowired
	private transient RipperSettingsService ripperConfService;

	/**
	 * Subgroup settings service
	 */
	@Autowired
	private transient SubgroupSettingsService subgroupConfService;

	/**
	 * Tree to rules settings service
	 */
	@Autowired
	private transient TreeToRulesSettingsService treeConfService;

	/**
	 * Preprocessed data service
	 */
	@Autowired
	private transient PreprocessedDataService preproDataService;

	/**
	 * Rule service
	 */
	@Autowired
	private transient RuleService ruleService;

	/**
	 * Ripper settings validator
	 */
	@Autowired
	private transient RipperSettingsValidator ripperValidator;

	/**
	 * Subgroup settings validator
	 */
	@Autowired
	private transient SubgroupSettingsValidator subgroupValidator;

	/**
	 * Tree settings validator
	 */
	@Autowired
	private transient TreeSettingsValidator treeValidator;

	@RequestMapping(value = "/ripper", method = RequestMethod.POST)
	public ModelAndView ripper(@ModelAttribute final RipperSettings ripperSettings,
			final BindingResult result) throws NamingException {

		ripperValidator.validate(ripperSettings, result);

		ModelAndView res;

		if (result.hasErrors()) {
			final SubgroupSettings subgroupSettings = subgroupConfService.create();
			subgroupSettings.setProcess(ripperSettings.getProcess());
			final TreeToRulesSettings treeSettings = treeConfService.create();
			treeSettings.setProcess(ripperSettings.getProcess());

			res = new ModelAndView("process/create");
			res.addObject(RIPPER_SETTINGS, ripperSettings);
			res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
			res.addObject(TREE_SETTINGS, treeSettings);
			res.addObject(ERROR_LOWER_CASE, RIPPER);
		} else {
			final Results results = resultsService.calculateRipperResults(ripperSettings);
			res = createResultsModelAndView(results);
		}

		return res;
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute final SubgroupSettings subgroupSettings,
			final BindingResult result) throws NamingException {

		subgroupValidator.validate(subgroupSettings, result);

		ModelAndView res;

		if (result.hasErrors()) {
			final RipperSettings ripperSettings = ripperConfService.create();
			ripperSettings.setProcess(subgroupSettings.getProcess());
			final TreeToRulesSettings treeSettings = treeConfService.create();
			treeSettings.setProcess(subgroupSettings.getProcess());

			res = new ModelAndView("process/create");
			res.addObject(RIPPER_SETTINGS, ripperSettings);
			res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
			res.addObject(TREE_SETTINGS, treeSettings);
			res.addObject(ERROR_LOWER_CASE, SUBGROUP_DISCOVERY);
		} else {
			final Results results = resultsService.calculateSubgroupResults(subgroupSettings);
			res = createResultsModelAndView(results);
		}
		return res;
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute final TreeToRulesSettings treeSettings,
			final BindingResult result) throws NamingException {

		treeValidator.validate(treeSettings, result);

		ModelAndView res;

		if (result.hasErrors()) {
			final RipperSettings ripperSettings = ripperConfService.create();
			ripperSettings.setProcess(treeSettings.getProcess());
			final SubgroupSettings subgroupSettings = subgroupConfService.create();
			subgroupSettings.setProcess(treeSettings.getProcess());

			res = new ModelAndView("process/create");
			res.addObject(RIPPER_SETTINGS, ripperSettings);
			res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
			res.addObject(TREE_SETTINGS, treeSettings);
			res.addObject(ERROR_LOWER_CASE, TREE_TO_RULES);
		} else {
			final Results results = resultsService.calculateTreeToRulesResults(treeSettings);
			res = createResultsModelAndView(results);
		}

		return res;
	}

	public ModelAndView createResultsModelAndView(final Results results) {
		final Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		final XYLineChart chart = RoisinUtils.getAucChart(rules, results.getAuc());

		final ModelAndView res = new ModelAndView("results/view");
		res.addObject(RULES_LOWER_CASE, rules);
		res.addObject(REQUEST_URI, "results/view?resultsId=" + results.getId());
		res.addObject(CHART_LOWER_CASE, chart.toURLString());
		res.addObject(RESULTS_LOWER_CASE, results);

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "ripper" })
	public ModelAndView ripperDetails(@RequestParam final int dataId) {

		final Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				RIPPER);
		final PreprocessedData data = preproDataService.findOne(dataId);

		final ModelAndView res = new ModelAndView("details/ripper");
		res.addObject(PROCESSES_LOWER_CASE, processes);
		res.addObject(DATA_NAME, data.getName());

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "subgroup" })
	public ModelAndView subgroupDetails(@RequestParam final int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				SUBGROUP_DISCOVERY);
		final PreprocessedData data = preproDataService.findOne(dataId);

		final ModelAndView res = new ModelAndView("details/subgroup");
		res.addObject(PROCESSES_LOWER_CASE, processes);
		res.addObject(DATA_NAME, data.getName());

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "tree" })
	public ModelAndView treeDetails(@RequestParam final int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				TREE_TO_RULES);
		final PreprocessedData data = preproDataService.findOne(dataId);

		final ModelAndView res = new ModelAndView("details/tree");
		res.addObject(PROCESSES_LOWER_CASE, processes);
		res.addObject(DATA_NAME, data.getName());

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute final PreproSimpleForm form) throws NamingException {

		final Process process = processService.createInitialProcessNoAlgorithm(form);

		// Creación de los formularios
		final RipperSettings ripperSettings = ripperConfService.create();
		ripperSettings.setProcess(process);
		final SubgroupSettings subgroupSettings = subgroupConfService.create();
		subgroupSettings.setProcess(process);
		final TreeToRulesSettings treeSettings = treeConfService.create();
		treeSettings.setProcess(process);

		final ModelAndView res = new ModelAndView("process/create");
		res.addObject(RIPPER_SETTINGS, ripperSettings);
		res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
		res.addObject(TREE_SETTINGS, treeSettings);

		return res;
	}
}
