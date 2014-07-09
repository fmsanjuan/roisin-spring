package com.roisin.spring.services;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rapidminer.Process;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.operator.learner.subgroups.RuleSet;
import com.roisin.core.processes.GenericProcesses;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.RoisinResults;
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
}
