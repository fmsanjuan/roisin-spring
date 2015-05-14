package com.roisin.spring.services;

import static com.roisin.spring.utils.Constants.EQUALS;
import static com.roisin.spring.utils.Constants.GREATER_OR_EQUALS;
import static com.roisin.spring.utils.Constants.GREATER_THAN;
import static com.roisin.spring.utils.Constants.NON_EQUALS;
import static com.roisin.spring.utils.Constants.ROISIN_NULL;
import static com.roisin.spring.utils.Constants.SMALLER_OR_EQUALS;
import static com.roisin.spring.utils.Constants.SMALLER_THAN;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.spring.forms.FilterConditionForm;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.model.User;
import com.roisin.spring.repositories.PreprocessedDataRepository;
import com.roisin.spring.utils.RoisinUtils;

@Service
@Transactional
public class PreprocessedDataService {

	@Autowired
	private PreprocessedDataRepository preprocessedDataRepository;

	@Autowired
	private PreprocessingFormService preprocessingFormService;

	@Autowired
	private DeletedRowService deletedRowService;

	@Autowired
	private UserService userService;

	public PreprocessedDataService() {
		super();
	}

	public PreprocessedData create() {
		final PreprocessedData preprocessedData = new PreprocessedData();

		return preprocessedData;
	}

	public Collection<PreprocessedData> findAll() {
		return preprocessedDataRepository.findAll();
	}

	public PreprocessedData findOne(final int preprocessedDataId) {
		Assert.notNull(preprocessedDataId);
		final User principal = userService.findByPrincipal();
		final PreprocessedData data = preprocessedDataRepository.findOne(preprocessedDataId);
		final boolean isOwner = principal.equals(data.getPreprocessingForm().getFile().getUser());
		Assert.isTrue(isOwner);
		return data;
	}

	public PreprocessedData save(final PreprocessedData preprocessedData) {
		return preprocessedDataRepository.save(preprocessedData);
	}

	public void delete(final PreprocessedData preprocessedData) {
		Assert.notNull(preprocessedData);
		final User principal = userService.findByPrincipal();
		boolean isOwner = principal.equals(preprocessedData.getPreprocessingForm().getFile()
				.getUser());
		Assert.isTrue(isOwner);
		preprocessedDataRepository.delete(preprocessedData);
	}

	// Extra methods

	public Collection<PreprocessedData> findDataByFileId(final int fileId) {
		return preprocessedDataRepository.findDataByFileId(fileId);
	}

	public Collection<PreprocessedData> findNullData(final int fileId) {
		return preprocessedDataRepository.findNullDataByFileId(fileId);
	}

	public void deleteNullData(final Collection<PreprocessedData> data) {
		preprocessedDataRepository.delete(data);
	}

	public List<Example> getExamplesFromPreprocessedData(final int dataId) {
		final PreprocessedData data = findOne(dataId);
		final ExampleSet exampleSet = data.getExampleSet();
		final List<Example> examples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(data
				.getPreprocessingForm().getId());
		if (!deletedRows.isEmpty()) {
			final List<Integer> deletedRowValues = RoisinUtils.getDeletedRowValues(deletedRows);
			for (final Integer deletedRowValue : deletedRowValues) {
				examples.remove(deletedRowValue);
			}
		}
		return examples;
	}

	public Attribute[] getAttributesFromPreprocessedData(final int dataId) {
		final PreprocessedData data = findOne(dataId);
		final ExampleSet exampleSet = data.getExampleSet();
		return exampleSet.getExampleTable().getAttributes();
	}

	public PreprocessedData createSavePreprocessedData(final ExampleSet exampleSet,
			final PreprocessingForm preform) {
		PreprocessedData data = create();
		data.setName(ROISIN_NULL);
		data.setDescription(ROISIN_NULL);
		data.setExampleSet(exampleSet);
		data.setPreprocessingForm(preform);
		data = save(data);
		return data;
	}

