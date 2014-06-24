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
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.utils.Constants;

@Service
public class ProcessingService {

	private static final Logger logger = LoggerFactory.getLogger(ProcessingService.class);

	public RoisinResults getRipperResults(PreprocessingForm form) {
		RipperResults results = null;
		try {
			Process process = GenericProcesses.getRipper(
					StringUtils.substringAfterLast(form.getFilePath(), Constants.DOT_SYMBOL),
					form.getFilePath(), form.getLabel(), form.getDeletedRows(),
					form.getFilterCondition(), form.getDeletedAttributes());
			IOContainer container = process.run();
			RuleModel ruleModel = (RuleModel) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	public RoisinResults getSubgroupResults(PreprocessingForm form) {
		SubgroupResults results = null;
		try {
			Process process = GenericProcesses.getSubgroupDiscoveryDiscretization(
					StringUtils.substringAfterLast(form.getFilePath(), Constants.DOT_SYMBOL),
					form.getFilePath(), form.getLabel(), form.getDeletedRows(),
					form.getFilterCondition(), form.getDeletedAttributes());
			IOContainer container = process.run();
			RuleSet ruleModel = (RuleSet) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new SubgroupResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	public RoisinResults getTreeToRulesResults(PreprocessingForm form) {
		RipperResults results = null;
		try {
			Process process = GenericProcesses.getDecisionTreeToRules(
					StringUtils.substringAfterLast(form.getFilePath(), Constants.DOT_SYMBOL),
					form.getFilePath(), form.getLabel(), form.getDeletedRows(),
					form.getFilterCondition(), form.getDeletedAttributes());
			IOContainer container = process.run();
			RuleModel ruleModel = (RuleModel) container.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
}
