package com.roisin.spring.controllers;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.charts4j.XYLineChart;
import com.roisin.core.results.RoisinResults;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.File;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.Process;
import com.roisin.spring.model.Results;
import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.model.Rule;
import com.roisin.spring.model.SelectedAttribute;
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
import com.roisin.spring.utils.ProcessConstants;
import com.roisin.spring.utils.RoisinUtils;
import com.roisin.spring.utils.Runner;
import com.roisin.spring.validator.RipperSettingsValidator;
import com.roisin.spring.validator.SubgroupSettingsValidator;
import com.roisin.spring.validator.TreeSettingsValidator;

@Controller
@RequestMapping("/process")
public class ProcessController {

	private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

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
	public ModelAndView ripper(@ModelAttribute RipperSettings ripperSettings, BindingResult result) {

		ripperValidator.validate(ripperSettings, result);

		if (result.hasErrors()) {
			SubgroupSettings subgroupSettings = subgroupSettingsService.create();
			subgroupSettings.setProcess(ripperSettings.getProcess());
			TreeToRulesSettings treeSettings = treeToRulesSettingsService.create();
			treeSettings.setProcess(ripperSettings.getProcess());

			ModelAndView res = new ModelAndView("process/create");
			res.addObject("ripperSettings", ripperSettings);
			res.addObject("subgroupSettings", subgroupSettings);
			res.addObject("treeSettings", treeSettings);
			res.addObject("error", ProcessConstants.RIPPER);

			return res;

		} else {
			// Process
			Process process = ripperSettings.getProcess();
			process = processService.saveProcessAlgorithm(process, ProcessConstants.RIPPER);
			// Se vuelve a salvar por si se ha creado un proceso nuevo
			ripperSettings.setProcess(process);
			ripperSettings = ripperSettingsService.save(ripperSettings);
			// Data and form
			PreprocessedData data = process.getPreprocessedData();
			PreprocessingForm form = data.getPreprocessingForm();
			// Extracción de file de BD
			File file = form.getFile();
			String tmpPath = fileService.writeFileFromDb(file);
			// Getting selected attributes and deleted rows
			Collection<SelectedAttribute> selectedAttributes = selectedAttributeService
					.findSelectedAttributesByFormId(form.getId());
			Collection<DeletedRow> deletedRows = deletedRowService
					.findFormDeletedRows(form.getId());
			// Numerical label
			boolean numericalLabel = RoisinUtils.isNumericLabel(data.getExampleSet()
					.getExampleTable().getAttributes(), process.getLabel().getName());
			// Roisin core is used to get RoisinResults
			RoisinResults roisinResults = Runner.getRipperResults(ripperSettings, tmpPath, process
					.getLabel().getName(), form.getFilterCondition(), RoisinUtils
					.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
					.getRowsFromDeletedRows(deletedRows), numericalLabel);
			// Truncate results
			roisinResults.truncateResults();
			Results results = resultsService.saveResultRules(roisinResults, process);

			return createResultsModelAndView(results);
		}
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute SubgroupSettings subgroupSettings,
			BindingResult result) {

		subgroupValidator.validate(subgroupSettings, result);

		if (result.hasErrors()) {
			RipperSettings ripperSettings = ripperSettingsService.create();
			ripperSettings.setProcess(subgroupSettings.getProcess());
			TreeToRulesSettings treeSettings = treeToRulesSettingsService.create();
			treeSettings.setProcess(subgroupSettings.getProcess());

			ModelAndView res = new ModelAndView("process/create");
			res.addObject("ripperSettings", ripperSettings);
			res.addObject("subgroupSettings", subgroupSettings);
			res.addObject("treeSettings", treeSettings);
			res.addObject("error", ProcessConstants.SUBGROUP_DISCOVERY);

			return res;
		} else {
			// Process
			Process process = subgroupSettings.getProcess();
			process = processService.saveProcessAlgorithm(process,
					ProcessConstants.SUBGROUP_DISCOVERY);
			// Se vuelve a salvar por si se ha creado un proceso nuevo
			subgroupSettings.setProcess(process);
			subgroupSettings = subgroupSettingsService.save(subgroupSettings);
			// Data and form
			PreprocessedData data = process.getPreprocessedData();
			PreprocessingForm form = data.getPreprocessingForm();
			// Extracción de file de BD
			File file = form.getFile();
			String tmpPath = fileService.writeFileFromDb(file);
			// Getting selected attributes and deleted rows
			Collection<SelectedAttribute> selectedAttributes = selectedAttributeService
					.findSelectedAttributesByFormId(form.getId());
			Collection<DeletedRow> deletedRows = deletedRowService
					.findFormDeletedRows(form.getId());
			// Roisin core is used to get RoisinResults
			RoisinResults roisinResults = Runner.getSubgroupResults(subgroupSettings, tmpPath,
					process.getLabel().getName(), form.getFilterCondition(),
					RoisinUtils.getAttributesFromSelectedAttributes(selectedAttributes),
					RoisinUtils.getRowsFromDeletedRows(deletedRows));
			// Truncate results
			roisinResults.truncateResults();
			Results results = resultsService.saveResultRules(roisinResults, process);

			return createResultsModelAndView(results);
		}
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute TreeToRulesSettings treeSettings, BindingResult result) {

		treeValidator.validate(treeSettings, result);

		if (result.hasErrors()) {
			RipperSettings ripperSettings = ripperSettingsService.create();
			ripperSettings.setProcess(treeSettings.getProcess());
			SubgroupSettings subgroupSettings = subgroupSettingsService.create();
			subgroupSettings.setProcess(treeSettings.getProcess());

			ModelAndView res = new ModelAndView("process/create");
			res.addObject("ripperSettings", ripperSettings);
			res.addObject("subgroupSettings", subgroupSettings);
			res.addObject("treeSettings", treeSettings);
			res.addObject("error", ProcessConstants.TREE_TO_RULES);

			return res;
		} else {

			// Process
			Process process = treeSettings.getProcess();
			process = processService.saveProcessAlgorithm(process, ProcessConstants.TREE_TO_RULES);
			// Se vuelve a salvar por si se ha creado un proceso nuevo
			treeSettings.setProcess(process);
			treeSettings = treeToRulesSettingsService.save(treeSettings);
			// Data and form
			PreprocessedData data = process.getPreprocessedData();
			PreprocessingForm form = data.getPreprocessingForm();
			// Extracción de file de BD
			File file = form.getFile();
			String tmpPath = fileService.writeFileFromDb(file);
			// Getting selected attributes and deleted rows
			Collection<SelectedAttribute> selectedAttributes = selectedAttributeService
					.findSelectedAttributesByFormId(form.getId());
			Collection<DeletedRow> deletedRows = deletedRowService
					.findFormDeletedRows(form.getId());
			// Numerical label
			boolean numericalLabel = RoisinUtils.isNumericLabel(data.getExampleSet()
					.getExampleTable().getAttributes(), process.getLabel().getName());
			// Roisin core is used to get RoisinResults
			RoisinResults roisinResults = Runner.getTreeToRulesResults(treeSettings, tmpPath,
					process.getLabel().getName(), form.getFilterCondition(),
					RoisinUtils.getAttributesFromSelectedAttributes(selectedAttributes),
					RoisinUtils.getRowsFromDeletedRows(deletedRows), numericalLabel);
			// Truncate results
			roisinResults.truncateResults();
			Results results = resultsService.saveResultRules(roisinResults, process);

			return createResultsModelAndView(results);
		}
	}

