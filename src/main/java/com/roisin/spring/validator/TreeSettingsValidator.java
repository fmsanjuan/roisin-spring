package com.roisin.spring.validator;

import static com.roisin.spring.utils.ProcessConstants.ACCURACY_RIPPER_TREE;
import static com.roisin.spring.utils.ProcessConstants.GAIN_RATIO;
import static com.roisin.spring.utils.ProcessConstants.GINI_INDEX;
import static com.roisin.spring.utils.ProcessConstants.INFORMATION_GAIN;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.TreeToRulesSettings;

public class TreeSettingsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		TreeToRulesSettings form = (TreeToRulesSettings) target;

		if (StringUtils.isBlank(form.getTree2RulesCriterion())
				|| (!form.getTree2RulesCriterion().equals(GAIN_RATIO)
						&& !form.getTree2RulesCriterion().equals(INFORMATION_GAIN)
						&& !form.getTree2RulesCriterion().equals(GINI_INDEX) && !form
						.getTree2RulesCriterion().equals(ACCURACY_RIPPER_TREE))) {
			errors.rejectValue("tree2RulesCriterion", "process.tree.error.criterion",
					"process.tree.error.criterion");
		}

		if (form.getMinimalSizeForSplit() < 1) {
			errors.rejectValue("minimalSizeForSplit", "process.tree.error.criterion",
					"process.tree.error.criterion");
		}

		if (form.getMinimalLeafSize() < 1) {
			errors.rejectValue("minimalLeafSize", "process.tree.error.min.size.split",
					"process.tree.error.min.size.split");
		}

		if (form.getMinimalGain().doubleValue() < 0.0) {
			errors.rejectValue("minimalGain", "process.tree.error.minimal.gain",
					"process.tree.error.minimal.gain");
		}

		if (form.getMaximalDepth() < -1) {
			errors.rejectValue("maximalDepth", "process.tree.error.maximal.depth",
					"process.tree.error.maximal.depth");
		}

		if (form.getConfidence() > 0.5 || form.getConfidence() < Math.pow(Math.E, -7)) {
			errors.rejectValue("confidence", "process.tree.error.confidence",
					"process.tree.error.confidence");
		}

		if (form.getNumberOfPrepruningAlternatives() < 0) {
			errors.rejectValue("maximalDepth", "process.tree.error.prepruning.alternatives",
					"process.tree.error.prepruning.alternatives");
		}

		if (form.getNoPrepruning() == null) {
			errors.rejectValue("maximalDepth", "process.tree.error.no.prepruning",
					"process.tree.error.no.prepruning");
		}

		if (form.getNoPruning() == null) {
			errors.rejectValue("maximalDepth", "process.tree.error.no.pruning",
					"process.tree.error.no.pruning");
		}
	}

}
