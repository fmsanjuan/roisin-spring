package com.roisin.spring.controllers;

import static com.roisin.spring.utils.ModelViewConstants.ATTRIBUTES_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.DATA_ID;
import static com.roisin.spring.utils.ModelViewConstants.EXAMPLES_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.FILTER_CONDITION_FORM;
import static com.roisin.spring.utils.ModelViewConstants.FORM_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.HAS_NOMINAL;
import static com.roisin.spring.utils.ModelViewConstants.HAS_NUMERICAL;
import static com.roisin.spring.utils.ModelViewConstants.LIST_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.REQUEST_URI;

import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.spring.forms.FilterConditionForm;
import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.model.File;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.PreprocessedDataService;
import com.roisin.spring.services.PreprocessingFormService;
import com.roisin.spring.utils.RoisinUtils;

@Controller
@RequestMapping("/preform")
public class PreprocessingFormController {

	@Autowired
	private FileService fileService;

	@Autowired
	private PreprocessingFormService preprocessingFormService;

	@Autowired
	private PreprocessedDataService preprocessedDataService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int fileId) throws NamingException {
		File file = fileService.findOne(fileId);
		ExampleSet exampleSet = fileService.getExampleSetFromFile(file);
		List<Example> examples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		Attribute[] attributes = exampleSet.getExampleTable().getAttributes();

		PreprocessingForm preform = preprocessingFormService
				.createSavePreprocessingFormFromFile(file);

		PreprocessedData data = preprocessedDataService.createSavePreprocessedData(exampleSet,
				preform);

		PreproSimpleForm form = preprocessingFormService
				.loadAttributesInPreproSimpleForm(attributes);

		FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(data.getId());

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject(EXAMPLES_LOWER_CASE, examples);
		res.addObject(ATTRIBUTES_LOWER_CASE, attributes);
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(DATA_ID, data.getId());
		res.addObject(REQUEST_URI, LIST_LOWER_CASE);
		res.addObject(FILTER_CONDITION_FORM, filterForm);
		res.addObject(HAS_NUMERICAL, RoisinUtils.hasNumerical(attributes));
		res.addObject(HAS_NOMINAL, RoisinUtils.hasNominal(attributes));

		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int dataId) {

		List<Example> examples = preprocessedDataService.getExamplesFromPreprocessedData(dataId);
		Attribute[] attributes = preprocessedDataService.getAttributesFromPreprocessedData(dataId);

		PreproSimpleForm form = preprocessingFormService
				.loadAttributesInPreproSimpleForm(attributes);

		FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(dataId);

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject(EXAMPLES_LOWER_CASE, examples);
		res.addObject(ATTRIBUTES_LOWER_CASE, attributes);
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(DATA_ID, dataId);
		res.addObject(REQUEST_URI, LIST_LOWER_CASE);
		res.addObject(FILTER_CONDITION_FORM, filterForm);
		res.addObject(HAS_NUMERICAL, RoisinUtils.hasNumerical(attributes));
		res.addObject(HAS_NOMINAL, RoisinUtils.hasNominal(attributes));

		return res;
	}

	@RequestMapping(value = "/deleterow", method = RequestMethod.GET)
	public ModelAndView deleterow(@RequestParam int dataId, @RequestParam int rowId) {
		List<Example> examples = preprocessedDataService.getExamplesWithoutDeletedRows(dataId,
				rowId);
		Attribute[] attributes = preprocessedDataService.getAttributesFromPreprocessedData(dataId);

		PreproSimpleForm preproForm = preprocessingFormService
				.loadAttributesInPreproSimpleForm(attributes);

		FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(dataId);

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject(EXAMPLES_LOWER_CASE, examples);
		res.addObject(ATTRIBUTES_LOWER_CASE, attributes);
		res.addObject(FORM_LOWER_CASE, preproForm);
		res.addObject(DATA_ID, dataId);
		res.addObject(REQUEST_URI, LIST_LOWER_CASE);
		res.addObject(FILTER_CONDITION_FORM, filterForm);
		res.addObject(HAS_NUMERICAL, RoisinUtils.hasNumerical(attributes));
		res.addObject(HAS_NOMINAL, RoisinUtils.hasNominal(attributes));

		return res;
	}

	@RequestMapping(value = "/filternumerical", method = RequestMethod.POST)
	public ModelAndView filterNumerical(@ModelAttribute FilterConditionForm form) {
		Attribute[] attributes = preprocessedDataService.getAttributesFromPreprocessedData(form
				.getDataId());
		Attribute filterAttribute = preprocessingFormService.loadFilterAttribute(attributes, form);
		List<Example> examples = preprocessedDataService.getNumericalFilteredExamples(form,
				filterAttribute);

		PreproSimpleForm preproForm = preprocessingFormService
				.loadAttributesInPreproSimpleForm(attributes);

		FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(form.getDataId());

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject(EXAMPLES_LOWER_CASE, examples);
		res.addObject(ATTRIBUTES_LOWER_CASE, attributes);
		res.addObject(FORM_LOWER_CASE, preproForm);
		res.addObject(DATA_ID, form.getDataId());
		res.addObject(REQUEST_URI, LIST_LOWER_CASE);
		res.addObject(FILTER_CONDITION_FORM, filterForm);
		res.addObject(HAS_NUMERICAL, RoisinUtils.hasNumerical(attributes));
		res.addObject(HAS_NOMINAL, RoisinUtils.hasNominal(attributes));

		return res;
	}

	@RequestMapping(value = "/filternominal", method = RequestMethod.POST)
	public ModelAndView filterNominal(@ModelAttribute FilterConditionForm form) {

		PreprocessingForm storedForm = preprocessingFormService.findFormByDataId(form.getDataId());
		Attribute[] attributes = preprocessedDataService.getAttributesFromPreprocessedData(form
				.getDataId());
		Attribute filterAttribute = preprocessingFormService.loadFilterAttribute(attributes, form);
		List<Example> examples = preprocessedDataService.getNominalFilteredExamples(form,
				filterAttribute);

		PreproSimpleForm preproForm = preprocessingFormService
				.loadAttributesInPreproSimpleForm(attributes);

		FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(storedForm.getId());

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject(EXAMPLES_LOWER_CASE, examples);
		res.addObject(ATTRIBUTES_LOWER_CASE, attributes);
		res.addObject(FORM_LOWER_CASE, preproForm);
		res.addObject(DATA_ID, form.getDataId());
		res.addObject(REQUEST_URI, LIST_LOWER_CASE);
		res.addObject(FILTER_CONDITION_FORM, filterForm);
		res.addObject(HAS_NUMERICAL, RoisinUtils.hasNumerical(attributes));
		res.addObject(HAS_NOMINAL, RoisinUtils.hasNominal(attributes));

		return res;
	}
}
