package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Examples;
import com.roisin.spring.repositories.ExamplesRepository;

@Service
@Transactional
public class ExamplesService {

	@Autowired
	private ExamplesRepository exampleRepository;

	public ExamplesService() {
		super();
	}

	public Examples create() {
		Examples roisinExample = new Examples();

		return roisinExample;
	}

	public Examples findOne(int exampleId) {
		return exampleRepository.findOne(exampleId);
	}

	public void save(Examples example) {

		exampleRepository.save(example);
	}

	public Collection<Examples> findExamplesByUserId(int userId) {
		return exampleRepository.findExamplesByUserId(userId);
	}

}
