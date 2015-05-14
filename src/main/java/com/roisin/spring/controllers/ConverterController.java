package com.roisin.spring.controllers;

import static com.roisin.spring.utils.Constants.DOT_SYMBOL;
import static com.roisin.spring.utils.Constants.SHA_256;
import static com.roisin.spring.utils.ModelViewConstants.ERROR_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.FORM_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.UPLOADED_FILE;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.forms.DownloadForm;
import com.roisin.spring.model.ConvertUploadFile;
import com.roisin.spring.services.FileUtils;
import com.roisin.spring.utils.HashUtils;
import com.roisin.spring.utils.Runner;
import com.roisin.spring.validator.FileConverterValidator;

import exception.RoisinException;

@Controller
@RequestMapping("/converter")
public class ConverterController {

	/**
	 * Class log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConverterController.class);

	/**
	 * File converter validator
	 */
	@Autowired
	private transient FileConverterValidator fileConverterValidator;

	/**
	 * File utility class
	 */
	@Autowired
	private transient FileUtils fileUtils;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView upload() {

		final ModelAndView res = new ModelAndView("converter/create");
		res.addObject(UPLOADED_FILE, new ConvertUploadFile());

		return res;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploaded(
			@ModelAttribute(UPLOADED_FILE) final ConvertUploadFile convertUploadedFile,
			final BindingResult result) throws RoisinException {
		ModelAndView res;
		try {

			final MultipartFile file = convertUploadedFile.getFile();
			fileConverterValidator.validate(convertUploadedFile, result);

			if (result.hasErrors()) {
				res = new ModelAndView("converter/create");
				res.addObject(ERROR_LOWER_CASE, true);
			} else {

				String originalFileName = StringUtils.substringBeforeLast(
						file.getOriginalFilename(), DOT_SYMBOL);
				String inputFormat = StringUtils.substringAfterLast(file.getOriginalFilename(),
						DOT_SYMBOL);
				final String fileHash = HashUtils.fileChecksum(file.getBytes(), SHA_256);
				final String outputFormat = convertUploadedFile.getOutputFormat();
				String outputPath = fileUtils.getConvertPath() + fileHash + DOT_SYMBOL
						+ outputFormat;
				final String inputPath = fileUtils.getConvertPath() + fileHash + DOT_SYMBOL
						+ inputFormat;
				fileUtils.writeFileFromByteArray(file.getBytes(), fileUtils.getConvertPath()
						+ fileHash + DOT_SYMBOL + inputFormat);
				Runner.convertFile(inputFormat, outputFormat, inputPath, outputPath);

				final DownloadForm form = new DownloadForm();
				form.setHash(fileHash);
				form.setOutputFormat(outputFormat);
				form.setFileName(originalFileName);

				res = new ModelAndView("converter/create");
				res.addObject(UPLOADED_FILE, new ConvertUploadFile());
				res.addObject(FORM_LOWER_CASE, form);
			}
		} catch (IOException e) {
			LOG.error("No ha sido posible convertir el fichero", e);
			throw new RoisinException("Could not convert file");
		}
		return res;
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<byte[]> download(@ModelAttribute final DownloadForm form)
			throws RoisinException {

		String filePath;
		filePath = fileUtils.getConvertPath() + form.getHash() + DOT_SYMBOL
				+ form.getOutputFormat();
		final String exportFileName = form.getFileName() + DOT_SYMBOL + form.getOutputFormat();
		final byte[] fileContent = fileUtils.getFileFromPath(filePath);
		// Create and configure headers to return the file
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/" + form.getOutputFormat()));
		headers.setContentDispositionFormData(exportFileName, exportFileName);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileContent, headers,
				HttpStatus.OK);

		return response;
	}

}
