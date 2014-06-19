package com.roisin.spring.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.OperatorException;
import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.services.PreprocessingService;
import com.roisin.spring.validator.FileValidator;

@Controller
@RequestMapping("/preprocessing")
public class PreprocessingController {

	private static final Logger logger = LoggerFactory.getLogger(PreprocessingController.class);

	@Autowired
	FileValidator fileValidator;

	@Autowired
	PreprocessingService preprocessingService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {

		ModelAndView res = new ModelAndView("preprocessing/create");
		res.addObject("uploaded", false);

		return res;
	}

	@RequestMapping("/upload")
	public ModelAndView uploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		ExampleSet exampleSet = null;

		MultipartFile file = uploadedFile.getFile();
		fileValidator.validate(uploadedFile, result);

		String fileName = file.getOriginalFilename();
		String filePath = StringUtils.EMPTY;

		if (result.hasErrors()) {
			return new ModelAndView("uploadForm");
		}

		try {
			inputStream = file.getInputStream();
			filePath = "/Users/felix/03.TFG/pruebafiles/" + fileName;
			File newFile = new File(filePath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			exampleSet = PreprocessingService.getExampleSetFromFile(fileName, filePath);
		} catch (IOException e) {
			logger.error("Imposible subir el fichero al servidor", e);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String exampleSize = exampleSet != null ? String.valueOf(exampleSet.getExampleTable()
				.size()) : null;

		ModelAndView res = new ModelAndView("preprocessing/upload");
		res.addObject("uploaded", true);
		res.addObject("exampleSize", exampleSize);
		res.addObject("attributes", exampleSet.getExampleTable().getAttributes());
		res.addObject("examples", exampleSet.getExampleTable());

		return res;
	}
}
