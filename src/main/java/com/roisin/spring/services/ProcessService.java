package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Process;
import com.roisin.spring.repositories.ProcessRepository;

@Service
@Transactional
public class ProcessService {

	@Autowired
	private ProcessRepository processRepository;

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
		process.setAlgorithm(algorithm);
		return save(process);
	}

	public void cleanTempProcesses(int dataId) {
		Collection<Process> nullProcesses = processRepository.findNullDataProcesses(dataId);
		for (Process process : nullProcesses) {
			delete(process);
		}
	}
}
