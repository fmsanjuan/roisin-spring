package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.utils.Constants;

public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object uploadedFile, Errors errors) {

		UploadedFile file = (UploadedFile) uploadedFile;

		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "uploadForm.selectFile", "Please select a file!");
		}

		if (file.getFile().getSize() > 0) {
			String fileFormat = StringUtils.substringAfterLast(
					file.getFile().getOriginalFilename(), Constants.DOT_SYMBOL);

			if (StringUtils.isBlank(fileFormat)
					|| (!fileFormat.equalsIgnoreCase(Constants.FORMAT_XLSX)
							&& !fileFormat.equalsIgnoreCase(Constants.FORMAT_XLS)
							&& !fileFormat.equalsIgnoreCase(Constants.FORMAT_ARFF)
							&& !fileFormat.equalsIgnoreCase(Constants.FORMAT_CSV) && !fileFormat
								.equalsIgnoreCase(Constants.FORMAT_XRFF))) {
				errors.rejectValue("file", "uploadForm.fileFormat",
						"The file format must be xlsx, xls, arff, xrff or csv");
			}
		}

	}

}
