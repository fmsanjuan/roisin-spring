package com.roisin.spring.controllers;

import static com.roisin.spring.utils.Constants.ERROR;
import static com.roisin.spring.utils.ModelViewConstants.ATTRIBUTES_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.DATA_ID_FORM;
import static com.roisin.spring.utils.ModelViewConstants.DATA_LIST;
import static com.roisin.spring.utils.ModelViewConstants.ERROR_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.EXAMPLES_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.FILE_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.FORMS_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.FORM_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.LIST_LOWER_CASE;
import static com.roisin.spring.utils.ModelViewConstants.REQUEST_URI;
import static com.roisin.spring.utils.ModelViewConstants.RIPPER_SETTINGS;
import static com.roisin.spring.utils.ModelViewConstants.SUBGROUP_SETTINGS;
import static com.roisin.spring.utils.ModelViewConstants.TREE_SETTINGS;

import java.util.Collection;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.spring.forms.DataIdForm;
import com.roisin.spring.forms.DataViewForm;
import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.File;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.Process;
import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.model.SubgroupSettings;
import com.roisin.spring.model.TreeToRulesSettings;
import com.roisin.spring.services.DeletedRowService;
import com.roisin.spring.services.FileService;
import com.roisin.spring.services.FileUtils;
import com.roisin.spring.services.PreprocessedDataService;
import com.roisin.spring.services.PreprocessingFormService;
import com.roisin.spring.services.ProcessService;
import com.roisin.spring.services.RipperSettingsService;
import com.roisin.spring.services.SubgroupSettingsService;
import com.roisin.spring.services.TreeToRulesSettingsService;
import com.roisin.spring.utils.RoisinUtils;
import com.roisin.spring.utils.Runner;
import com.roisin.spring.validator.PreproSimpleFormValidator;

@Controller
@RequestMapping("/data")
public class PreprocessedDataController {

	@Autowired
	private PreprocessedDataService preprocessedDataService;

	@Autowired
	private PreprocessingFormService preprocessingFormService;

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

	@Autowired
	private FileService fileService;

	@Autowired
	private PreproSimpleFormValidator psfValidator;
	
	@Autowired
	private FileUtils fileUtils;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int fileId) {

		File file = fileService.findOne(fileId);
		Collection<PreprocessedData> forms = preprocessedDataService.findDataByFileId(fileId);
		ModelAndView res = new ModelAndView("data/list");
		res.addObject(FORMS_LOWER_CASE, forms);
		res.addObject(FILE_LOWER_CASE, file);
		res.addObject(DATA_ID_FORM, new DataIdForm());
		res.addObject(REQUEST_URI, "list?fileId=" + fileId);

		return res;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public ModelAndView viewFilePreprocessedData(@ModelAttribute DataViewForm form) {

		File file = fileService.findOne(form.getFileId());
		Collection<PreprocessedData> nullData = preprocessedDataService.findNullData(form
				.getFileId());
		preprocessedDataService.deleteNullData(nullData);
		Collection<PreprocessedData> forms = preprocessedDataService.findDataByFileId(form
				.getFileId());

		ModelAndView res = new ModelAndView("data/list");
		res.addObject(FORMS_LOWER_CASE, forms);
		res.addObject(FILE_LOWER_CASE, file);
		res.addObject(DATA_ID_FORM, new DataIdForm());
		res.addObject(REQUEST_URI, "list?fileId=" + form.getFileId());

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int dataId) {

		PreprocessedData data = preprocessedDataService.findOne(dataId);
		ExampleSet exampleSet = data.getExampleSet();
		List<Example> examples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		Attribute[] attributes = exampleSet.getExampleTable().getAttributes();
		PreproSimpleForm form = new PreproSimpleForm();
		form.setDataId(String.valueOf(dataId));

		ModelAndView res = new ModelAndView("data/details");
		res.addObject(EXAMPLES_LOWER_CASE, examples);
		res.addObject(ATTRIBUTES_LOWER_CASE, attributes);
		res.addObject(FORM_LOWER_CASE, form);
		res.addObject(REQUEST_URI, "details?dataId=" + dataId);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Collection<PreprocessedData> dataList = preprocessedDataService.findAll();
		ModelAndView res = new ModelAndView("data/list");
		res.addObject(DATA_LIST, dataList);
		res.addObject(REQUEST_URI, LIST_LOWER_CASE);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = { "process" })
	public ModelAndView process(@ModelAttribute PreproSimpleForm form, BindingResult result,
			RedirectAttributes redirect) {

		psfValidator.validateProcess(form, result);

		if (result.hasErrors()) {
			for (FieldError fieldError : result.getFieldErrors()) {
				redirect.addFlashAttribute(fieldError.getField() + ERROR,
						fieldError.getDefaultMessage());
			}
			redirect.addFlashAttribute(ERROR_LOWER_CASE, true);
			ModelAndView res = new ModelAndView("redirect:/preform/list?dataId=" + form.getDataId());
			return res;
		} else {

			Process process = processService.createInitialProcessNoAlgorithm(form);
			// Creación de los formularios
			RipperSettings ripperSettings = ripperSettingsService.create();
			ripperSettings.setProcess(process);
			SubgroupSettings subgroupSettings = subgroupSettingsService.create();
			subgroupSettings.setProcess(process);
			TreeToRulesSettings treeToRulesSettings = treeToRulesSettingsService.create();
			treeToRulesSettings.setProcess(process);

			ModelAndView res = new ModelAndView("process/create");
			res.addObject(RIPPER_SETTINGS, ripperSettings);
			res.addObject(SUBGROUP_SETTINGS, subgroupSettings);
			res.addObject(TREE_SETTINGS, treeToRulesSettings);

			return res;
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = { "export" })
	public ResponseEntity<byte[]> export(@ModelAttribute PreproSimpleForm form) {

		PreprocessedData data = preprocessedDataService.findOne(Integer.parseInt(form.getDataId()));
		// Formulario
		PreprocessingForm storedForm = preprocessingFormService.saveSubmitedSimpleFormExport(
				data.getPreprocessingForm(), form);
		// Extracción de file de BD
		File file = storedForm.getFile();
		String exportFormat = form.getExportFormat();
		String exportFileName = FileUtils.getExportFileName(file.getName(), exportFormat);
		String tmpPath = fileUtils.getFileTmpPath(file);
		// Escritura en disco del fichero
		fileUtils.writeFileFromByteArray(file.getOriginalFile(), tmpPath);
		// Colección de deleted rows
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(storedForm
				.getId());
		// Obtención del example set resultante
		ByteArrayOutputStream document = Runner.exportData(tmpPath,
				RoisinUtils.getRowsFromDeletedRows(deletedRows), storedForm.getFilterCondition(),
				form.getExportAttributeSelection(),
				fileUtils.getFileDownloadPath(file, exportFormat));
		// Create and configure headers to return the file
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/" + exportFormat));
		headers.setContentDispositionFormData(exportFileName, exportFileName);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(document.toByteArray(),
				headers, HttpStatus.OK);

		return response;
	}

}
