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

	/**
	 * Process repository
	 */
	@Autowired
	private transient ProcessRepository processRepository;

	/**
	 * Preprocessed data service
	 */
	@Autowired
	private transient PreprocessedDataService preproDataService;

	/**
	 * Preprocessing form service
	 */
	@Autowired
	private transient PreprocessingFormService preproFormService;

	/**
	 * Deleted Row Service
	 */
	@Autowired
	private transient DeletedRowService deletedRowService;

	/**
	 * Selected Attribute Service
	 */
	@Autowired
	private transient SelectedAttributeService saService;

	/**
	 * User service
	 */
	@Autowired
	private transient UserService userService;

	/**
	 * File Utils
	 */
	@Autowired
	private transient FileUtils fileUtils;

	public ProcessService() {
		super();
	}

	public Process create() {
		final Process process = new Process();

		return process;
	}

	public Collection<Process> findAll() {
		return processRepository.findAll();
	}

	public Process findOne(final int processId) {
		Assert.notNull(processId);
		final User principal = userService.findByPrincipal();
		final Process process = processRepository.findOne(processId);
		boolean isOwner = principal.equals(process.getPreprocessedData().getPreprocessingForm()
				.getFile().getUser());
		Assert.isTrue(isOwner);
		return process;
	}

	public Process save(final Process process) {
		return processRepository.save(process);
	}

	public void delete(final Process process) {
		Assert.notNull(process);
		final User principal = userService.findByPrincipal();
		final boolean isOwner = principal.equals(process.getPreprocessedData()
				.getPreprocessingForm().getFile().getUser());
		Assert.isTrue(isOwner);
		processRepository.delete(process);
	}

	// Extra methods

	public Process saveProcessAlgorithm(final Process process, final String algorithm) {
		if (process.getAlgorithm().equals(ROISIN_NULL)) {
			process.setAlgorithm(algorithm);
			return save(process);
		} else {
			Process newProcess = create();
			newProcess.setPreprocessedData(process.getPreprocessedData());
			newProcess.setAlgorithm(algorithm);
			final SelectedAttribute label = process.getLabel();
			newProcess.setLabel(process.getLabel());
			newProcess.setLabel(label);
			newProcess = save(newProcess);

			return save(newProcess);
		}
	}

	public void cleanTempProcesses(final int dataId) {
		final Collection<Process> nullProcesses = processRepository.findNullDataProcesses(dataId);
		for (final Process process : nullProcesses) {
			delete(process);
		}
	}

	public Collection<Process> findByAlgorithmAndDataId(final int dataId, final String algorithm) {
		return processRepository.findByAlgorithmAndDataId(dataId, algorithm);
	}

	public Collection<Process> findProcessByLabelId(final int labelId) {
		return processRepository.findProcessByLabelId(labelId);
	}

	public Process createInitialProcessNoAlgorithm(final PreproSimpleForm form) {
		PreprocessedData data = preproDataService.findOne(Integer.parseInt(form.getDataId()));
		// Formulario
		PreprocessingForm storedForm = preproFormService.saveSubmitedSimpleForm(
				data.getPreprocessingForm(), form);
		// Extracción de file de BD
		final File file = storedForm.getFile();
		final String fileFormat = StringUtils.substringAfterLast(file.getName(), DOT_SYMBOL);
		final String tmpPath = fileUtils.getStoragePath() + file.getHash() + DOT_SYMBOL
				+ fileFormat;
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
		data = preproDataService.findOne(data.getId());
		data.setExampleSet(exampleSet);
		data.setName(form.getName());
		data.setDescription(form.getDescription());
		data = preproDataService.save(data);
		// Finalmente se manda al usuario al formulario de proceso
		cleanTempProcesses(data.getId());
		// Creación del proceso
		Process process = create();
		process.setPreprocessedData(data);
		process.setAlgorithm(ROISIN_NULL);
		// Se establece la label (clase) para este proceso
		SelectedAttribute label = saService.findLabel(storedForm.getId(), form.getLabel())
				.iterator().next();
		process.setLabel(label);
		process = save(process);

		return process;
	}

	public Process createInitProcess(final PreproSimpleForm form) {
		final int dataId = Integer.parseInt(form.getDataId());
		final PreprocessedData data = preproDataService.findOne(dataId);
		// Finalmente se manda al usuario al formulario de proceso
		cleanTempProcesses(dataId);
		// Creación del proceso
		Process process = create();
		process.setPreprocessedData(data);
		process.setAlgorithm(ROISIN_NULL);
		// Se establece la label (clase) para este proceso
		SelectedAttribute label = saService
				.findLabel(data.getPreprocessingForm().getId(), form.getLabel()).iterator().next();
		process.setLabel(label);
		process = save(process);

		return process;
	}
}
