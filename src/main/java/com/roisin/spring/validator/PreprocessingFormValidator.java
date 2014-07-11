package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.forms.PreprocessingForm;
import com.roisin.spring.utils.ProcessConstants;

public class PreprocessingFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		PreprocessingForm form = (PreprocessingForm) target;

		if (!StringUtils.isBlank(form.getLabel())
				&& !form.getAttributeSelection().contains(form.getLabel())) {
			errors.rejectValue("label", "form.label",
					"The label selected is not in the attribute selection");
		}

		if (form.getAttributeSelection().size() < 3) {
			errors.rejectValue("attributeSelection", "form.attributes",
					"You must select at least three attributes");
		}

		if (form.getDeletedRows() != null
				&& form.getDeletedRows().size() >= form.getExampleSetSize()) {
			errors.rejectValue("deletedRows", "form.rows", "You cannot delete all the examples");
		}

	}

	public void validateRipper(Object target, Errors errors) {

		PreprocessingForm form = (PreprocessingForm) target;

		if (StringUtils.isBlank(form.getRipperCriterion())
				|| (!form.getRipperCriterion().equals(ProcessConstants.INFORMATION_GAIN) && !form
						.getRipperCriterion().equals(ProcessConstants.ACCURACY_RIPPER_TREE))) {
			errors.rejectValue("ripperCriterion", "form.ripperCriterion",
					"Please, select a criterion");
		}

		if (form.getSampleRatio().doubleValue() < 0.0 || form.getSampleRatio() > 1.0) {
			errors.rejectValue("sampleRatio", "form.sampleRatio", "Must be betweeen 0 and 1");
		}

		if (form.getPureness().doubleValue() < 0.0 || form.getPureness() > 1.0) {
			errors.rejectValue("pureness", "form.pureness", "Must be between 0 and 1");
		}

		if (form.getMinimalPruneBenefit().doubleValue() < 0.0
				|| form.getMinimalPruneBenefit() > 1.0) {
			errors.rejectValue("minimalPruneBenefit", "form.minimalPruneBenefit",
					"Must be between 0 and 1");
		}

		validate(form, errors);
	}

	public void validateSubgroup(Object target, Errors errors) {

		PreprocessingForm form = (PreprocessingForm) target;

		if (StringUtils.isBlank(form.getMode())
				|| (!form.getMode().equals(ProcessConstants.K_BEST_RULES) && !form.getMode()
						.equals(ProcessConstants.ABOVE_MINIMUM_UTILITY))) {
			errors.rejectValue("mode", "form.mode", "Please, select a mode");
		}

		if (StringUtils.isBlank(form.getUtilityFunction())
				|| (!form.getUtilityFunction().equals(ProcessConstants.WRACC)
						&& !form.getUtilityFunction().equals(ProcessConstants.COVERAGE)
						&& !form.getUtilityFunction().equals(ProcessConstants.PRECISION)
						&& !form.getUtilityFunction().equals(ProcessConstants.ACCURACY_SUBGROUP)
						&& !form.getUtilityFunction().equals(ProcessConstants.BIAS)
						&& !form.getUtilityFunction().equals(ProcessConstants.LIFT)
						&& !form.getUtilityFunction().equals(ProcessConstants.BINOMINAL)
						&& !form.getUtilityFunction().equals(ProcessConstants.SQUARED)
						&& !form.getUtilityFunction().equals(ProcessConstants.ODDS) && !form
						.getUtilityFunction().equals(ProcessConstants.ODDS_RATIO))) {
			errors.rejectValue("utilityFunction", "form.utilityFunction",
					"Please, select a utility function");
		}

		if (form.getkBestRules() < 1) {
			errors.rejectValue("kBestRules", "form.kBestRules",
					"Must be greater than or equal to 1");
		}

		if (StringUtils.isBlank(form.getRuleGeneration())
				|| (!form.getRuleGeneration().equals(ProcessConstants.POSITIVE)
						&& !form.getRuleGeneration().equals(ProcessConstants.NEGATIVE)
						&& !form.getRuleGeneration().equals(ProcessConstants.PREDICTION) && !form
						.getRuleGeneration().equals(ProcessConstants.BOTH))) {
			errors.rejectValue("ruleGeneration", "form.ruleGeneration",
					"Please, select a rule generatio mode");
		}

		if (form.getMaxDepth() < 0) {
			errors.rejectValue("maxDepth", "form.maxDepth", "Must be greater than or equal to 0");
		}

		if (form.getMinCoverage().doubleValue() < 0.0 || form.getMinCoverage().doubleValue() > 1.0) {
			errors.rejectValue("minCoverage", "form.minCoverage", "Must be between 0 and 1");
		}

		validate(form, errors);
	}

	public void validateTree2rules(Object target, Errors errors) {

		PreprocessingForm form = (PreprocessingForm) target;

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

		if (form.getConfidence() > 0.5 || form.getConfidence() < 0.0) {
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

		validate(form, errors);
	}

}
