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

	private static final String PREFORM_LIST = "preform/list";

	/**
	 * File service
	 */
	@Autowired
	private transient FileService fileService;

	/**
	 * Preprocessing form service
	 */
	@Autowired
	private transient PreprocessingFormService preproFormService;

	/**
	 * Preprocessed data service
	 */
	@Autowired
	private transient PreprocessedDataService preproDataService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fileId) throws NamingException {
		final File file = fileService.findOne(fileId);
		final ExampleSet exampleSet = fileService.getExampleSetFromFile(file);
		final List<Example> examples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		final Attribute[] attributes = exampleSet.getExampleTable().getAttributes();

		final PreprocessingForm preform = preproFormService
				.createSavePreprocessingFormFromFile(file);

		final PreprocessedData data = preproDataService.createSavePreprocessedData(exampleSet,
				preform);

		final PreproSimpleForm form = preproFormService
				.loadAttributesInPreproSimpleForm(attributes);

		final FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(data.getId());

		final ModelAndView res = new ModelAndView(PREFORM_LIST);
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
	public ModelAndView list(@RequestParam final int dataId) {

		final List<Example> examples = preproDataService.getExamplesFromPreprocessedData(dataId);
		final Attribute[] attributes = preproDataService.getAttributesFromPreprocessedData(dataId);

		final PreproSimpleForm form = preproFormService
				.loadAttributesInPreproSimpleForm(attributes);

		final FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(dataId);

		final ModelAndView res = new ModelAndView(PREFORM_LIST);
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
	public ModelAndView deleterow(@RequestParam final int dataId, @RequestParam final int rowId) {
		final List<Example> examples = preproDataService.getExamplesWithoutDeletedRows(dataId,
				rowId);
		final Attribute[] attributes = preproDataService.getAttributesFromPreprocessedData(dataId);

		PreproSimpleForm preproForm = preproFormService
				.loadAttributesInPreproSimpleForm(attributes);

		final FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(dataId);

		final ModelAndView res = new ModelAndView(PREFORM_LIST);
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
	public ModelAndView filterNumerical(@ModelAttribute final FilterConditionForm form) {
		final Attribute[] attributes = preproDataService.getAttributesFromPreprocessedData(form
				.getDataId());
		final Attribute filterAttribute = preproFormService.loadFilterAttribute(attributes, form);
		final List<Example> examples = preproDataService.getNumericalFilteredExamples(form,
				filterAttribute);

		PreproSimpleForm preproForm = preproFormService
				.loadAttributesInPreproSimpleForm(attributes);

		final FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(form.getDataId());

		final ModelAndView res = new ModelAndView(PREFORM_LIST);
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
	public ModelAndView filterNominal(@ModelAttribute final FilterConditionForm form) {

		final PreprocessingForm storedForm = preproFormService.findFormByDataId(form.getDataId());
		Attribute[] attributes = preproDataService.getAttributesFromPreprocessedData(form
				.getDataId());
		final Attribute filterAttribute = preproFormService.loadFilterAttribute(attributes, form);
		List<Example> examples = preproDataService
				.getNominalFilteredExamples(form, filterAttribute);

		PreproSimpleForm preproForm = preproFormService
				.loadAttributesInPreproSimpleForm(attributes);

		final FilterConditionForm filterForm = new FilterConditionForm();
		filterForm.setDataId(storedForm.getId());

		final ModelAndView res = new ModelAndView(PREFORM_LIST);
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
