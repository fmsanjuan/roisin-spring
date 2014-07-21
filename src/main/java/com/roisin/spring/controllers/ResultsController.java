package com.roisin.spring.controllers;

import org.apache.commons.io.output.ByteArrayOutputStream;
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
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.forms.PreprocessingForm;
import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.services.ResultsService2;

@Controller
@RequestMapping("/results")
public class ResultsController {

	private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);

	@Autowired
	private ResultsService2 resultService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {

		ModelAndView res = new ModelAndView("preprocessing/create");
		res.addObject("uploaded", false);

		return res;
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportResults(@ModelAttribute("form") PreprocessingForm form,
			BindingResult result) {

		ByteArrayOutputStream document = resultService.getExcelResults(form);

		// Create and configure headers to return the file
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/xls"));
		headers.setContentDispositionFormData("prueba", "prueba.xls");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(document.toByteArray(),
				headers, HttpStatus.OK);

		return response;
	}
}
