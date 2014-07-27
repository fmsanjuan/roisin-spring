package com.roisin.spring.controllers;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.model.File;
import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.model.User;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.UserService;
import com.roisin.spring.utils.Constants;
import com.roisin.spring.utils.HashUtils;
import com.roisin.spring.validator.FileValidator;

@Controller
@RequestMapping("/file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@Autowired
	private FileValidator fileValidator;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		User user = userService.findByPrincipal();
		Collection<File> files = fileService.findFilesByUserId(user.getId());
		ModelAndView res = new ModelAndView("file/list");
		res.addObject("files", files);
		res.addObject("requestURI", "list");

		return res;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {

		MultipartFile file = uploadedFile.getFile();
		fileValidator.validate(uploadedFile, result);

		if (result.hasErrors()) {
			ModelAndView res = new ModelAndView("file/list");
			res.addObject("error", true);
			return res;
		} else {
			File roisinFile = fileService.create();
			roisinFile.setName(file.getOriginalFilename());
			roisinFile.setDescription("descripcion");
			try {
				String hash = HashUtils.fileChecksum(file.getBytes(), Constants.SHA_256);
				roisinFile.setHash(hash);
				roisinFile.setOriginalFile(file.getBytes());
			} catch (IOException e) {
				logger.error("No ha sido posible guardar el fichero en el servidor");
			}
			roisinFile.setUser(userService.findByPrincipal());
			fileService.save(roisinFile);

			return list();
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int fileId) {
		File file = fileService.findOne(fileId);
		fileService.delete(file);

		User user = userService.findByPrincipal();
		Collection<File> files = fileService.findFilesByUserId(user.getId());

		ModelAndView res = new ModelAndView("file/list");
		res.addObject("files", files);
		res.addObject("requestURI", "list");

		return res;
	}

}