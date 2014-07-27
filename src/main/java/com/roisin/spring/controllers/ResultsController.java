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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.roisin.spring.services.ResultsService;

@Controller
@RequestMapping("/results")
public class ResultsController {

	private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);

	@Autowired
	private ResultsService resultsService;

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ResponseEntity<byte[]> exportResults(@RequestParam int resultsId) {

		ByteArrayOutputStream document = resultsService.getExcelResults(resultsId);

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
