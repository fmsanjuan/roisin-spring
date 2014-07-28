package com.roisin.spring.controllers;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.roisin.spring.services.ProcessService;
import com.roisin.spring.services.ResultsService;
import com.roisin.spring.services.RuleService;
import com.roisin.spring.services.SelectedAttributeService;
import com.roisin.spring.utils.ProcessConstants;
import com.roisin.spring.utils.RoisinUtils;
import com.roisin.spring.utils.Runner;

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
	RuleService ruleService;

	@RequestMapping(value = "/ripper", method = RequestMethod.POST)
	public ModelAndView ripper(@ModelAttribute RipperSettings ripperSettings) {

		// Process
		Process process = ripperSettings.getProcess();
		process = processService.saveProcessAlgorithm(process, ProcessConstants.RIPPER);
		// Data and form
		PreprocessedData data = process.getPreprocessedData();
		PreprocessingForm form = data.getPreprocessingForm();
		// Extracción de file de BD
		File file = form.getFile();
		String tmpPath = fileService.writeFileFromDb(file);
		// Getting selected attributes and deleted rows
		Collection<SelectedAttribute> selectedAttributes = selectedAttributeService
				.findSelectedAttributesByFormId(form.getId());
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(form.getId());
		// Roisin core is used to get RoisinResults
		RoisinResults roisinResults = Runner.getRipperResults(ripperSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
				.getRowsFromDeletedRows(deletedRows));
		// Truncate results
		roisinResults.truncateResults();
		Results results = resultsService.saveResultRules(roisinResults);
		process.setResults(results);

		return createResultsModelAndView(results);
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute SubgroupSettings subgroupSettings) {

		// Process
		Process process = subgroupSettings.getProcess();
		process = processService.saveProcessAlgorithm(process, ProcessConstants.SUBGROUP_DISCOVERY);
		// Data and form
		PreprocessedData data = process.getPreprocessedData();
		PreprocessingForm form = data.getPreprocessingForm();
		// Extracción de file de BD
		File file = form.getFile();
		String tmpPath = fileService.writeFileFromDb(file);
		// Getting selected attributes and deleted rows
		Collection<SelectedAttribute> selectedAttributes = selectedAttributeService
				.findSelectedAttributesByFormId(form.getId());
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(form.getId());
		// Roisin core is used to get RoisinResults
		RoisinResults roisinResults = Runner.getSubgroupResults(subgroupSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
				.getRowsFromDeletedRows(deletedRows));
		// Truncate results
		roisinResults.truncateResults();
		Results results = resultsService.saveResultRules(roisinResults);
		process.setResults(results);

		return createResultsModelAndView(results);
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute TreeToRulesSettings treeSettings) {
		// Process
		Process process = treeSettings.getProcess();
		process = processService.saveProcessAlgorithm(process, ProcessConstants.TREE_TO_RULES);
		// Data and form
		PreprocessedData data = process.getPreprocessedData();
		PreprocessingForm form = data.getPreprocessingForm();
		// Extracción de file de BD
		File file = form.getFile();
		String tmpPath = fileService.writeFileFromDb(file);
		// Getting selected attributes and deleted rows
		Collection<SelectedAttribute> selectedAttributes = selectedAttributeService
				.findSelectedAttributesByFormId(form.getId());
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(form.getId());
		// Roisin core is used to get RoisinResults
		RoisinResults roisinResults = Runner.getTreeToRulesResults(treeSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
				.getRowsFromDeletedRows(deletedRows));
		// Truncate results
		roisinResults.truncateResults();
		Results results = resultsService.saveResultRules(roisinResults);
		process.setResults(results);

		return createResultsModelAndView(results);
	}

	public ModelAndView createResultsModelAndView(Results results) {
		Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		XYLineChart chart = RoisinUtils.getAucChart(rules, results.getAuc());

		ModelAndView res = new ModelAndView("results/view");
		res.addObject("rules", rules);
		res.addObject("requestURI", "results/view");
		res.addObject("chart", chart.toURLString());
		res.addObject("results", results);

		return res;
	}

}
