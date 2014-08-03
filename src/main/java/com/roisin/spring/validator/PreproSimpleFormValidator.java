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

		if (form.getAttributeSelection().size() < 1) {
			errors.rejectValue("attributeSelection", "form.attributeSelection",
					"Select at least 1 attribute");
		}

		if (!form.getExportFormat().equals(Constants.FORMAT_XLSX)
				&& !form.getExportFormat().equals(Constants.FORMAT_XLS)
				&& !form.getExportFormat().equals(Constants.FORMAT_ARFF)
				&& !form.getExportFormat().equals(Constants.FORMAT_XRFF)
				&& !form.getExportFormat().equals(Constants.FORMAT_CSV)) {
			errors.rejectValue("exportFormat", "form.exportFormat", "Invalid format");
		}

	}

	public void validateProcess(Object target, Errors errors) {
		PreproSimpleForm form = (PreproSimpleForm) target;

		if (StringUtils.isBlank(form.getName())) {
			errors.rejectValue("name", "form.name", "Name cannot be blank");
		}

		if (StringUtils.isBlank(form.getDescription())) {
			errors.rejectValue("description", "form.description", "Description cannot be blank");
		}

		if (!form.getAttributeSelection().contains(form.getLabel())) {
			errors.rejectValue("label", "form.label", "Label must be in attribute selection");
		}

		if (form.getAttributeSelection().size() < 3) {
			errors.rejectValue("attributeSelection", "form.attributeSelection",
					"You must select at least 3 attributes");
		}

		validate(target, errors);
	}

}
