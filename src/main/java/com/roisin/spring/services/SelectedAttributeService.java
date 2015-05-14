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

	/**
	 * Selected attribute repository
	 */
	@Autowired
	private transient SelectedAttributeRepository saRepository;

	public SelectedAttributeService() {
		super();
	}

	public SelectedAttribute create() {
		final SelectedAttribute selectedAttribute = new SelectedAttribute();

		return selectedAttribute;
	}

	public SelectedAttribute findOne(final int saId) {
		return saRepository.findOne(saId);
	}

	public SelectedAttribute save(final SelectedAttribute selectedAttribute) {
		return saRepository.save(selectedAttribute);
	}

	public void delete(final SelectedAttribute selectedAttribute) {
		saRepository.delete(selectedAttribute);
	}

	public Collection<SelectedAttribute> findAll() {
		return saRepository.findAll();
	}

	// Extra methods

	public Collection<SelectedAttribute> findLabel(final int formId, final String label) {
		return saRepository.findLabel(formId, label);
	}

	public Collection<SelectedAttribute> findSelectedAttributesByFormId(final int formId) {
		return saRepository.findSelectedAttributesByFormId(formId);
	}

}
