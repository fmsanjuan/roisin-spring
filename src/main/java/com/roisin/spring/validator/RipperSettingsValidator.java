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
	}

}
