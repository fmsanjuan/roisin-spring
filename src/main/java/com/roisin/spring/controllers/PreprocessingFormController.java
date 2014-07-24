package com.roisin.spring.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rapidminer.example.ExampleSet;
import com.roisin.spring.model.File;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.PreprocessedDataService;
import com.roisin.spring.services.PreprocessingFormService;
import com.roisin.spring.utils.Constants;
import com.roisin.spring.utils.FileUtils;
import com.roisin.spring.utils.Runner;

@Controller
@RequestMapping("/preform")
public class PreprocessingFormController {

	@Autowired
	private PreprocessingFormService preprocessingFormService;

	@Autowired
	private FileService fileService;

	@Autowired
	private PreprocessedDataService preprocessedDataService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int fileId) {

		File file = fileService.findOne(fileId);
		byte[] fileArray = file.getOriginalFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
		String tmpPath = "/Users/felix/03.TFG/pruebafiles/" + file.getHash() + Constants.DOT_SYMBOL
				+ fileFormat;

		FileUtils.writeFileFromByteArray(fileArray, tmpPath);
		ExampleSet examples = Runner.getExampleSetFromFile(fileFormat, tmpPath);

		PreprocessedData data = preprocessedDataService.create();
		data.setName("roisinnull");
		data.setDescription("roisinnull");
		data.setExampleSet(examples);
		preprocessedDataService.save(data);

		ModelAndView res = new ModelAndView("data/list");
		res.addObject("examples", examples);
		res.addObject("requestURI", "list");

		return res;
	}
}
