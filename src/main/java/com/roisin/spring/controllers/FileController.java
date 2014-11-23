package com.roisin.spring.controllers;

import static com.roisin.spring.utils.ModelViewConstants.ERROR_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.FILES_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.FORM_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.LIST_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.REQUEST_URI;
import static com.roisin.spring.utils.ModelViewConstants.SUCCESS_MESSAGE;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.roisin.spring.exception.RoisinSpringException;
import com.roisin.spring.forms.DataViewForm;
import com.roisin.spring.model.File;
import com.roisin.spring.model.UploadedFile;
import com.roisin.spring.model.User;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.UserService;
import com.roisin.spring.validator.FileValidator;

@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@Autowired
	private FileValidator fileValidator;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		return createListModelAndView(false, null);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) throws RoisinSpringException {
		MultipartFile file = uploadedFile.getFile();
		fileValidator.validate(uploadedFile, result);
		if (result.hasErrors()) {
			return createListModelAndView(true, null);
		} else {
			String successMessage = fileService.upload(file);
			return createListModelAndView(false, successMessage);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int fileId) {
		File file = fileService.findOne(fileId);
		fileService.delete(file);
		String successMessage = file.getName();

		return createListModelAndView(false, successMessage);
	}

	public ModelAndView createListModelAndView(boolean error, String successMessage) {

		User user = userService.findByPrincipal();
		Collection<File> files = fileService.findFilesByUserId(user.getId());

		ModelAndView res = new ModelAndView("file/list");
		res.addObject(FILES_LOWER_CASE, files);
		res.addObject(REQUEST_URI, LIST_LOWER_CASE);
		res.addObject(FORM_LOWER_CASE, new DataViewForm());
		res.addObject(ERROR_LOWER_CASE, error);
		res.addObject(SUCCESS_MESSAGE, successMessage);

		return res;
	}

}