	public ModelAndView createResultsModelAndView(Results results) {
		Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		XYLineChart chart = RoisinUtils.getAucChart(rules, results.getAuc());

		ModelAndView res = new ModelAndView("results/view");
		res.addObject("rules", rules);
		res.addObject("requestURI", "results/view?resultsId=" + results.getId());
		res.addObject("chart", chart.toURLString());
		res.addObject("results", results);

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "ripper" })
	public ModelAndView ripperDetails(@RequestParam int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				ProcessConstants.RIPPER);
		PreprocessedData data = preprocessedDataService.findOne(dataId);

		ModelAndView res = new ModelAndView("details/ripper");
		res.addObject("processes", processes);
		res.addObject("dataName", data.getName());

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "subgroup" })
	public ModelAndView subgroupDetails(@RequestParam int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				ProcessConstants.SUBGROUP_DISCOVERY);
		PreprocessedData data = preprocessedDataService.findOne(dataId);

		ModelAndView res = new ModelAndView("details/subgroup");
		res.addObject("processes", processes);
		res.addObject("dataName", data.getName());

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = { "tree" })
	public ModelAndView treeDetails(@RequestParam int dataId) {

		Collection<Process> processes = processService.findByAlgorithmAndDataId(dataId,
				ProcessConstants.TREE_TO_RULES);
		PreprocessedData data = preprocessedDataService.findOne(dataId);

		ModelAndView res = new ModelAndView("details/tree");
		res.addObject("processes", processes);
		res.addObject("dataName", data.getName());

		return res;
	}
}
