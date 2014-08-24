package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.utils.Constants;

public class PreproSimpleFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

	public void validateExport(Object target, Errors errors) {
		PreproSimpleForm form = (PreproSimpleForm) target;

		if (form.getExportAttributeSelection().size() < 1) {
			errors.rejectValue("attributeSelection", "preform.error.attribute",
					"preform.error.attribute");
		}

		if (!form.getExportFormat().equals(Constants.FORMAT_XLSX)
				&& !form.getExportFormat().equals(Constants.FORMAT_XLS)
				&& !form.getExportFormat().equals(Constants.FORMAT_ARFF)
				&& !form.getExportFormat().equals(Constants.FORMAT_XRFF)
				&& !form.getExportFormat().equals(Constants.FORMAT_CSV)) {
			errors.rejectValue("exportFormat", "preform.error.format", "preform.error.format");
		}

	}

	public void validateProcess(Object target, Errors errors) {
		PreproSimpleForm form = (PreproSimpleForm) target;

		if (StringUtils.isBlank(form.getName())) {
			errors.rejectValue("name", "preform.error.name", "preform.error.name");
		}

		if (StringUtils.isBlank(form.getDescription())) {
			errors.rejectValue("description", "preform.error.description",
					"preform.error.description");
		}

		if (!form.getProcessAttributeSelection().contains(form.getLabel())) {
			errors.rejectValue("label", "preform.error.label", "preform.error.label");
		}

		if (form.getProcessAttributeSelection().size() < 3) {
			errors.rejectValue("attributeSelection", "preform.error.attribute.selection",
					"preform.error.attribute.selection");
		}

		validate(target, errors);
	}

}