	public List<Example> getExamplesWithoutDeletedRows(final int dataId, final int rowId) {
		final PreprocessingForm form = preprocessingFormService.findFormByDataId(dataId);
		final int formId = form.getId();
		final Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(formId);
		// Se recupera el conjunto de datos de ejemplo
		final PreprocessedData data = findOne(dataId);
		final ExampleSet exampleSet = data.getExampleSet();
		// Lista original
		final List<Example> originalExamples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		// Lista modificable
		final List<Example> examples = Lists.newArrayList(originalExamples);
		// Creación de deletedrow
		final DeletedRow deletedRow = deletedRowService.create();
		if (deletedRows.isEmpty()) {
			// Si es el primero, sabemos sin problema cuál es el rowId
			deletedRow.setNumber(rowId + 1);
			examples.remove(rowId);
		} else {
			// Ecc, eliminamos de la lista todas las filas en el mismo orden que
			// se almacenaron.
			for (DeletedRow dRow : deletedRows) {
				examples.remove(originalExamples.get(dRow.getNumber() - 1));
			}
			// Obtenemos el ejemplo concreto que se ha soliticado eliminar en
			// esta ocasión.
			final Example exampleToDelete = examples.get(rowId);
			// Obtenemos su el id real del ejemplo a eliminar
			final int realRowId = originalExamples.indexOf(exampleToDelete);
			// Se elimina de la lista result
			examples.remove(exampleToDelete);
			// Guardamos el id real
			deletedRow.setNumber(realRowId + 1);
		}
		deletedRow.setPreprocessingForm(form);
		deletedRowService.save(deletedRow);

		return examples;
	}

	public List<Example> getNumericalFilteredExamples(final FilterConditionForm form,
			final Attribute filterAttribute) {
		final PreprocessingForm storedForm = preprocessingFormService.findFormByDataId(form
				.getDataId());
		final PreprocessedData data = findOne(form.getDataId());
		final ExampleSet exampleSet = data.getExampleSet();
		// Lista original
		final List<Example> originalExamples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		// Lista modificable
		final List<Example> examples = Lists.newArrayList(originalExamples);

		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(storedForm
				.getId());
		for (final DeletedRow dRow : deletedRows) {
			examples.remove(originalExamples.get(dRow.getNumber() - 1));
		}

		if (form.getFilterOperator().equals(EQUALS)) {
			for (final Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) == Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(NON_EQUALS)) {
			for (final Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) != Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					final DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(GREATER_OR_EQUALS)) {
			for (final Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) >= Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					final DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(SMALLER_OR_EQUALS)) {
			for (final Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) <= Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					final DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(SMALLER_THAN)) {
			for (final Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) < Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					final DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(GREATER_THAN)) {
			for (final Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) > Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					final DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		}

		return examples;
	}

	public List<Example> getNominalFilteredExamples(final FilterConditionForm form,
			final Attribute filterAttribute) {

		final PreprocessingForm storedForm = preprocessingFormService.findFormByDataId(form
				.getDataId());
		final PreprocessedData data = findOne(form.getDataId());
		final ExampleSet exampleSet = data.getExampleSet();
		// Lista original
		final List<Example> originalExamples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		// Lista modificable
		final List<Example> examples = Lists.newArrayList(originalExamples);

		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(storedForm
				.getId());
		for (final DeletedRow dRow : deletedRows) {
			examples.remove(originalExamples.get(dRow.getNumber() - 1));
		}

		if (form.getFilterOperator().equals(EQUALS)) {
			for (final Example example : originalExamples) {
				if (example.getNominalValue(filterAttribute).equals(form.getFilterValue())
						&& examples.contains(example)) {
					DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(NON_EQUALS)) {
			for (final Example example : originalExamples) {
				if (!example.getNominalValue(filterAttribute).equals(form.getFilterValue())
						&& examples.contains(example)) {
					DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		}

		return examples;
	}

}
