package com.roisin.spring.controllers;

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
import com.roisin.spring.utils.Constants;
import com.roisin.spring.utils.FileUtils;
import com.roisin.spring.utils.HashUtils;
import com.roisin.spring.utils.Runner;
import com.roisin.spring.validator.FileConverterValidator;

import exception.RoisinException;

@Controller
@RequestMapping("/converter")
public class ConverterController {

	private static final Logger logger = LoggerFactory.getLogger(ConverterController.class);

	@Autowired
	private FileConverterValidator fileConverterValidator;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView upload() {

		ModelAndView res = new ModelAndView("converter/create");
		res.addObject("uploadedFile", new ConvertUploadFile());

		return res;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploaded(
			@ModelAttribute("uploadedFile") ConvertUploadFile convertUploadedFile,
			BindingResult result) throws RoisinException {
		try {

			MultipartFile file = convertUploadedFile.getFile();
			fileConverterValidator.validate(convertUploadedFile, result);

			if (result.hasErrors()) {
				ModelAndView res = new ModelAndView("converter/create");
				res.addObject("error", true);

				return res;
			} else {

				String originalFileName = StringUtils.substringBeforeLast(
						file.getOriginalFilename(), Constants.DOT_SYMBOL);
				String inputFormat = StringUtils.substringAfterLast(file.getOriginalFilename(),
						Constants.DOT_SYMBOL);
				String fileHash = HashUtils.fileChecksum(file.getBytes(), Constants.SHA_256);
				String outputFormat = convertUploadedFile.getOutputFormat();
				String outputPath = Constants.CONVERT_PATH + fileHash + Constants.DOT_SYMBOL
						+ outputFormat;
				String inputPath = Constants.CONVERT_PATH + fileHash + Constants.DOT_SYMBOL
						+ inputFormat;
				FileUtils.writeFileFromByteArray(file.getBytes(), Constants.CONVERT_PATH + fileHash
						+ Constants.DOT_SYMBOL + inputFormat);
				Runner.convertFile(inputFormat, outputFormat, inputPath, outputPath);

				DownloadForm form = new DownloadForm();
				form.setHash(fileHash);
				form.setOutputFormat(outputFormat);
				form.setFileName(originalFileName);

				ModelAndView res = new ModelAndView("converter/create");
				res.addObject("uploadedFile", new ConvertUploadFile());
				res.addObject("form", form);

				return res;
			}
		} catch (IOException e) {
			logger.error("No ha sido posible convertir el fichero", e);
			throw new RoisinException("Could not convert file");
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<byte[]> download(@ModelAttribute DownloadForm form) {

		String filePath = Constants.CONVERT_PATH + form.getHash() + Constants.DOT_SYMBOL
				+ form.getOutputFormat();
		String exportFileName = form.getFileName() + Constants.DOT_SYMBOL + form.getOutputFormat();
		byte[] fileContent = FileUtils.getFileFromPath(filePath);
		// Create and configure headers to return the file
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/" + form.getOutputFormat()));
		headers.setContentDispositionFormData(exportFileName, exportFileName);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileContent, headers,
				HttpStatus.OK);

		return response;
	}

}
