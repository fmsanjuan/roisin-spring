package com.roisin.spring.validator;

import static com.roisin.spring.utils.Constants.DOT_SYMBOL;
import static com.roisin.spring.utils.Constants.FORMAT_ARFF;
import static com.roisin.spring.utils.Constants.FORMAT_CSV;
import static com.roisin.spring.utils.Constants.FORMAT_XLS;
import static com.roisin.spring.utils.Constants.FORMAT_XLSX;
import static com.roisin.spring.utils.Constants.FORMAT_XRFF;
import static com.roisin.spring.utils.ModelViewConstants.FILE2;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.roisin.spring.model.ConvertUploadFile;

public class FileConverterValidator implements Validator {

	@Override
	public boolean supports(final Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(final Object uploadedFile, final Errors errors) {

		final ConvertUploadFile file = (ConvertUploadFile) uploadedFile;

		if (file.getFile().getSize() == 0) {
			errors.rejectValue(FILE2, "converter.error.select.file", "converter.error.select.file");
		}

		if (StringUtils.isBlank(file.getOutputFormat())
				|| (!file.getOutputFormat().equalsIgnoreCase(FORMAT_XLSX)
						&& !file.getOutputFormat().equalsIgnoreCase(FORMAT_XLS)
						&& !file.getOutputFormat().equalsIgnoreCase(FORMAT_ARFF)
						&& !file.getOutputFormat().equalsIgnoreCase(FORMAT_CSV) && !file
						.getOutputFormat().equalsIgnoreCase(FORMAT_XRFF))) {
			errors.rejectValue(FILE2, "converter.error.output.format",
					"converter.error.output.format");
		}

		if (file.getFile().getSize() > 0) {
			String fileFormat = StringUtils.substringAfterLast(
					file.getFile().getOriginalFilename(), DOT_SYMBOL);

			if (StringUtils.isBlank(fileFormat)
					|| (!fileFormat.equalsIgnoreCase(FORMAT_XLSX)
							&& !fileFormat.equalsIgnoreCase(FORMAT_XLS)
							&& !fileFormat.equalsIgnoreCase(FORMAT_ARFF)
							&& !fileFormat.equalsIgnoreCase(FORMAT_CSV) && !fileFormat
								.equalsIgnoreCase(FORMAT_XRFF))) {
				errors.rejectValue(FILE2, "converter.error.format", "converter.error.format");
			}

			if (file.getOutputFormat().equalsIgnoreCase(fileFormat)) {
				errors.rejectValue(FILE2, "converter.error.input.format",
						"converter.error.input.format");
			}
		}

	}
}
