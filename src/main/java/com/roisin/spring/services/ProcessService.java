package com.roisin.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Process;
import com.roisin.spring.repositories.ProcessRepository;

@Service
@Transactional
public class ProcessService {

	@Autowired
	private ProcessRepository roisinProcessRepository;

	public ProcessService() {
		super();
	}

	public Process create() {
		Process roisinProcess = new Process();

		return roisinProcess;
	}

	public Process findOne(int roisinProcessId) {
		return roisinProcessRepository.findOne(roisinProcessId);
	}

	public void save(Process roisinProcess) {

		roisinProcessRepository.save(roisinProcess);
	}
}
