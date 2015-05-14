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

	/**
	 * Preprocessing form repository
	 */
	@Autowired
	private transient PreprocessingFormRepository preproFormRepo;

	/**
	 * Selected attribute service
	 */
	@Autowired
	private transient SelectedAttributeService saService;

	/**
	 * Process service
	 */
	@Autowired
	private transient ProcessService processService;

	/**
	 * File service
	 */
	@Autowired
	private transient FileService fileService;

	/**
	 * User service
	 */
	@Autowired
	private transient UserService userService;

	public PreprocessingFormService() {
		super();
	}

	public PreprocessingForm create() {
		final PreprocessingForm preprocessingForm = new PreprocessingForm();

		return preprocessingForm;
	}

	public Collection<PreprocessingForm> findAll() {
		return preproFormRepo.findAll();
	}

	public PreprocessingForm findOne(final int preproFormId) {
		Assert.notNull(preproFormId);
		final User principal = userService.findByPrincipal();
		final PreprocessingForm form = preproFormRepo.findOne(preproFormId);
		final boolean isOwner = principal.equals(form.getFile().getUser());
		Assert.isTrue(isOwner);
		return form;
	}

	public PreprocessingForm save(final PreprocessingForm preprocessingForm) {
		return preproFormRepo.save(preprocessingForm);
	}

	public void delete(final PreprocessingForm preprocessingForm) {
		Assert.notNull(preprocessingForm);
		final User principal = userService.findByPrincipal();
		final boolean isOwner = principal.equals(preprocessingForm.getFile().getUser());
		Assert.isTrue(isOwner);
		preproFormRepo.delete(preprocessingForm);
	}

	// Extra methods

	public PreprocessingForm findFormByDataId(final int dataId) {
		Assert.notNull(dataId);
		final User principal = userService.findByPrincipal();
		final PreprocessingForm form = preproFormRepo.findFormByDataId(dataId);
		final boolean isOwner = principal.equals(form.getFile().getUser());
		Assert.isTrue(isOwner);
		return form;
	}

	public PreprocessingForm saveSubmitedSimpleForm(final PreprocessingForm storedForm,
			final PreproSimpleForm form) {
		// Borrado de attributos seleccionados en caso de que ya existan
		deleteExistingSelectedAttributes(storedForm);
		// Attributos seleccionados
		for (final String attributeName : form.getProcessAttributeSelection()) {
			final SelectedAttribute selectedAttribute = saService.create();
			selectedAttribute.setPreprocessingForm(storedForm);
			selectedAttribute.setName(attributeName);
			saService.save(selectedAttribute);
		}

		return storedForm;
	}

	public PreprocessingForm saveSubmitedSimpleFormExport(final PreprocessingForm storedForm,
			final PreproSimpleForm form) {
		// Borrado de attributos seleccionados en caso de que ya existan
		deleteExistingSelectedAttributes(storedForm);
		// Attributos seleccionados
		for (final String attributeName : form.getExportAttributeSelection()) {
			final SelectedAttribute selectedAttribute = saService.create();
			selectedAttribute.setPreprocessingForm(storedForm);
			selectedAttribute.setName(attributeName);
			saService.save(selectedAttribute);
		}

		return storedForm;
	}

	public void deleteExistingSelectedAttributes(final PreprocessingForm storedForm) {
		Collection<SelectedAttribute> selectedList = saService
				.findSelectedAttributesByFormId(storedForm.getId());
		for (final SelectedAttribute selectedAttribute : selectedList) {
			final Collection<Process> process = processService
					.findProcessByLabelId(selectedAttribute.getId());
			if (processService.findProcessByLabelId(selectedAttribute.getId()).isEmpty()) {
				saService.delete(selectedAttribute);
			} else {
				processService.delete(process.iterator().next());
			}
		}
	}

	public Collection<PreprocessingForm> findFormsByFileId(final int fileId) {
		Assert.notNull(fileId);
		final User principal = userService.findByPrincipal();
		final File file = fileService.findOne(fileId);
		final boolean isOwner = principal.equals(file.getUser());
		Assert.isTrue(isOwner);
		return preproFormRepo.findFormsByFileId(fileId);
	}

	public Collection<PreprocessingForm> findNullDataForms(final int fileId) {
		return preproFormRepo.findNullDataForms(fileId);
	}

	public void deleteNullDataForms(final Collection<PreprocessingForm> forms) {
		preproFormRepo.deleteInBatch(forms);
	}

	public PreproSimpleForm loadAttributesInPreproSimpleForm(final Attribute[] attributes) {
		final PreproSimpleForm form = new PreproSimpleForm();
		form.setProcessAttributeSelection(RoisinUtils
				.getAttributeNameListFromExampleSet(attributes));
		form.setExportAttributeSelection(RoisinUtils.getAttributeNameListFromExampleSet(attributes));
		return form;
	}

	public PreprocessingForm createSavePreprocessingFormFromFile(final File file) {
		PreprocessingForm preform = create();
		preform.setFile(file);
		preform = save(preform);
		return preform;
	}

	public Attribute loadFilterAttribute(final Attribute[] attributes,
			final FilterConditionForm form) {
		Attribute res = null;
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].getName().equals(form.getFilterAttribute())) {
				res = attributes[i];
			}
		}
		return res;
	}

}
