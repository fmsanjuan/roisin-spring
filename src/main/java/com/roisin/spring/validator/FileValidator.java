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

import com.roisin.spring.model.UploadedFile;

public class FileValidator implements Validator {

	@Override
	public boolean supports(final Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(final Object uploadedFile, final Errors errors) {

		final UploadedFile file = (UploadedFile) uploadedFile;

		if (file.getFile().getSize() == 0) {
			errors.rejectValue(FILE2, "file.error.select.file", "file.error.select.file");
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
				errors.rejectValue(FILE2, "file.error.format", "file.error.format");
			}
		}

	}

}
