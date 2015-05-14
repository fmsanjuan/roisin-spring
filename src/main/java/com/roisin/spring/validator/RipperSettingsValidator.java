package com.roisin.spring.validator;

import static com.roisin.spring.utils.ProcessConstants.ACCURACY_RIPPER_TREE;
import static com.roisin.spring.utils.ProcessConstants.INFORMATION_GAIN;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.RipperSettings;

public class RipperSettingsValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final RipperSettings form = (RipperSettings) target;

		if (StringUtils.isBlank(form.getRipperCriterion())
				|| (!form.getRipperCriterion().equals(INFORMATION_GAIN) && !form
						.getRipperCriterion().equals(ACCURACY_RIPPER_TREE))) {
			errors.rejectValue("ripperCriterion", "process.ripper.error.criterion",
					"process.ripper.error.criterion");
		}

		if (form.getSampleRatio().doubleValue() < 0.0 || form.getSampleRatio() > 1.0) {
			errors.rejectValue("sampleRatio", "process.ripper.error.sample.ratio",
					"process.ripper.error.sample.ratio");
		}

		if (form.getPureness().doubleValue() < 0.0 || form.getPureness() > 1.0) {
			errors.rejectValue("pureness", "process.ripper.error.pureness",
					"process.ripper.error.pureness");
		}

		if (form.getMinimalPruneBenefit().doubleValue() < 0.0
				|| form.getMinimalPruneBenefit() > 1.0) {
			errors.rejectValue("minimalPruneBenefit", "process.ripper.error.minimal.prune.benefit",
					"process.ripper.error.minimal.prune.benefit");
		}
	}

}
