package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Process;
import com.roisin.spring.model.SelectedAttribute;
import com.roisin.spring.repositories.ProcessRepository;
import com.roisin.spring.utils.Constants;

@Service
@Transactional
public class ProcessService {

	@Autowired
	private ProcessRepository processRepository;

	@Autowired
	private SelectedAttributeService selectedAttributeService;

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
		return processRepository.findOne(processId);
	}

	public Process save(Process process) {
		return processRepository.save(process);
	}

	public void delete(Process process) {
		processRepository.delete(process);
	}

	// Extra methods

	public Process saveProcessAlgorithm(Process process, String algorithm) {
		if (process.getAlgorithm().equals(Constants.ROISIN_NULL)) {
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
}
