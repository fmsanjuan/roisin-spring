package com.roisin.spring.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.XYLine;
import com.googlecode.charts4j.XYLineChart;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.RoisinRule;
import com.roisin.core.utils.RoisinRuleComparator;
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

	/**
	 * This method returns the chart with the AUC representation.
	 * 
	 * @param roisinResults
	 * @return
	 */
	public static XYLineChart getAucChart(RoisinResults roisinResults) {

		List<RoisinRule> rules = roisinResults.getRoisinRules();
		Collections.sort(rules, new RoisinRuleComparator());
		int rulesSize = rules.size();

		// Curve
		double[] xValues = new double[rulesSize + 2];
		double[] yValues = new double[rulesSize + 2];
		xValues[0] = Constants.ZERO;
		yValues[0] = Constants.ZERO;

		int i;

		for (i = 0; i < rules.size(); i++) {
			xValues[i + 1] = rules.get(i).getFalsePositiveRate();
			yValues[i + 1] = rules.get(i).getTruePositiveRate();
		}

		xValues[i + 1] = Constants.ONE;
		yValues[i + 1] = Constants.ONE;

		Data xData = DataUtil.scaleWithinRange(Constants.ZERO, Constants.ONE, xValues);
		Data yData = DataUtil.scaleWithinRange(Constants.ZERO, Constants.ONE, yValues);

		XYLine line = Plots.newXYLine(xData, yData);
		line.setFillAreaColor(Color.YELLOW);
		XYLineChart chart = GCharts.newXYLineChart(line);
		chart.setSize(Constants.CHART_WIDTH, Constants.CHART_HEIGTH);
		chart.setTitle("Area under the curve = " + roisinResults.getRulesAuc());

		chart.setAreaFill(Fills.newSolidFill(Color.GRAY));

		return chart;
	}
}
