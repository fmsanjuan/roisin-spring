package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.SelectedAttribute;
import com.roisin.spring.repositories.PreprocessingFormRepository;

@Service
@Transactional
public class PreprocessingFormService {

	@Autowired
	private PreprocessingFormRepository preprocessingFormRepository;

	@Autowired
	SelectedAttributeService selectedAttributeService;

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

	public PreprocessingForm save(PreprocessingForm preprocessingForm) {
		return preprocessingFormRepository.save(preprocessingForm);
	}

	public void delete(PreprocessingForm preprocessingForm) {
		preprocessingFormRepository.delete(preprocessingForm);
	}

	// Extra methods

	public PreprocessingForm findFormByDataId(int dataId) {
		return preprocessingFormRepository.findFormByDataId(dataId);
	}

	public PreprocessingForm saveSubmitedSimpleForm(PreprocessingForm storedForm,
			PreproSimpleForm form) {
		// Borrado de attributos seleccionados en caso de que ya existan
		Collection<SelectedAttribute> selectedList = selectedAttributeService
				.findSelectedAttributesByFormId(storedForm.getId());
		for (SelectedAttribute selectedAttribute : selectedList) {
			selectedAttributeService.delete(selectedAttribute);
		}
		// Attributos seleccionados
		for (String attributeName : form.getAttributeSelection()) {
			SelectedAttribute sa = selectedAttributeService.create();
			sa.setPreprocessingForm(storedForm);
			sa.setName(attributeName);
			selectedAttributeService.save(sa);
		}

		return storedForm;
	}

	public Collection<PreprocessingForm> findFormsByFileId(int fileId) {
		return preprocessingFormRepository.findFormsByFileId(fileId);
	}

}
