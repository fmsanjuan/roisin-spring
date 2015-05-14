package com.roisin.spring.services;

import static com.roisin.spring.utils.ProcessConstants.RIPPER;
import static com.roisin.spring.utils.ProcessConstants.SUBGROUP_DISCOVERY;
import static com.roisin.spring.utils.ProcessConstants.TREE_TO_RULES;

import java.io.IOException;
import java.util.Collection;

import javax.naming.NamingException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
import com.roisin.spring.model.User;
import com.roisin.spring.repositories.ResultsRepository;
import com.roisin.spring.utils.RoisinUtils;
import com.roisin.spring.utils.Runner;

@Service
@Transactional
public class ResultsService {

	/**
	 * Results service
	 */
	@Autowired
	private transient ResultsRepository resultsRepository;

	/**
	 * Rule service
	 */
	@Autowired
	private transient RuleService ruleService;

	/**
	 * Process service
	 */
	@Autowired
	private transient ProcessService processService;

	/**
	 * Ripper settings service
	 */
	@Autowired
	private transient RipperSettingsService ripperSetService;

	/**
	 * Subgroup settings service
	 */
	@Autowired
	private transient SubgroupSettingsService subgroupSetService;

	/**
	 * Tree to rules settings service
	 */
	@Autowired
	private transient TreeToRulesSettingsService treeSetService;

	/**
	 * File Service
	 */
	@Autowired
	private transient FileService fileService;

	/**
	 * Selected attribute servive
	 */
	@Autowired
	private transient SelectedAttributeService saService;

	/**
	 * Deleted row service
	 */
	@Autowired
	private transient DeletedRowService deletedRowService;

	/**
	 * User service
	 */
	@Autowired
	private transient UserService userService;

	public ResultsService() {
		super();
	}

	public Results create() {
		final Results results = new Results();

		return results;
	}

	public Collection<Results> findAll() {
		return resultsRepository.findAll();
	}

	public Results findOne(final int resultsId) {
		Assert.notNull(resultsId);
		final User principal = userService.findByPrincipal();
		final Results results = resultsRepository.findOne(resultsId);
		boolean isOwner = principal.equals(results.getProcess().getPreprocessedData()
				.getPreprocessingForm().getFile().getUser());
		Assert.isTrue(isOwner);
		return results;
	}

	public Results save(final Results results) {
		return resultsRepository.save(results);
	}

	public void delete(final Results results) {
		Assert.notNull(results);
		final User principal = userService.findByPrincipal();
		boolean isOwner = principal.equals(results.getProcess().getPreprocessedData()
				.getPreprocessingForm().getFile().getUser());
		Assert.isTrue(isOwner);
		resultsRepository.delete(results);
	}

	// Extra methods

