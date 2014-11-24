package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rapidminer.example.Attribute;
import com.roisin.spring.forms.FilterConditionForm;
import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.model.File;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.Process;
import com.roisin.spring.model.SelectedAttribute;
import com.roisin.spring.model.User;
import com.roisin.spring.repositories.PreprocessingFormRepository;
import com.roisin.spring.utils.RoisinUtils;

@Service
@Transactional
public class PreprocessingFormService {

	@Autowired
	private PreprocessingFormRepository preprocessingFormRepository;

	@Autowired
	private SelectedAttributeService selectedAttributeService;

	@Autowired
	private ProcessService processService;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

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
		Assert.notNull(preprocessingFormId);
		User principal = userService.findByPrincipal();
		PreprocessingForm form = preprocessingFormRepository.findOne(preprocessingFormId);
		boolean isOwner = principal.equals(form.getFile().getUser());
		Assert.isTrue(isOwner);
		return form;
	}

	public PreprocessingForm save(PreprocessingForm preprocessingForm) {
		return preprocessingFormRepository.save(preprocessingForm);
	}

	public void delete(PreprocessingForm preprocessingForm) {
		Assert.notNull(preprocessingForm);
		User principal = userService.findByPrincipal();
		boolean isOwner = principal.equals(preprocessingForm.getFile().getUser());
		Assert.isTrue(isOwner);
		preprocessingFormRepository.delete(preprocessingForm);
	}

	// Extra methods

	public PreprocessingForm findFormByDataId(int dataId) {
		Assert.notNull(dataId);
		User principal = userService.findByPrincipal();
		PreprocessingForm form = preprocessingFormRepository.findFormByDataId(dataId);
		boolean isOwner = principal.equals(form.getFile().getUser());
		Assert.isTrue(isOwner);
		return form;
	}

	public PreprocessingForm saveSubmitedSimpleForm(PreprocessingForm storedForm,
			PreproSimpleForm form) {
		// Borrado de attributos seleccionados en caso de que ya existan
		deleteExistingSelectedAttributes(storedForm);
		// Attributos seleccionados
		for (String attributeName : form.getProcessAttributeSelection()) {
			SelectedAttribute sa = selectedAttributeService.create();
			sa.setPreprocessingForm(storedForm);
			sa.setName(attributeName);
			selectedAttributeService.save(sa);
		}

		return storedForm;
	}

	public PreprocessingForm saveSubmitedSimpleFormExport(PreprocessingForm storedForm,
			PreproSimpleForm form) {
		// Borrado de attributos seleccionados en caso de que ya existan
		deleteExistingSelectedAttributes(storedForm);
		// Attributos seleccionados
		for (String attributeName : form.getExportAttributeSelection()) {
			SelectedAttribute sa = selectedAttributeService.create();
			sa.setPreprocessingForm(storedForm);
			sa.setName(attributeName);
			selectedAttributeService.save(sa);
		}

		return storedForm;
	}

	public void deleteExistingSelectedAttributes(PreprocessingForm storedForm) {
		Collection<SelectedAttribute> selectedList = selectedAttributeService
				.findSelectedAttributesByFormId(storedForm.getId());
		for (SelectedAttribute selectedAttribute : selectedList) {
			Collection<Process> ps = processService.findProcessByLabelId(selectedAttribute.getId());
			if (processService.findProcessByLabelId(selectedAttribute.getId()).isEmpty()) {
				selectedAttributeService.delete(selectedAttribute);
			} else {
				processService.delete(ps.iterator().next());
			}
		}
	}

	public Collection<PreprocessingForm> findFormsByFileId(int fileId) {
		Assert.notNull(fileId);
		User principal = userService.findByPrincipal();
		File file = fileService.findOne(fileId);
		boolean isOwner = principal.equals(file.getUser());
		Assert.isTrue(isOwner);
		return preprocessingFormRepository.findFormsByFileId(fileId);
	}

	public Collection<PreprocessingForm> findNullDataForms(int fileId) {
		return preprocessingFormRepository.findNullDataForms(fileId);
	}

	public void deleteNullDataForms(Collection<PreprocessingForm> forms) {
		preprocessingFormRepository.deleteInBatch(forms);
	}

	public PreproSimpleForm loadAttributesInPreproSimpleForm(Attribute[] attributes) {
		PreproSimpleForm form = new PreproSimpleForm();
		form.setProcessAttributeSelection(RoisinUtils
				.getAttributeNameListFromExampleSet(attributes));
		form.setExportAttributeSelection(RoisinUtils.getAttributeNameListFromExampleSet(attributes));
		return form;
	}

	public PreprocessingForm createSavePreprocessingFormFromFile(File file) {
		PreprocessingForm preform = create();
		preform.setFile(file);
		preform = save(preform);
		return preform;
	}

	public Attribute loadFilterAttribute(Attribute[] attributes, FilterConditionForm form) {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].getName().equals(form.getFilterAttribute())) {
				return attributes[i];
			}
		}
		return null;
	}

}
