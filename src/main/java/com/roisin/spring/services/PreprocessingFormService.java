package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.repositories.PreprocessingFormRepository;

@Service
@Transactional
public class PreprocessingFormService {

	@Autowired
	private PreprocessingFormRepository preprocessingFormRepository;

	public PreprocessingFormService() {
		super();
	}

	public PreprocessingForm create() {
		PreprocessingForm preprocessingForm = new PreprocessingForm();

		return preprocessingForm;
	}

	public Collection<PreprocessingForm> findAll() {
		return preprocessingFormRepository.findAll();
	}

	public PreprocessingForm findOne(int preprocessingFormId) {
		return preprocessingFormRepository.findOne(preprocessingFormId);
	}

	public void save(PreprocessingForm preprocessingForm) {
		preprocessingFormRepository.save(preprocessingForm);
	}

	public void delete(PreprocessingForm preprocessingForm) {
		preprocessingFormRepository.delete(preprocessingForm);
	}

}