	public Results saveResultRules(final RoisinResults roisinResults, final Process process) {
		Results results = create();
		results.setAuc(roisinResults.getRulesAuc());
		results.setProcess(process);
		results = save(results);

		Rule rule;

		for (final RoisinRule roisinRule : roisinResults.getRoisinRules()) {
			rule = ruleService.create();
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

		results = findOne(results.getId());

		return results;
	}

	public ByteArrayOutputStream getExcelResults(final Results results) {

		final Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		final ByteArrayOutputStream document = new ByteArrayOutputStream();

		try {
			final HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Roisin Results");
			worksheet = writeExcelRules(worksheet, rules, results.getAuc(), 1);
			workbook.write(document);
			document.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Imposible obtener el fichero");
		}
		return document;
	}

	public ByteArrayOutputStream getOptimizationExcelResults(final Results results) {

		final Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		final Collection<Rule> removedRules = RoisinUtils.getAucOptimizationRemovedRules(rules);
		rules.removeAll(removedRules);

		final ByteArrayOutputStream document = new ByteArrayOutputStream();

		try {
			int initRow = 1;
			final HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Roisin Results");

			worksheet = writeExcelRules(worksheet, rules, results.getAuc(), initRow);

			initRow += 2;
			final HSSFRow rowTitle = worksheet.createRow(initRow);
			final HSSFCell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellValue("AUC Optimization Removed Rules");

			initRow++;
			worksheet = writeExcelRules(worksheet, removedRules,
					RoisinUtils.calculateRulesAuc(rules), initRow);

			workbook.write(document);
			document.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Imposible obtener el fichero");
		}

		return document;
	}

	public HSSFSheet writeExcelRules(final HSSFSheet worksheet, final Collection<Rule> rules,
			final double auc, int initRow) {
		final HSSFRow row1 = worksheet.createRow(0);

		final HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellValue("Premise");
		final HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellValue("Conclusion");
		final HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellValue("Precision");
		final HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellValue("Support");
		final HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellValue("TPR");
		final HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellValue("FPR");
		final HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellValue("TP");
		final HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellValue("FP");
		final HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellValue("FN");
		final HSSFCell cellJ1 = row1.createCell(9);
		cellJ1.setCellValue("TN");
		final HSSFCell cellK1 = row1.createCell(10);
		cellK1.setCellValue("AUC");
		final HSSFCell cellM1 = row1.createCell(12);
		cellM1.setCellValue("Total AUC");
		final HSSFCell cellN1 = row1.createCell(13);
		cellN1.setCellValue(auc);

		HSSFCell cellA;
		HSSFCell cellB;
		HSSFCell cellC;
		HSSFCell cellD;
		HSSFCell cellE;
		HSSFCell cellF;
		HSSFCell cellG;
		HSSFCell cellH;
		HSSFCell cellI;
		HSSFCell cellJ;
		HSSFCell cellK;
		for (final Rule rule : rules) {
			HSSFRow row = worksheet.createRow(initRow);
			initRow++;

			cellA = row.createCell(0);
			cellA.setCellValue(rule.getPremise());
			cellB = row.createCell(1);
			cellB.setCellValue(rule.getConclusion());
			cellC = row.createCell(2);
			cellC.setCellValue(rule.getRulePrecision());
			cellD = row.createCell(3);
			cellD.setCellValue(rule.getSupport());
			cellE = row.createCell(4);
			cellE.setCellValue(rule.getTpr());
			cellF = row.createCell(5);
			cellF.setCellValue(rule.getFpr());
			cellG = row.createCell(6);
			cellG.setCellValue(rule.getTp());
			cellH = row.createCell(7);
			cellH.setCellValue(rule.getFp());
			cellI = row.createCell(8);
			cellI.setCellValue(rule.getFn());
			cellJ = row.createCell(9);
			cellJ.setCellValue(rule.getTn());
			cellK = row.createCell(10);
			cellK.setCellValue(rule.getAuc());

		}
		return worksheet;
	}

	public Results calculateRipperResults(RipperSettings ripperSettings) throws NamingException {
		// Process
		Process process = ripperSettings.getProcess();
		process = processService.saveProcessAlgorithm(process, RIPPER);
		// Se vuelve a salvar por si se ha creado un proceso nuevo
		ripperSettings.setProcess(process);
		ripperSettings = ripperSetService.save(ripperSettings);
		// Data and form
		final PreprocessedData data = process.getPreprocessedData();
		final PreprocessingForm form = data.getPreprocessingForm();
		// Extracción de file de BD
		final File file = form.getFile();
		final String tmpPath = fileService.writeFileFromDb(file);
		// Getting selected attributes and deleted rows
		Collection<SelectedAttribute> selectedAttributes = saService
				.findSelectedAttributesByFormId(form.getId());
		final Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(form
				.getId());
		// Numerical label
		boolean numericalLabel = RoisinUtils.isNumericLabel(data.getExampleSet().getExampleTable()
				.getAttributes(), process.getLabel().getName());
		// Roisin core is used to get RoisinResults
		RoisinResults roisinResults = Runner.getRipperResults(ripperSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
				.getRowsFromDeletedRows(deletedRows), numericalLabel);
		// Truncate results
		roisinResults.truncateResults();
		final Results results = saveResultRules(roisinResults, process);

		return results;
	}

	public Results calculateSubgroupResults(SubgroupSettings subgroupSettings)
			throws NamingException {
		// Process
		Process process = subgroupSettings.getProcess();
		process = processService.saveProcessAlgorithm(process, SUBGROUP_DISCOVERY);
		// Se vuelve a salvar por si se ha creado un proceso nuevo
		subgroupSettings.setProcess(process);
		subgroupSettings = subgroupSetService.save(subgroupSettings);
		// Data and form
		final PreprocessedData data = process.getPreprocessedData();
		final PreprocessingForm form = data.getPreprocessingForm();
		// Extracción de file de BD
		final File file = form.getFile();
		final String tmpPath = fileService.writeFileFromDb(file);
		// Getting selected attributes and deleted rows
		Collection<SelectedAttribute> selectedAttributes = saService
				.findSelectedAttributesByFormId(form.getId());
		final Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(form
				.getId());
		// Roisin core is used to get RoisinResults
		RoisinResults roisinResults = Runner.getSubgroupResults(subgroupSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
				.getRowsFromDeletedRows(deletedRows));
		// Truncate results
		roisinResults.truncateResults();
		final Results results = saveResultRules(roisinResults, process);

		return results;
	}

	public Results calculateTreeToRulesResults(TreeToRulesSettings treeSettings)
			throws NamingException {

		// Process
		Process process = treeSettings.getProcess();
		process = processService.saveProcessAlgorithm(process, TREE_TO_RULES);
		// Se vuelve a salvar por si se ha creado un proceso nuevo
		treeSettings.setProcess(process);
		treeSettings = treeSetService.save(treeSettings);
		// Data and form
		final PreprocessedData data = process.getPreprocessedData();
		final PreprocessingForm form = data.getPreprocessingForm();
		// Extracción de file de BD
		final File file = form.getFile();
		final String tmpPath = fileService.writeFileFromDb(file);
		// Getting selected attributes and deleted rows
		Collection<SelectedAttribute> selectedAttributes = saService
				.findSelectedAttributesByFormId(form.getId());
		final Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(form
				.getId());
		// Numerical label
		boolean numericalLabel = RoisinUtils.isNumericLabel(data.getExampleSet().getExampleTable()
				.getAttributes(), process.getLabel().getName());
		// Roisin core is used to get RoisinResults
		RoisinResults roisinResults = Runner.getTreeToRulesResults(treeSettings, tmpPath, process
				.getLabel().getName(), form.getFilterCondition(), RoisinUtils
				.getAttributesFromSelectedAttributes(selectedAttributes), RoisinUtils
				.getRowsFromDeletedRows(deletedRows), numericalLabel);
		// Truncate results
		roisinResults.truncateResults();
		final Results results = saveResultRules(roisinResults, process);

		return results;
	}
}
