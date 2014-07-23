package com.roisin.spring.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.model.File;
import com.roisin.spring.model.User;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.UserService;

@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int fileId) {
		File file = fileService.findOne(fileId);
		fileService.delete(file);

		User user = userService.findByPrincipal();
		Collection<File> files = fileService.findFilesByUserId(user.getId());

		ModelAndView res = new ModelAndView("data/list");
		res.addObject("files", files);
		res.addObject("requestURI", "list");

		return res;
	}

}
