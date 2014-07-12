package com.roisin.spring.services;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.XYLine;
import com.googlecode.charts4j.XYLineChart;
import com.rapidminer.Process;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.operator.learner.subgroups.RuleSet;
import com.roisin.core.processes.GenericProcesses;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.RoisinRule;
import com.roisin.core.results.SubgroupResults;
import com.roisin.spring.forms.PreprocessingForm;
import com.roisin.spring.utils.Constants;

@Service
public class ProcessingService {

	private static final Logger logger = LoggerFactory.getLogger(ProcessingService.class);

	public RoisinResults getRipperResults(PreprocessingForm form) {
		RipperResults results = null;
		try {
			Process process = GenericProcesses.getRipper(form.getFilePath(), form.getLabel(), form
					.getDeletedRows(), form.getFilterCondition(), form.getAttributeSelection(),
					form.getRipperCriterion(), form.getSampleRatio().toString(), form.getPureness()
							.toString(), form.getMinimalPruneBenefit().toString());
			IOContainer container = process.run();
			RuleModel ruleModel = (RuleModel) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			logger.error("No ha sido posible ejecutar el proceso con Ripper");
		}
		return results;
	}

	public RoisinResults getSubgroupResults(PreprocessingForm form) {
		SubgroupResults results = null;
		try {
			Process process = GenericProcesses.getSubgroupDiscoveryDiscretization(StringUtils
					.substringAfterLast(form.getFilePath(), Constants.DOT_SYMBOL), form
					.getFilePath(), form.getLabel(), form.getDeletedRows(), form
					.getFilterCondition(), form.getAttributeSelection(), form.getMode(), form
					.getUtilityFunction(), form.getMinUtility().toString(), form.getkBestRules()
					.toString(), form.getRuleGeneration().toString(),
					form.getMaxDepth().toString(), form.getMinCoverage().toString());
			IOContainer container = process.run();
			RuleSet ruleModel = (RuleSet) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new SubgroupResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			logger.error("No ha sido posible ejecutar el proceso con Subgroup Discovery");
		}
		return results;
	}

	public RoisinResults getTreeToRulesResults(PreprocessingForm form) {
		RipperResults results = null;
		try {
			Process process = GenericProcesses.getDecisionTreeToRules(StringUtils
					.substringAfterLast(form.getFilePath(), Constants.DOT_SYMBOL), form
					.getFilePath(), form.getLabel(), form.getDeletedRows(), form
					.getFilterCondition(), form.getAttributeSelection(), form
					.getTree2RulesCriterion(), form.getMinimalSizeForSplit().toString(), form
					.getMinimalLeafSize().toString(), form.getMinimalGain().toString(), form
					.getMaximalDepth().toString(), form.getConfidence().toString(), form
					.getNumberOfPrepruningAlternatives().toString(), form.getNoPrepruning()
					.toString(), form.getNoPruning().toString());
			IOContainer container = process.run();
			RuleModel ruleModel = (RuleModel) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			logger.error("No ha sido posible ejecutar el proceso con Decision Tree to rules");
		}
		return results;
	}

	public XYLineChart getAucChart(RoisinResults roisinResults) {

		int width = 500;
		int heigth = 300;

		List<RoisinRule> rules = roisinResults.getRoisinRules();
		int rulesSize = rules.size();

		double[] xValues = new double[rulesSize + 1];
		double[] yValues = new double[rulesSize + 1];
		xValues[0] = 0.0;
		yValues[0] = 0.0;

		for (int i = 0; i < rules.size(); i++) {
			xValues[i + 1] = rules.get(i).getFalsePositiveRate();
			yValues[i + 1] = rules.get(i).getTruePositiveRate();
		}

		Data xData = DataUtil.scaleWithinRange(0.0, 1.0, xValues);
		Data yData = DataUtil.scaleWithinRange(0.0, 1.0, yValues);

		XYLine line = Plots.newXYLine(xData, yData);
		XYLineChart chart = GCharts.newXYLineChart(line);
		chart.setSize(width, heigth);
		chart.setTitle("Area under the curve");

		return chart;
	}
}
