package com.roisin.spring.utils;

import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.operator.learner.subgroups.RuleSet;
import com.roisin.core.processes.GenericProcesses;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.SubgroupResults;
import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.model.SubgroupSettings;
import com.roisin.spring.model.TreeToRulesSettings;

import exception.RoisinException;

public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	public static boolean started = false;

	public static ExampleSet getExampleSetFromFile(String fileFormat, String path) {
		checkStarted();

		Process process = com.roisin.core.processes.Preprocessing.getExampleSetFromFileProcess(
				fileFormat, path);
		IOContainer container;
		ExampleSet res = null;
		try {
			container = process.run();
			res = (ExampleSet) container.asList().get(0);
			return res;
		} catch (OperatorException e) {
			logger.error("No ha sido posible extraer información del fichero");
		}
		return res;
	}

	public static ExampleSet getPreprocessedExampleSetFromFile(String inputPath,
			SortedSet<Integer> rowFilter, String filterCondition, List<String> attributeSelection) {
		ExampleSet res = null;
		Process process;
		try {
			process = com.roisin.core.processes.Preprocessing.getPreprocessedExampleSet(inputPath,
					rowFilter, filterCondition, attributeSelection);
			IOContainer container;
			container = process.run();
			res = (ExampleSet) container.asList().get(0);
			return res;
		} catch (OperatorException e) {
			logger.error("No ha sido posible preprocesar la información del fichero", e);
		} catch (RoisinException e1) {
			logger.error("No ha sido posible preprocesar la información del fichero", e1);
		}
		return res;
	}

	public static RoisinResults getRipperResults(RipperSettings form, String filePath,
			String label, String filterCondition, List<String> attributeSelection,
			SortedSet<Integer> deletedRows) {
		RipperResults results = null;
		try {
			Process process = GenericProcesses.getRipper(filePath, label, deletedRows,
					filterCondition, attributeSelection, form.getRipperCriterion(), form
							.getSampleRatio().toString(), form.getPureness().toString(), form
							.getMinimalPruneBenefit().toString());
			IOContainer container = process.run();
			RuleModel ruleModel = (RuleModel) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			logger.error("No ha sido posible ejecutar el proceso con Ripper");
		}
		return results;
	}

	public static RoisinResults getSubgroupResults(SubgroupSettings form, String filePath,
			String label, String filterCondition, List<String> attributeSelection,
			SortedSet<Integer> deletedRows) {
		SubgroupResults results = null;
		try {
			Process process = GenericProcesses.getSubgroupDiscoveryDiscretization(filePath, label,
					deletedRows, filterCondition, attributeSelection, form.getMode(), form
							.getUtilityFunction(), form.getMinUtility().toString(), form
							.getkBestRules().toString(), form.getRuleGeneration().toString(), form
							.getMaxDepth().toString(), form.getMinCoverage().toString());
			IOContainer container = process.run();
			RuleSet ruleModel = (RuleSet) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new SubgroupResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			logger.error("No ha sido posible ejecutar el proceso con Subgroup Discovery");
		}
		return results;
	}

	public static RoisinResults getTreeToRulesResults(TreeToRulesSettings form, String filePath,
			String label, String filterCondition, List<String> attributeSelection,
			SortedSet<Integer> deletedRows) {
		RipperResults results = null;
		try {
			Process process = GenericProcesses.getDecisionTreeToRules(filePath, label, deletedRows,
					filterCondition, attributeSelection, form.getTree2RulesCriterion(), form
							.getMinimalSizeForSplit().toString(), form.getMinimalLeafSize()
							.toString(), form.getMinimalGain().toString(), form.getMaximalDepth()
							.toString(), form.getConfidence().toString(), form
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

	public static void startRapidminer() {
		logger.info("Iniciando rapidminer");
		RapidMiner.setExecutionMode(ExecutionMode.APPSERVER);
		RapidMiner.init();
		started = true;
	}

	public static boolean isStarted() {
		return started;
	}

	public static void checkStarted() {
		if (!isStarted()) {
			startRapidminer();
		}
	}

}
