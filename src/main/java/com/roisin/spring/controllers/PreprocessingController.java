package com.roisin.spring.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.validator.FileValidator;

@Controller
@RequestMapping("/preprocessing")
public class PreprocessingController {

	private static final Logger logger = LoggerFactory.getLogger(PreprocessingController.class);

	@Autowired
	FileValidator fileValidator;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {

		ModelAndView res = new ModelAndView("preprocessing/create");

		return res;
	}

	@RequestMapping("/upload")
	public ModelAndView uploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		MultipartFile file = uploadedFile.getFile();
		fileValidator.validate(uploadedFile, result);

		String fileName = file.getOriginalFilename();

		if (result.hasErrors()) {
			return new ModelAndView("uploadForm");
		}

		try {
			inputStream = file.getInputStream();

			File newFile = new File("/Users/felix/03.TFG/pruebafiles/" + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView res = new ModelAndView("preprocessing/upload");
		res.addObject("message", "Subido");

		return res;
	}

}
