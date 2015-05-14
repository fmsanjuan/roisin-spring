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

	/**
	 * Lowest number of prepruning alternatives
	 */
	private static final int PREPRU_ALT_LIM = 0;

	/**
	 * Lowest leaf size value
	 */
	private static final int LEAF_SIZE_LIM = 1;

	/**
	 * Lowest minal size for split value
	 */
	private static final int M_SIZE_SPLIT_LIM = 1;

	/**
	 * Confidence highest value
	 */
	private static final double CONFIDENCE_LIM = 0.5;

	/**
	 * Maximal depth lowest value
	 */
	private static final int MAX_DEPTH_LIM = -1;

	/**
	 * Minimal gain lowest value
	 */
	private static final double MINIMAL_GAIN = 0.0;

	/**
	 * MAXIMAL DEPTH
	 */
	private static final String MAXIMAL_DEPTH = "maximalDepth";

	/**
	 * Criterion error message
	 */
	private static final String MSG_CRITERION = "process.tree.error.criterion";

	@Override
	public boolean supports(final Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(final Object target, final Errors errors) {

		final TreeToRulesSettings form = (TreeToRulesSettings) target;

		if (StringUtils.isBlank(form.getTree2RulesCriterion())
				|| (!form.getTree2RulesCriterion().equals(GAIN_RATIO)
						&& !form.getTree2RulesCriterion().equals(INFORMATION_GAIN)
						&& !form.getTree2RulesCriterion().equals(GINI_INDEX) && !form
						.getTree2RulesCriterion().equals(ACCURACY_RIPPER_TREE))) {
			errors.rejectValue("tree2RulesCriterion", MSG_CRITERION, MSG_CRITERION);
		}

		if (form.getMinimalSizeForSplit() < M_SIZE_SPLIT_LIM) {
			errors.rejectValue("minimalSizeForSplit", MSG_CRITERION, MSG_CRITERION);
		}

		if (form.getMinimalLeafSize() < LEAF_SIZE_LIM) {
			errors.rejectValue("minimalLeafSize", "process.tree.error.min.size.split",
					"process.tree.error.min.size.split");
		}

		if (form.getMinimalGain().doubleValue() < MINIMAL_GAIN) {
			errors.rejectValue("minimalGain", "process.tree.error.minimal.gain",
					"process.tree.error.minimal.gain");
		}

		if (form.getMaximalDepth() < MAX_DEPTH_LIM) {
			errors.rejectValue(MAXIMAL_DEPTH, "process.tree.error.maximal.depth",
					"process.tree.error.maximal.depth");
		}

		if (form.getConfidence() > CONFIDENCE_LIM || form.getConfidence() < Math.pow(Math.E, -7)) {
			errors.rejectValue("confidence", "process.tree.error.confidence",
					"process.tree.error.confidence");
		}

		if (form.getNumberOfPrepruningAlternatives() < PREPRU_ALT_LIM) {
			errors.rejectValue(MAXIMAL_DEPTH, "process.tree.error.prepruning.alternatives",
					"process.tree.error.prepruning.alternatives");
		}

		if (form.getNoPrepruning() == null) {
			errors.rejectValue(MAXIMAL_DEPTH, "process.tree.error.no.prepruning",
					"process.tree.error.no.prepruning");
		}

		if (form.getNoPruning() == null) {
			errors.rejectValue(MAXIMAL_DEPTH, "process.tree.error.no.pruning",
					"process.tree.error.no.pruning");
		}
	}

}
