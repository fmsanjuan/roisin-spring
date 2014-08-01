package com.roisin.spring.services;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.RoisinRule;
import com.roisin.spring.model.Process;
import com.roisin.spring.model.Results;
import com.roisin.spring.model.Rule;
import com.roisin.spring.repositories.ResultsRepository;
import com.roisin.spring.utils.RoisinUtils;

@Service
@Transactional
public class ResultsService {

	@Autowired
	private ResultsRepository resultsRepository;

	@Autowired
	private RuleService ruleService;

	public ResultsService() {
		super();
	}

	public Results create() {
		Results results = new Results();

		return results;
	}

	public Collection<Results> findAll() {
		return resultsRepository.findAll();
	}

	public Results findOne(int resultsId) {
		return resultsRepository.findOne(resultsId);
	}

	public Results save(Results results) {
		return resultsRepository.save(results);
	}

	public void delete(Results results) {
		resultsRepository.delete(results);
	}

	// Extra methods

	public Results saveResultRules(RoisinResults roisinResults, Process process) {
		Results results = create();
		results.setAuc(roisinResults.getRulesAuc());
		results.setProcess(process);
		results = save(results);

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

		results = findOne(results.getId());

		return results;
	}

	public ByteArrayOutputStream getExcelResults(Results results) {

		Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		ByteArrayOutputStream document = new ByteArrayOutputStream();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Roisin Results");
			worksheet = writeExcelRules(worksheet, rules, results.getAuc(), 1);
			workbook.write(document);
			document.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Imposible obtener el fichero");
		}
		return document;
	}

	public ByteArrayOutputStream getOptimizationExcelResults(Results results) {

		Collection<Rule> rules = ruleService.findRulesByResultsId(results.getId());
		Collection<Rule> removedRules = RoisinUtils.getAucOptimizationRemovedRules(rules);
		rules.removeAll(removedRules);

		ByteArrayOutputStream document = new ByteArrayOutputStream();

		try {
			int initRow = 1;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Roisin Results");

			worksheet = writeExcelRules(worksheet, rules, results.getAuc(), initRow);

			initRow += 2;
			HSSFRow rowTitle = worksheet.createRow(initRow);
			HSSFCell cellTitle = rowTitle.createCell(0);
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

	public HSSFSheet writeExcelRules(HSSFSheet worksheet, Collection<Rule> rules, double auc,
			int initRow) {
		HSSFRow row1 = worksheet.createRow(0);

		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellValue("Premise");
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellValue("Conclusion");
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellValue("Precision");
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellValue("Support");
		HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellValue("TPR");
		HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellValue("FPR");
		HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellValue("TP");
		HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellValue("FP");
		HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellValue("FN");
		HSSFCell cellJ1 = row1.createCell(9);
		cellJ1.setCellValue("TN");
		HSSFCell cellK1 = row1.createCell(10);
		cellK1.setCellValue("AUC");
		HSSFCell cellM1 = row1.createCell(12);
		cellM1.setCellValue("Total AUC");
		HSSFCell cellN1 = row1.createCell(13);
		cellN1.setCellValue(auc);

		for (Rule rule : rules) {
			HSSFRow row = worksheet.createRow(initRow);
			initRow++;

			HSSFCell cellA = row.createCell(0);
			cellA.setCellValue(rule.getPremise());
			HSSFCell cellB = row.createCell(1);
			cellB.setCellValue(rule.getConclusion());
			HSSFCell cellC = row.createCell(2);
			cellC.setCellValue(rule.getRulePrecision());
			HSSFCell cellD = row.createCell(3);
			cellD.setCellValue(rule.getSupport());
			HSSFCell cellE = row.createCell(4);
			cellE.setCellValue(rule.getTpr());
			HSSFCell cellF = row.createCell(5);
			cellF.setCellValue(rule.getFpr());
			HSSFCell cellG = row.createCell(6);
			cellG.setCellValue(rule.getTp());
			HSSFCell cellH = row.createCell(7);
			cellH.setCellValue(rule.getFp());
			HSSFCell cellI = row.createCell(8);
			cellI.setCellValue(rule.getFn());
			HSSFCell cellJ = row.createCell(9);
			cellJ.setCellValue(rule.getTn());
			HSSFCell cellK = row.createCell(10);
			cellK.setCellValue(rule.getAuc());

		}
		return worksheet;
	}
}
