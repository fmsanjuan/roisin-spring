package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.PreprocessingForm;

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
}
