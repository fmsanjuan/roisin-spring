package com.roisin.spring.validator;

import static com.roisin.spring.utils.ProcessConstants.ABOVE_MINIMUM_UTILITY;
import static com.roisin.spring.utils.ProcessConstants.ACCURACY_SUBGROUP;
import static com.roisin.spring.utils.ProcessConstants.BIAS;
import static com.roisin.spring.utils.ProcessConstants.BINOMINAL;
import static com.roisin.spring.utils.ProcessConstants.BOTH;
import static com.roisin.spring.utils.ProcessConstants.COVERAGE;
import static com.roisin.spring.utils.ProcessConstants.K_BEST_RULES;
import static com.roisin.spring.utils.ProcessConstants.LIFT;
import static com.roisin.spring.utils.ProcessConstants.NEGATIVE;
import static com.roisin.spring.utils.ProcessConstants.ODDS;
import static com.roisin.spring.utils.ProcessConstants.ODDS_RATIO;
import static com.roisin.spring.utils.ProcessConstants.POSITIVE;
import static com.roisin.spring.utils.ProcessConstants.PRECISION;
import static com.roisin.spring.utils.ProcessConstants.PREDICTION;
import static com.roisin.spring.utils.ProcessConstants.SQUARED;
import static com.roisin.spring.utils.ProcessConstants.WRACC;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.SubgroupSettings;

public class SubgroupSettingsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		SubgroupSettings form = (SubgroupSettings) target;

		if (StringUtils.isBlank(form.getMode())
				|| (!form.getMode().equals(K_BEST_RULES) && !form.getMode().equals(
						ABOVE_MINIMUM_UTILITY))) {
			errors.rejectValue("mode", "process.subgroup.error.mode", "process.subgroup.error.mode");
		}

		if (StringUtils.isBlank(form.getUtilityFunction())
				|| (!form.getUtilityFunction().equals(WRACC)
						&& !form.getUtilityFunction().equals(COVERAGE)
						&& !form.getUtilityFunction().equals(PRECISION)
						&& !form.getUtilityFunction().equals(ACCURACY_SUBGROUP)
						&& !form.getUtilityFunction().equals(BIAS)
						&& !form.getUtilityFunction().equals(LIFT)
						&& !form.getUtilityFunction().equals(BINOMINAL)
						&& !form.getUtilityFunction().equals(SQUARED)
						&& !form.getUtilityFunction().equals(ODDS) && !form.getUtilityFunction()
						.equals(ODDS_RATIO))) {
			errors.rejectValue("utilityFunction", "process.subgroup.error.utility.function",
					"process.subgroup.error.utility.function");
		}

		if (form.getkBestRules() < 1) {
			errors.rejectValue("kBestRules", "process.subgroup.error.k.best.rules",
					"process.subgroup.error.k.best.rules");
		}

		if (StringUtils.isBlank(form.getRuleGeneration())
				|| (!form.getRuleGeneration().equals(POSITIVE)
						&& !form.getRuleGeneration().equals(NEGATIVE)
						&& !form.getRuleGeneration().equals(PREDICTION) && !form
						.getRuleGeneration().equals(BOTH))) {
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
