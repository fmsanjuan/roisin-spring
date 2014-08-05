package com.roisin.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.ConvertUploadFile;
import com.roisin.spring.utils.Constants;

public class FileConverterValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object uploadedFile, Errors errors) {

		ConvertUploadFile file = (ConvertUploadFile) uploadedFile;

		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "convertUploadForm.selectFile", "Please select a file!");
		}

		if (StringUtils.isBlank(file.getOutputFormat())
				|| (!file.getOutputFormat().equalsIgnoreCase(Constants.FORMAT_XLSX)
						&& !file.getOutputFormat().equalsIgnoreCase(Constants.FORMAT_XLS)
						&& !file.getOutputFormat().equalsIgnoreCase(Constants.FORMAT_ARFF)
						&& !file.getOutputFormat().equalsIgnoreCase(Constants.FORMAT_CSV) && !file
						.getOutputFormat().equalsIgnoreCase(Constants.FORMAT_XRFF))) {
			errors.rejectValue("file", "convertUploadForm.outputFormat",
					"Output format is not correct");
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
				errors.rejectValue("file", "convertUploadForm.fileFormat",
						"The file format must be xlsx, xls, arff, xrff or csv");
			}

			if (file.getOutputFormat().equalsIgnoreCase(fileFormat)) {
				errors.rejectValue("file", "convertUploadForm.fileFormat",
						"Input format must be different to output format");
			}
		}

	}
}
