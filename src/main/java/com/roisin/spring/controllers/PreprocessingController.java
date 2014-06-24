package com.roisin.spring.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.google.common.collect.Lists;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.OperatorException;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.services.PreprocessingService;
import com.roisin.spring.utils.Constants;
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

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
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
		// Listado de ejemplos
		List<Example> examples = Lists.newArrayList();
		for (int i = 0; i < exampleSet.getExampleTable().size(); i++) {
			examples.add(exampleSet.getExample(i));
		}
		// Listado de atributos seleccionados, en un principio se deben
		// seleccionar todos
		Attribute[] attributes = exampleSet.getExampleTable().getAttributes();
		List<String> attributeSelection = Lists.newArrayList();
		for (int i = 0; i < attributes.length; i++) {
			attributeSelection.add(attributes[i].getName());
		}
		// Creación del formulario vacío
		PreprocessingForm form = new PreprocessingForm();
		form.setFilePath(filePath);
		form.setDeletedAttributes(attributeSelection);

		ModelAndView res = new ModelAndView("preprocessing/upload");
		res.addObject("uploaded", true);
		res.addObject("examples", examples);
		res.addObject("attributes", attributes);
		res.addObject("form", form);

		return res;
	}

	@RequestMapping(value = "/processData", method = RequestMethod.POST)
	public ModelAndView uploaded(@ModelAttribute("form") PreprocessingForm form,
			BindingResult result) {

		// Transformación de condición provisional
		if (!StringUtils.isBlank(form.getFilterValue())) {
			StringBuilder condition = new StringBuilder();
			condition.append(form.getFilterAttribute());
			if (form.getFilterOperator().equals(Constants.EQUALS)) {
				condition.append(Constants.EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.NON_EQUALS)) {
				condition.append(Constants.NON_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_OR_EQUALS)) {
				condition.append(Constants.GREATER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_OR_EQUALS)) {
				condition.append(Constants.SMALLER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_THAN)) {
				condition.append(Constants.SMALLER_THAN_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_THAN)) {
				condition.append(Constants.GREATER_THAN_SYMBOL);
			}
			condition.append(form.getFilterValue());
			form.setFilterCondition(condition.toString());
		}

		ModelAndView res = new ModelAndView("processing/create");
		res.addObject("uploaded", true);
		res.addObject("form", form);
		return res;
	}
}
