package com.roisin.spring.controllers;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.RoisinRule;
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
import com.roisin.spring.services.ProcessService;
import com.roisin.spring.services.ResultsService;
import com.roisin.spring.services.RuleService;
import com.roisin.spring.services.SelectedAttributeService;
import com.roisin.spring.utils.Constants;
import com.roisin.spring.utils.FileUtils;
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
	private RuleService ruleService;

	@Autowired
	private SelectedAttributeService selectedAttributeService;

	@Autowired
	private DeletedRowService deletedRowService;

	@RequestMapping(value = "/ripper", method = RequestMethod.POST)
	public ModelAndView ripper(@ModelAttribute RipperSettings ripperSettings) {

		Process process = ripperSettings.getProcess();
		process.setAlgorithm("ripper");
		process = processService.save(process);

		PreprocessedData data = process.getPreprocessedData();

		PreprocessingForm form = data.getPreprocessingForm();

		// Extracción de file de BD
		File file = form.getFile();
		byte[] fileArray = file.getOriginalFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
		String tmpPath = Constants.STORAGE_PATH + file.getHash() + Constants.DOT_SYMBOL
				+ fileFormat;
		// Escritura en disco del fichero
		FileUtils.writeFileFromByteArray(fileArray, tmpPath);

		Collection<SelectedAttribute> selectedAttributes = selectedAttributeService
				.findSelectedAttributesByFormId(form.getId());
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(form.getId());

		RoisinResults roisinResults = Runner.getRipperResults(ripperSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
				.getRowsFromDeletedRows(deletedRows));

		Results results = resultsService.create();
		results.setAuc(roisinResults.getRulesAuc());
		results = resultsService.save(results);

		for (RoisinRule roisinRule : roisinResults.getRoisinRules()) {
			Rule rule = ruleService.create();
			rule.setAuc(roisinRule.getAuc());
			rule.setPremise(roisinRule.getPremise());
			rule.setConclusion(roisinRule.getConclusion());
			rule.setTp(roisinRule.getTruePositives());
			rule.setTn(roisinRule.getTrueNegatives());
			rule.setFp(roisinRule.getFalsePositives());
			rule.setFn(roisinRule.getFalseNegatives());
			rule.setTpr(roisinRule.getTruePositiveRate());
			rule.setFpr(roisinRule.getFalsePositiveRate());
			rule.setRulePrecision(roisinRule.getPrecision());
			rule.setSupport(roisinRule.getSupport());
			rule.setResults(results);
			ruleService.save(rule);
		}

		Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());

		ModelAndView res = new ModelAndView("results/view");
		res.addObject("rules", rules);
		res.addObject("requestURI", "results/view");

		return res;
	}

	@RequestMapping(value = "/subgroup", method = RequestMethod.POST)
	public ModelAndView subgroup(@ModelAttribute SubgroupSettings subgroupSettings) {

		Process process = subgroupSettings.getProcess();
		process.setAlgorithm("subgroupDiscovery");
		process = processService.save(process);

		PreprocessedData data = process.getPreprocessedData();

		PreprocessingForm form = data.getPreprocessingForm();

		// Extracción de file de BD
		File file = form.getFile();
		byte[] fileArray = file.getOriginalFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
		String tmpPath = Constants.STORAGE_PATH + file.getHash() + Constants.DOT_SYMBOL
				+ fileFormat;
		// Escritura en disco del fichero
		FileUtils.writeFileFromByteArray(fileArray, tmpPath);

		RoisinResults roisinResults = Runner.getSubgroupResults(subgroupSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(form.getSelectedAttributes()), RoisinUtils
				.getRowsFromDeletedRows(form.getDeletedRows()));

		Results results = resultsService.create();
		results.setAuc(roisinResults.getRulesAuc());
		results = resultsService.save(results);

		for (RoisinRule roisinRule : roisinResults.getRoisinRules()) {
			Rule rule = ruleService.create();
			rule.setAuc(roisinRule.getAuc());
			rule.setPremise(roisinRule.getPremise());
			rule.setConclusion(roisinRule.getConclusion());
			rule.setTp(roisinRule.getTruePositives());
			rule.setTn(roisinRule.getTrueNegatives());
			rule.setFp(roisinRule.getFalsePositives());
			rule.setFn(roisinRule.getFalseNegatives());
			rule.setTpr(roisinRule.getTruePositiveRate());
			rule.setFpr(roisinRule.getFalsePositiveRate());
			rule.setRulePrecision(roisinRule.getPrecision());
			rule.setSupport(roisinRule.getSupport());
			rule.setResults(results);
			ruleService.save(rule);
		}

		results = resultsService.findOne(results.getId());

		ModelAndView res = new ModelAndView("results/view");
		res.addObject("rules", results.getRules());
		res.addObject("requestURI", "results/view");

		return res;
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView tree(@ModelAttribute TreeToRulesSettings treeSettings) {

		Process process = treeSettings.getProcess();
		process.setAlgorithm("treeToRules");
		process = processService.save(process);

		PreprocessedData data = process.getPreprocessedData();

		PreprocessingForm form = data.getPreprocessingForm();

		// Extracción de file de BD
		File file = form.getFile();
		byte[] fileArray = file.getOriginalFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
		String tmpPath = Constants.STORAGE_PATH + file.getHash() + Constants.DOT_SYMBOL
				+ fileFormat;
		// Escritura en disco del fichero
		FileUtils.writeFileFromByteArray(fileArray, tmpPath);

		RoisinResults roisinResults = Runner.getTreeToRulesResults(treeSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(form.getSelectedAttributes()), RoisinUtils
				.getRowsFromDeletedRows(form.getDeletedRows()));

		Results results = resultsService.create();
		results.setAuc(roisinResults.getRulesAuc());
		results = resultsService.save(results);

		for (RoisinRule roisinRule : roisinResults.getRoisinRules()) {
			Rule rule = ruleService.create();
			rule.setAuc(roisinRule.getAuc());
			rule.setPremise(roisinRule.getPremise());
			rule.setConclusion(roisinRule.getConclusion());
			rule.setTp(roisinRule.getTruePositives());
			rule.setTn(roisinRule.getTrueNegatives());
			rule.setFp(roisinRule.getFalsePositives());
			rule.setFn(roisinRule.getFalseNegatives());
			rule.setTpr(roisinRule.getTruePositiveRate());
			rule.setFpr(roisinRule.getFalsePositiveRate());
			rule.setRulePrecision(roisinRule.getPrecision());
			rule.setSupport(roisinRule.getSupport());
			rule.setResults(results);
			ruleService.save(rule);
		}

		results = resultsService.findOne(results.getId());

		ModelAndView res = new ModelAndView("results/view");
		res.addObject("rules", results.getRules());
		res.addObject("requestURI", "results/view");

		return res;
	}

	@RequestMapping(value = "/prueba", method = RequestMethod.GET)
	public ModelAndView prueba() {

		logger.info("HOLA");

		ModelAndView res = new ModelAndView("results/view");

		return res;
	}
}
