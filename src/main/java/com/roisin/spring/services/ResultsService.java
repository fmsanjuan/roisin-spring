package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Results;
import com.roisin.spring.repositories.ResultsRepository;

@Service
@Transactional
public class ResultsService {

	@Autowired
	private ResultsRepository resultsRepository;

	public ResultsService() {
		super();
	}

	public Results create() {
		Results results = new Results();

		return results;
	}

	public Collection<Results> findAll() {
		return resultsRepository.findAll();
	}

	public Results findOne(int resultsId) {
		return resultsRepository.findOne(resultsId);
	}

	public Results save(Results results) {
		return resultsRepository.save(results);
	}

	public void delete(Results results) {
		resultsRepository.delete(results);
	}
}
