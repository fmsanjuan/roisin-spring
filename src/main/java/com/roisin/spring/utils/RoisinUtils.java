package com.roisin.spring.utils;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.spring.forms.PreproSimpleForm;
import com.roisin.spring.forms.PreprocessingForm;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.SelectedAttribute;

public class RoisinUtils {

	public static PreprocessingForm calculateFilterCondition(PreprocessingForm form) {
		// Transformación de condición provisional
		if (!StringUtils.isBlank(form.getFilterValue())) {
			StringBuilder condition = new StringBuilder();
			condition.append(form.getFilterAttribute());
			if (form.getFilterOperator().equals(Constants.EQUALS)) {
				condition.append(Constants.EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.NON_EQUALS)) {
				condition.append(Constants.NON_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_OR_EQUALS)) {
				condition.append(Constants.GREATER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_OR_EQUALS)) {
				condition.append(Constants.SMALLER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_THAN)) {
				condition.append(Constants.SMALLER_THAN_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_THAN)) {
				condition.append(Constants.GREATER_THAN_SYMBOL);
			}
			condition.append(form.getFilterValue());
			form.setFilterCondition(condition.toString());
		}
		return form;
	}

	/**
	 * Returns the list of examples that are contained into an example set.
	 * 
	 * @param exampleSet
	 * @return
	 */
	public static List<Example> getExampleListFromExampleSet(ExampleSet exampleSet) {
		List<Example> examples = Lists.newArrayList();
		for (int i = 0; i < exampleSet.getExampleTable().size(); i++) {
			examples.add(exampleSet.getExample(i));
		}
		return examples;
	}

	/**
	 * Returns all the attribute names from an example set.
	 * 
	 * @param exampleSet
	 * @return
	 */
	public static List<String> getAttributeNameListFromExampleSet(Attribute[] attributes) {
		List<String> attributeSelection = Lists.newArrayList();
		for (int i = 0; i < attributes.length; i++) {
			attributeSelection.add(attributes[i].getName());
		}
		return attributeSelection;
	}

	public static List<Integer> getDeletedRowValues(Collection<DeletedRow> deletedRows) {
		List<Integer> res = Lists.newArrayList();
		for (DeletedRow deletedRow : deletedRows) {
			res.add(deletedRow.getNumber());
		}
		return res;
	}

	public static String calculateFilterCondition(PreproSimpleForm form) {
		// Transformación de condición provisional
		if (!StringUtils.isBlank(form.getFilterValue())) {
			StringBuilder condition = new StringBuilder();
			condition.append(form.getFilterAttribute());
			if (form.getFilterOperator().equals(Constants.EQUALS)) {
				condition.append(Constants.EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.NON_EQUALS)) {
				condition.append(Constants.NON_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_OR_EQUALS)) {
				condition.append(Constants.GREATER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_OR_EQUALS)) {
				condition.append(Constants.SMALLER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_THAN)) {
				condition.append(Constants.SMALLER_THAN_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_THAN)) {
				condition.append(Constants.GREATER_THAN_SYMBOL);
			}
			condition.append(form.getFilterValue());
			return condition.toString();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * This method transforms deleted row collections to integer sorted set
	 * which represents the rows in order. This process is necessary to execute
	 * processes from core.
	 * 
	 * @param deletedRows
	 * @return
	 */
	public static SortedSet<Integer> getRowsFromDeletedRows(Collection<DeletedRow> deletedRows) {
		SortedSet<Integer> res = Sets.newTreeSet();
		for (DeletedRow deletedRow : deletedRows) {
			res.add(deletedRow.getNumber());
		}
		return res;
	}

	public static List<String> getAttributesFromSelectedAttributes(
			Collection<SelectedAttribute> selectedAttributes) {
		List<String> res = Lists.newArrayList();
		for (SelectedAttribute selectedAttribute : selectedAttributes) {
			res.add(selectedAttribute.getName());
		}
		return res;
	}
}
