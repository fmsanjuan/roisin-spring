package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.SelectedAttribute;
import com.roisin.spring.repositories.SelectedAttributeRepository;

@Service
@Transactional
public class SelectedAttributeService {

	@Autowired
	private SelectedAttributeRepository selectedAttributeRepository;

	public SelectedAttributeService() {
		super();
	}

	public SelectedAttribute create() {
		SelectedAttribute selectedAttribute = new SelectedAttribute();

		return selectedAttribute;
	}

	public SelectedAttribute findOne(int selectedAttributeId) {
		return selectedAttributeRepository.findOne(selectedAttributeId);
	}

	public SelectedAttribute save(SelectedAttribute selectedAttribute) {
		return selectedAttributeRepository.save(selectedAttribute);
	}

	public void delete(SelectedAttribute selectedAttribute) {
		selectedAttributeRepository.delete(selectedAttribute);
	}

	public Collection<SelectedAttribute> findAll() {
		return selectedAttributeRepository.findAll();
	}

	// Extra methods

	public SelectedAttribute findLabel(int formId, String label) {
		return selectedAttributeRepository.findLabel(formId, label);
	}

	public Collection<SelectedAttribute> findSelectedAttributesByFormId(int formId) {
		return selectedAttributeRepository.findSelectedAttributesByFormId(formId);
	}
}
