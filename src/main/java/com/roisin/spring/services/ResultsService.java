package com.roisin.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Results;
import com.roisin.spring.repositories.ResultsRepository;

@Service
@Transactional
public class ResultsService {

	@Autowired
	private ResultsRepository roisinResultsRepository;

	public ResultsService() {
		super();
	}

	public Results create() {
		Results roisinResults = new Results();

		return roisinResults;
	}

	public Results findOne(int roisinResultsId) {
		return roisinResultsRepository.findOne(roisinResultsId);
	}

	public void save(Results roisinResults) {

		roisinResultsRepository.save(roisinResults);
	}
}
