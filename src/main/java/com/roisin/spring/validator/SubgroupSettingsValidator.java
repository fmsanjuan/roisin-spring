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
			errors.rejectValue("mode", "process.subgroup.error.mode", "process.subgroup.error.mode");
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
			errors.rejectValue("utilityFunction", "process.subgroup.error.utility.function",
					"process.subgroup.error.utility.function");
		}

		if (form.getkBestRules() < 1) {
			errors.rejectValue("kBestRules", "process.subgroup.error.k.best.rules",
					"process.subgroup.error.k.best.rules");
		}

		if (StringUtils.isBlank(form.getRuleGeneration())
				|| (!form.getRuleGeneration().equals(ProcessConstants.POSITIVE)
						&& !form.getRuleGeneration().equals(ProcessConstants.NEGATIVE)
						&& !form.getRuleGeneration().equals(ProcessConstants.PREDICTION) && !form
						.getRuleGeneration().equals(ProcessConstants.BOTH))) {
			errors.rejectValue("ruleGeneration", "process.subgroup.error.rule.generation",
					"process.subgroup.error.rule.generation");
		}

		if (form.getMaxDepth() < 0) {
			errors.rejectValue("maxDepth", "process.subgroup.error.max.depth",
					"process.subgroup.error.max.depth");
		}

		if (form.getMinCoverage().doubleValue() < 0.0 || form.getMinCoverage().doubleValue() > 1.0) {
			errors.rejectValue("minCoverage", "process.subgroup.error.min.coverage",
					"process.subgroup.error.min.coverage");
		}
	}

}
