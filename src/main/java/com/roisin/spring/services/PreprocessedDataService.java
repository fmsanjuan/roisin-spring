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

import com.google.common.collect.Lists;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.spring.forms.FilterConditionForm;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.model.PreprocessingForm;
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

	public PreprocessedDataService() {
		super();
	}

	public PreprocessedData create() {
		PreprocessedData preprocessedData = new PreprocessedData();

		return preprocessedData;
	}

	public Collection<PreprocessedData> findAll() {
		return preprocessedDataRepository.findAll();
	}

	public PreprocessedData findOne(int preprocessedDataId) {
		return preprocessedDataRepository.findOne(preprocessedDataId);
	}

	public PreprocessedData save(PreprocessedData preprocessedData) {
		return preprocessedDataRepository.save(preprocessedData);
	}

	public void delete(PreprocessedData preprocessedData) {
		preprocessedDataRepository.delete(preprocessedData);
	}

	// Extra methods

	public Collection<PreprocessedData> findDataByFileId(int fileId) {
		return preprocessedDataRepository.findDataByFileId(fileId);
	}

	public Collection<PreprocessedData> findNullData(int fileId) {
		return preprocessedDataRepository.findNullDataByFileId(fileId);
	}

	public void deleteNullData(Collection<PreprocessedData> data) {
		preprocessedDataRepository.delete(data);
	}

	public List<Example> getExamplesFromPreprocessedData(int dataId) {
		PreprocessedData data = findOne(dataId);
		ExampleSet exampleSet = data.getExampleSet();
		List<Example> examples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(data
				.getPreprocessingForm().getId());
		if (!deletedRows.isEmpty()) {
			List<Integer> deletedRowValues = RoisinUtils.getDeletedRowValues(deletedRows);
			for (Integer deletedRowValue : deletedRowValues) {
				examples.remove(deletedRowValue);
			}
		}
		return examples;
	}

	public Attribute[] getAttributesFromPreprocessedData(int dataId) {
		PreprocessedData data = findOne(dataId);
		ExampleSet exampleSet = data.getExampleSet();
		return exampleSet.getExampleTable().getAttributes();
	}

	public PreprocessedData createSavePreprocessedData(ExampleSet exampleSet,
			PreprocessingForm preform) {
		PreprocessedData data = create();
		data.setName(ROISIN_NULL);
		data.setDescription(ROISIN_NULL);
		data.setExampleSet(exampleSet);
		data.setPreprocessingForm(preform);
		data = save(data);
		return data;
	}

	public List<Example> getExamplesWithoutDeletedRows(int dataId, int rowId) {
		PreprocessingForm form = preprocessingFormService.findFormByDataId(dataId);
		int formId = form.getId();
		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(formId);
		// Se recupera el conjunto de datos de ejemplo
		PreprocessedData data = findOne(dataId);
		ExampleSet exampleSet = data.getExampleSet();
		// Lista original
		List<Example> originalExamples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		// Lista modificable
		List<Example> examples = Lists.newArrayList(originalExamples);
		// Creación de deletedrow
		DeletedRow deletedRow = deletedRowService.create();
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
			Example exampleToDelete = examples.get(rowId);
			// Obtenemos su el id real del ejemplo a eliminar
			int realRowId = originalExamples.indexOf(exampleToDelete);
			// Se elimina de la lista result
			examples.remove(exampleToDelete);
			// Guardamos el id real
			deletedRow.setNumber(realRowId + 1);
		}
		deletedRow.setPreprocessingForm(form);
		deletedRowService.save(deletedRow);

		return examples;
	}

	public List<Example> getNumericalFilteredExamples(FilterConditionForm form,
			Attribute filterAttribute) {
		PreprocessingForm storedForm = preprocessingFormService.findFormByDataId(form.getDataId());
		PreprocessedData data = findOne(form.getDataId());
		ExampleSet exampleSet = data.getExampleSet();
		// Lista original
		List<Example> originalExamples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		// Lista modificable
		List<Example> examples = Lists.newArrayList(originalExamples);

		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(storedForm
				.getId());
		for (DeletedRow dRow : deletedRows) {
			examples.remove(originalExamples.get(dRow.getNumber() - 1));
		}

		if (form.getFilterOperator().equals(EQUALS)) {
			for (Example example : originalExamples) {
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
			for (Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) != Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(GREATER_OR_EQUALS)) {
			for (Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) >= Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(SMALLER_OR_EQUALS)) {
			for (Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) <= Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(SMALLER_THAN)) {
			for (Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) < Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
					DeletedRow deletedRow = deletedRowService.create();
					deletedRow.setNumber(originalExamples.indexOf(example) + 1);
					deletedRow.setPreprocessingForm(storedForm);
					deletedRowService.save(deletedRow);
					examples.remove(example);
				}
			}
		} else if (form.getFilterOperator().equals(GREATER_THAN)) {
			for (Example example : originalExamples) {
				if (example.getNumericalValue(filterAttribute) > Double.parseDouble(form
						.getFilterValue()) && examples.contains(example)) {
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

	public List<Example> getNominalFilteredExamples(FilterConditionForm form,
			Attribute filterAttribute) {

		PreprocessingForm storedForm = preprocessingFormService.findFormByDataId(form.getDataId());
		PreprocessedData data = findOne(form.getDataId());
		ExampleSet exampleSet = data.getExampleSet();
		// Lista original
		List<Example> originalExamples = RoisinUtils.getExampleListFromExampleSet(exampleSet);
		// Lista modificable
		List<Example> examples = Lists.newArrayList(originalExamples);

		Collection<DeletedRow> deletedRows = deletedRowService.findFormDeletedRows(storedForm
				.getId());
		for (DeletedRow dRow : deletedRows) {
			examples.remove(originalExamples.get(dRow.getNumber() - 1));
		}

		if (form.getFilterOperator().equals(EQUALS)) {
			for (Example example : originalExamples) {
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
			for (Example example : originalExamples) {
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
