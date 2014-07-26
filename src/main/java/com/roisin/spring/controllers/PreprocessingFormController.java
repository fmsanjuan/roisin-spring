package com.roisin.spring.controllers;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.File;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.Process;
import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.model.SelectedAttribute;
import com.roisin.spring.model.SubgroupSettings;
import com.roisin.spring.model.TreeToRulesSettings;
import com.roisin.spring.services.DeletedRowService;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.PreprocessedDataService;
import com.roisin.spring.services.PreprocessingFormService;
import com.roisin.spring.services.ProcessService;
import com.roisin.spring.services.RipperSettingsService;
import com.roisin.spring.services.SelectedAttributeService;
import com.roisin.spring.services.SubgroupSettingsService;
import com.roisin.spring.services.TreeToRulesSettingsService;
import com.roisin.spring.utils.Constants;
import com.roisin.spring.utils.FileUtils;
import com.roisin.spring.utils.RoisinUtils;
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

	@Autowired
	private SelectedAttributeService selectedAttributeService;

	@Autowired
	private DeletedRowService deletedRowService;

	@Autowired
	private ProcessService processService;

	@Autowired
	private RipperSettingsService ripperSettingsService;

	@Autowired
	private SubgroupSettingsService subgroupSettingsService;

	@Autowired
	private TreeToRulesSettingsService treeToRulesSettingsService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int fileId) {

		File file = fileService.findOne(fileId);
		byte[] fileArray = file.getOriginalFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
		String tmpPath = Constants.STORAGE_PATH + file.getHash() + Constants.DOT_SYMBOL
				+ fileFormat;

		FileUtils.writeFileFromByteArray(fileArray, tmpPath);
		ExampleSet exampleSet = Runner.getExampleSetFromFile(fileFormat, tmpPath);
		List<Example> examples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		Attribute[] attributes = exampleSet.getExampleTable().getAttributes();

		PreprocessingForm preform = preprocessingFormService.create();
		preform.setFile(file);
		preform = preprocessingFormService.save(preform);

		PreprocessedData data = preprocessedDataService.create();
		data.setName("roisinnull");
		data.setDescription("roisinnull");
		data.setExampleSet(exampleSet);
		data.setPreprocessingForm(preform);
		data = preprocessedDataService.save(data);

		PreproSimpleForm form = new PreproSimpleForm();
		form.setAttributeSelection(RoisinUtils.getAttributeNameListFromExampleSet(attributes));

		String dataParam = "dataId=" + data.getId();

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject("examples", examples);
		res.addObject("attributes", attributes);
		res.addObject("form", form);
		res.addObject("dataParam", dataParam);
		res.addObject("requestURI", "list?dataId=" + data.getId() + "&formId=" + preform.getId());

		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int dataId, @RequestParam int formId) {

		PreprocessedData data = preprocessedDataService.findOne(dataId);
		ExampleSet exampleSet = data.getExampleSet();
		List<Example> examples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		Attribute[] attributes = exampleSet.getExampleTable().getAttributes();

		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(formId);
		if (!deletedRows.isEmpty()) {
			List<Integer> deletedRowValues = RoisinUtils.getDeletedRowValues(deletedRows);
			for (Integer deletedRowValue : deletedRowValues) {
				examples.remove(deletedRowValue);
			}
		}

		PreproSimpleForm form = new PreproSimpleForm();
		form.setAttributeSelection(RoisinUtils.getAttributeNameListFromExampleSet(attributes));

		String dataParam = "dataId=" + dataId;

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject("examples", examples);
		res.addObject("attributes", attributes);
		res.addObject("form", form);
		res.addObject("dataParam", dataParam);
		res.addObject("requestURI", "list?" + dataParam);

		return res;
	}

	@RequestMapping(value = "/deleterow", method = RequestMethod.GET)
	public ModelAndView deleterow(@RequestParam int dataId, @RequestParam int rowId) {
		PreprocessingForm form = preprocessingFormService.findFormByDataId(dataId);
		int formId = form.getId();
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(formId);
		// Se recupera el conjunto de datos de ejemplo
		PreprocessedData data = preprocessedDataService.findOne(dataId);
		ExampleSet exampleSet = data.getExampleSet();
		// Lista original
		List<Example> originalExamples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		// Lista modificable
		List<Example> examples = Lists.newArrayList(originalExamples);
		Attribute[] attributes = exampleSet.getExampleTable().getAttributes();
		// Creación de deletedrow
		DeletedRow deletedRow = deletedRowService.create();
		if (deletedRows.isEmpty()) {
			// Si es el primero, sabemos sin problema cuál es el rowId
			deletedRow.setNumber(rowId + 1);
			examples.remove(rowId);
		} else {
			// Ecc, eliminamos de la lista todas las filas en el mismo orden que
			// se almacenaron.
			for (DeletedRow dRow : deletedRows) {
				examples.remove(originalExamples.get(dRow.getNumber() - 1));
			}
			// Obtenemos el ejemplo concreto que se ha soliticado eliminar en
			// esta ocasión.
			Example exampleToDelete = examples.get(rowId);
			// Obtenemos su el id real del ejemplo a eliminar
			int realRowId = originalExamples.indexOf(exampleToDelete);
			// Se elimina de la lista result
			examples.remove(exampleToDelete);
			// Guardamos el id real
			deletedRow.setNumber(realRowId + 1);
		}
		deletedRow.setPreprocessingForm(form);
		deletedRowService.save(deletedRow);

		PreproSimpleForm preproForm = new PreproSimpleForm();
		preproForm
				.setAttributeSelection(RoisinUtils.getAttributeNameListFromExampleSet(attributes));

		String dataParam = "dataId=" + dataId;

		ModelAndView res = new ModelAndView("preform/list");
		res.addObject("examples", examples);
		res.addObject("attributes", attributes);
		res.addObject("form", preproForm);
		res.addObject("dataParam", dataParam);
		res.addObject("requestURI", "list?" + dataParam);

		return res;
	}

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public ModelAndView process(@ModelAttribute PreproSimpleForm form) {

		// Aquí en primer lugar se tienen que guardar los datos filtrados.
		int dataId = Integer.parseInt((StringUtils.substringAfterLast(form.getDataParam(),
				Constants.EQUALS_SYMBOL)));
		PreprocessedData data = preprocessedDataService.findOne(dataId);
		// Formulario
		PreprocessingForm storedForm = data.getPreprocessingForm();
		String filterCondition = RoisinUtils.calculateFilterCondition(form);
		storedForm.setFilterCondition(filterCondition);
		storedForm = preprocessingFormService.save(storedForm);
		// Attributos seleccionados
		for (String attributeName : form.getAttributeSelection()) {
			SelectedAttribute sa = selectedAttributeService.create();
			sa.setPreprocessingForm(storedForm);
			sa.setName(attributeName);
			selectedAttributeService.save(sa);
		}
		// Extracción de file de BD
		File file = storedForm.getFile();
		byte[] fileArray = file.getOriginalFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
		String tmpPath = Constants.STORAGE_PATH + file.getHash() + Constants.DOT_SYMBOL
				+ fileFormat;
		// Escritura en disco del fichero
		FileUtils.writeFileFromByteArray(fileArray, tmpPath);
		// Colección de deleted rows
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(storedForm
				.getId());
		// Obtención del example set resultante
		ExampleSet exampleSet = Runner.getPreprocessedExampleSetFromFile(tmpPath,
				RoisinUtils.getRowsFromDeletedRows(deletedRows), filterCondition,
				form.getAttributeSelection());
		// Se almacen el example set
		data = preprocessedDataService.findOne(dataId);
		data.setExampleSet(exampleSet);
		data = preprocessedDataService.save(data);
		// Finalmente se manda al usuario al formulario de proceso

		// Creación del proceso
		Process process = processService.create();
		process.setLabel(selectedAttributeService.findLabel(storedForm.getId(), form.getLabel()));
		process.setPreprocessedData(data);
		process.setAlgorithm("roisinnull");
		process = processService.save(process);
		// Creación de los formularios
		RipperSettings ripperSettings = ripperSettingsService.create();
		SubgroupSettings subgroupSettings = subgroupSettingsService.create();
		TreeToRulesSettings treeToRulesSettings = treeToRulesSettingsService.create();

		ModelAndView res = new ModelAndView("process/create");
		res.addObject("ripperSettings", ripperSettings);
		res.addObject("subgroupSettings", subgroupSettings);
		res.addObject("treeToRulesSettings", treeToRulesSettings);

		return res;
	}
}
