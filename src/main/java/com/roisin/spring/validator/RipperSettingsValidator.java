package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.utils.ProcessConstants;

public class RipperSettingsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		RipperSettings form = (RipperSettings) target;

		if (StringUtils.isBlank(form.getRipperCriterion())
				|| (!form.getRipperCriterion().equals(ProcessConstants.INFORMATION_GAIN) && !form
						.getRipperCriterion().equals(ProcessConstants.ACCURACY_RIPPER_TREE))) {
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
