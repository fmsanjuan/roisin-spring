package com.roisin.spring.controllers;

import java.util.List;

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

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.services.PreprocessingService;
import com.roisin.spring.validator.FileValidator;
import com.roisin.spring.validator.PreprocessingFormValidator;

@Controller
@RequestMapping("/preprocessing")
public class PreprocessingController {

	private static final Logger logger = LoggerFactory.getLogger(PreprocessingController.class);

	private static String filePath = StringUtils.EMPTY;

	@Autowired
	FileValidator fileValidator;

	@Autowired
	PreprocessingFormValidator formValidator;

	@Autowired
	PreprocessingService preprocessingService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {

		ModelAndView res = new ModelAndView("preprocessing/create");
		res.addObject("uploaded", false);

		return res;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {

		MultipartFile file = uploadedFile.getFile();
		fileValidator.validate(uploadedFile, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("preprocessing/create");
			res.addObject("error", true);
			res.addObject("uploaded", false);
			return res;
		} else {
			filePath = "/Users/felix/03.TFG/pruebafiles/" + file.getOriginalFilename();
			ExampleSet exampleSet = preprocessingService.getExampleSetFromFile(file, filePath);
			List<Example> examples = preprocessingService.getExampleListFromExampleSet(exampleSet);
			Attribute[] attributes = exampleSet.getExampleTable().getAttributes();
			// Creación del formulario vacío
			PreprocessingForm form = new PreprocessingForm();
			form.setFilePath(filePath);
			form.setAttributeSelection(preprocessingService
					.getAttributeNameListFromExampleSet(attributes));
			form.setExampleSetSize(examples.size());

			ModelAndView res = new ModelAndView("preprocessing/upload");
			res.addObject("uploaded", true);
			res.addObject("examples", examples);
			res.addObject("attributes", attributes);
			res.addObject("form", form);

			return res;
		}
	}

	@RequestMapping(value = "/processData", method = RequestMethod.POST)
	public ModelAndView uploaded(PreprocessingForm form, BindingResult result) {

		formValidator.validate(form, result);

		if (result.hasErrors()) {

			ExampleSet exampleSet = preprocessingService.getExampleSetFromFilePath(filePath);
			List<Example> examples = preprocessingService.getExampleListFromExampleSet(exampleSet);
			Attribute[] attributes = exampleSet.getExampleTable().getAttributes();

			ModelAndView res = new ModelAndView("preprocessing/create");
			res.addObject("uploaded", true);
			res.addObject("form", form);
			res.addObject("examples", examples);
			res.addObject("attributes", attributes);
			res.addObject("error", true);
			return res;
		} else {
			form = preprocessingService.calculateFilterCondition(form);
			ModelAndView res = new ModelAndView("processing/create");
			res.addObject("form", form);
			return res;
		}
	}
}
