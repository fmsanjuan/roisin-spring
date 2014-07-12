package com.roisin.spring.services;

import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.RoisinRule;
import com.roisin.spring.forms.PreprocessingForm;
import com.roisin.spring.utils.ProcessConstants;

@Service
public class ResultsService {

	private static final Logger logger = LoggerFactory.getLogger(ResultsService.class);

	@Autowired
	private ProcessingService processingService;

	public ByteArrayOutputStream getExcelResults(PreprocessingForm form) {

		RoisinResults results;

		if (form.getAlgorithm().equals(ProcessConstants.RIPPER)) {
			results = processingService.getRipperResults(form);
		} else if (form.getAlgorithm().equals(ProcessConstants.SUBGROUP_DISCOVERY)) {
			results = processingService.getSubgroupResults(form);
		} else if (form.getAlgorithm().equals(ProcessConstants.TREE_TO_RULES)) {
			results = processingService.getTreeToRulesResults(form);
		} else {
			throw new IllegalArgumentException("Imposible obtener el fichero");
		}

		ByteArrayOutputStream document = new ByteArrayOutputStream();

		try {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Roisin Results");
			// index from 0,0... cell A1 is cell(0,0)
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

			for (RoisinRule rule : results.getRoisinRules()) {
				HSSFRow row = worksheet.createRow(results.getRoisinRules().indexOf(rule) + 1);

				HSSFCell cellA = row.createCell(0);
				cellA.setCellValue(rule.getPremise());
				HSSFCell cellB = row.createCell(1);
				cellB.setCellValue(rule.getConclusion());
				HSSFCell cellC = row.createCell(2);
				cellC.setCellValue(rule.getPrecision());
				HSSFCell cellD = row.createCell(3);
				cellD.setCellValue(rule.getSupport());
				HSSFCell cellE = row.createCell(4);
				cellE.setCellValue(rule.getTruePositiveRate());
				HSSFCell cellF = row.createCell(5);
				cellF.setCellValue(rule.getFalsePositiveRate());
				HSSFCell cellG = row.createCell(6);
				cellG.setCellValue(rule.getTruePositives());
				HSSFCell cellH = row.createCell(7);
				cellH.setCellValue(rule.getFalsePositives());
				HSSFCell cellI = row.createCell(8);
				cellI.setCellValue(rule.getFalseNegatives());
				HSSFCell cellJ = row.createCell(9);
				cellJ.setCellValue(rule.getTrueNegatives());
				HSSFCell cellK = row.createCell(10);
				cellK.setCellValue(rule.getAuc());
			}

			workbook.write(document);
			document.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Imposible obtener el fichero");
		}

		return document;
	}

}
