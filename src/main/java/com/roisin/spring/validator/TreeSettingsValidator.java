package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.TreeToRulesSettings;
import com.roisin.spring.utils.ProcessConstants;

public class TreeSettingsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		TreeToRulesSettings form = (TreeToRulesSettings) target;

		if (StringUtils.isBlank(form.getTree2RulesCriterion())
				|| (!form.getTree2RulesCriterion().equals(ProcessConstants.GAIN_RATIO)
						&& !form.getTree2RulesCriterion().equals(ProcessConstants.INFORMATION_GAIN)
						&& !form.getTree2RulesCriterion().equals(ProcessConstants.GINI_INDEX) && !form
						.getTree2RulesCriterion().equals(ProcessConstants.ACCURACY_RIPPER_TREE))) {
			errors.rejectValue("tree2RulesCriterion", "form.tree2RulesCriterion",
					"Please, select a criterion");
		}

		if (form.getMinimalSizeForSplit() < 1) {
			errors.rejectValue("minimalSizeForSplit", "form.minimalSizeForSplit",
					"Must be greater than or equal to 1");
		}

		if (form.getMinimalLeafSize() < 1) {
			errors.rejectValue("minimalLeafSize", "form.minimalLeafSize",
					"Must be greater than or equal to 1");
		}

		if (form.getMinimalGain().doubleValue() < 0.0) {
			errors.rejectValue("minimalGain", "form.minimalGain",
					"Must be greater than or equal to 0");
		}

		if (form.getMaximalDepth() < -1) {
			errors.rejectValue("maximalDepth", "form.maximalDepth",
					"Must be greater than or equal to -1");
		}

		if (form.getConfidence() > 0.5 || form.getConfidence() < Math.pow(Math.E, -7)) {
			errors.rejectValue("confidence", "form.confidence", "Must be between 0 and 0.5");
		}

		if (form.getNumberOfPrepruningAlternatives() < 0) {
			errors.rejectValue("maximalDepth", "form.maximalDepth",
					"Must be greater than or equal to 0");
		}

		if (form.getNoPrepruning() == null) {
			errors.rejectValue("maximalDepth", "form.maximalDepth", "Cannot be null");
		}

		if (form.getNoPruning() == null) {
			errors.rejectValue("maximalDepth", "form.maximalDepth", "Cannot be null");
		}
	}

}
