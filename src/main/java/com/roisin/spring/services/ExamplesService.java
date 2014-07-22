package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.repositories.ExamplesRepository;

@Service
@Transactional
public class ExamplesService {

	@Autowired
	private ExamplesRepository exampleRepository;

	public ExamplesService() {
		super();
	}

	public PreprocessedData create() {
		PreprocessedData roisinExample = new PreprocessedData();

		return roisinExample;
	}

	public PreprocessedData findOne(int exampleId) {
		return exampleRepository.findOne(exampleId);
	}

	public void save(PreprocessedData example) {

		exampleRepository.save(example);
	}

	public Collection<PreprocessedData> findExamplesByUserId(int userId) {
		return exampleRepository.findExamplesByUserId(userId);
	}

}
