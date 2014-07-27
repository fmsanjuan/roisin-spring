package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.SubgroupSettings;
import com.roisin.spring.utils.ProcessConstants;

public class SubgroupSettingsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		SubgroupSettings form = (SubgroupSettings) target;

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

}
