package com.roisin.spring.services;

import static com.roisin.spring.utils.Constants.DOT_SYMBOL;
import static com.roisin.spring.utils.Constants.ROISIN_NULL;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rapidminer.example.ExampleSet;
import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.File;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.Process;
import com.roisin.spring.model.SelectedAttribute;
import com.roisin.spring.model.User;
import com.roisin.spring.repositories.ProcessRepository;
import com.roisin.spring.utils.RoisinUtils;
import com.roisin.spring.utils.Runner;

@Service
@Transactional
public class ProcessService {

	@Autowired
	private ProcessRepository processRepository;

	@Autowired
	private PreprocessedDataService preprocessedDataService;

	@Autowired
	private PreprocessingFormService preprocessingFormService;

	@Autowired
	private DeletedRowService deletedRowService;

	@Autowired
	private SelectedAttributeService selectedAttributeService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUtils fileUtils;

	public ProcessService() {
		super();
	}

	public Process create() {
		Process process = new Process();

		return process;
	}

	public Collection<Process> findAll() {
		return processRepository.findAll();
	}

	public Process findOne(int processId) {
		Assert.notNull(processId);
		User principal = userService.findByPrincipal();
		Process process = processRepository.findOne(processId);
		boolean isOwner = principal.equals(process.getPreprocessedData().getPreprocessingForm()
				.getFile().getUser());
		Assert.isTrue(isOwner);
		return process;
	}

	public Process save(Process process) {
		return processRepository.save(process);
	}

	public void delete(Process process) {
		Assert.notNull(process);
		User principal = userService.findByPrincipal();
		boolean isOwner = principal.equals(process.getPreprocessedData().getPreprocessingForm()
				.getFile().getUser());
		Assert.isTrue(isOwner);
		processRepository.delete(process);
	}

	// Extra methods

	public Process saveProcessAlgorithm(Process process, String algorithm) {
		if (process.getAlgorithm().equals(ROISIN_NULL)) {
			process.setAlgorithm(algorithm);
			return save(process);
		} else {
			Process newProcess = create();
			newProcess.setPreprocessedData(process.getPreprocessedData());
			newProcess.setAlgorithm(algorithm);
			SelectedAttribute label = process.getLabel();
			newProcess.setLabel(process.getLabel());
			newProcess.setLabel(label);
			newProcess = save(newProcess);

			return save(newProcess);
		}
	}

	public void cleanTempProcesses(int dataId) {
		Collection<Process> nullProcesses = processRepository.findNullDataProcesses(dataId);
		for (Process process : nullProcesses) {
			delete(process);
		}
	}

	public Collection<Process> findByAlgorithmAndDataId(int dataId, String algorithm) {
		return processRepository.findByAlgorithmAndDataId(dataId, algorithm);
	}

	public Collection<Process> findProcessByLabelId(int labelId) {
		return processRepository.findProcessByLabelId(labelId);
	}

	public Process createInitialProcessNoAlgorithm(PreproSimpleForm form) {
		PreprocessedData data = preprocessedDataService.findOne(Integer.parseInt(form.getDataId()));
		// Formulario
		PreprocessingForm storedForm = preprocessingFormService.saveSubmitedSimpleForm(
				data.getPreprocessingForm(), form);
		// Extracción de file de BD
		File file = storedForm.getFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), DOT_SYMBOL);
		String tmpPath = fileUtils.getStoragePath() + file.getHash() + DOT_SYMBOL + fileFormat;
		// Escritura en disco del fichero
		fileUtils.writeFileFromByteArray(file.getOriginalFile(), tmpPath);
		// Colección de deleted rows
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(storedForm
				.getId());
		// Obtención del example set resultante
		ExampleSet exampleSet = Runner.getPreprocessedExampleSetFromFile(tmpPath,
				RoisinUtils.getRowsFromDeletedRows(deletedRows), storedForm.getFilterCondition(),
				form.getProcessAttributeSelection());
		// Se almacen el example set
		data = preprocessedDataService.findOne(data.getId());
		data.setExampleSet(exampleSet);
		data.setName(form.getName());
		data.setDescription(form.getDescription());
		data = preprocessedDataService.save(data);
		// Finalmente se manda al usuario al formulario de proceso
		cleanTempProcesses(data.getId());
		// Creación del proceso
		Process process = create();
		process.setPreprocessedData(data);
		process.setAlgorithm(ROISIN_NULL);
		// Se establece la label (clase) para este proceso
		SelectedAttribute label = selectedAttributeService
				.findLabel(storedForm.getId(), form.getLabel()).iterator().next();
		process.setLabel(label);
		process = save(process);

		return process;
	}

	public Process createInitProcess(PreproSimpleForm form) {
		int dataId = Integer.parseInt(form.getDataId());
		PreprocessedData data = preprocessedDataService.findOne(dataId);
		// Finalmente se manda al usuario al formulario de proceso
		cleanTempProcesses(dataId);
		// Creación del proceso
		Process process = create();
		process.setPreprocessedData(data);
		process.setAlgorithm(ROISIN_NULL);
		// Se establece la label (clase) para este proceso
		SelectedAttribute label = selectedAttributeService
				.findLabel(data.getPreprocessingForm().getId(), form.getLabel()).iterator().next();
		process.setLabel(label);
		process = save(process);

		return process;
	}
}
