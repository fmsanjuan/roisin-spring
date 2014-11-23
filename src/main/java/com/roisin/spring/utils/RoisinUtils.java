package com.roisin.spring.utils;

import static com.roisin.spring.utils.Constants.CHART_HEIGTH;
import static com.roisin.spring.utils.Constants.CHART_WIDTH;
import static com.roisin.spring.utils.Constants.EQUALS;
import static com.roisin.spring.utils.Constants.EQUALS_SYMBOL;
import static com.roisin.spring.utils.Constants.GREATER_OR_EQUALS;
import static com.roisin.spring.utils.Constants.GREATER_OR_EQUALS_SYMBOL;
import static com.roisin.spring.utils.Constants.GREATER_THAN;
import static com.roisin.spring.utils.Constants.GREATER_THAN_SYMBOL;
import static com.roisin.spring.utils.Constants.NON_EQUALS;
import static com.roisin.spring.utils.Constants.NON_EQUALS_SYMBOL;
import static com.roisin.spring.utils.Constants.ONE;
import static com.roisin.spring.utils.Constants.SMALLER_OR_EQUALS;
import static com.roisin.spring.utils.Constants.SMALLER_OR_EQUALS_SYMBOL;
import static com.roisin.spring.utils.Constants.SMALLER_THAN;
import static com.roisin.spring.utils.Constants.SMALLER_THAN_SYMBOL;
import static com.roisin.spring.utils.Constants.ZERO;

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
import com.roisin.spring.forms.FilterConditionForm;
import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.model.Rule;
import com.roisin.spring.model.SelectedAttribute;

public class RoisinUtils {

	private static final String AREA_UNDER_THE_CURVE = "Area under the curve = ";

	public static String calculateFilterCondition(String filterAttribute, String filterOperator,
			String filterValue) {
		// Transformación de condición provisional
		StringBuilder condition = new StringBuilder();
		if (!StringUtils.isBlank(filterValue)) {
			condition.append(filterAttribute);
			if (filterOperator.equals(EQUALS)) {
				condition.append(EQUALS_SYMBOL);
			} else if (filterOperator.equals(NON_EQUALS)) {
				condition.append(NON_EQUALS_SYMBOL);
			} else if (filterOperator.equals(GREATER_OR_EQUALS)) {
				condition.append(GREATER_OR_EQUALS_SYMBOL);
			} else if (filterOperator.equals(SMALLER_OR_EQUALS)) {
				condition.append(SMALLER_OR_EQUALS_SYMBOL);
			} else if (filterOperator.equals(SMALLER_THAN)) {
				condition.append(SMALLER_THAN_SYMBOL);
			} else if (filterOperator.equals(GREATER_THAN)) {
				condition.append(GREATER_THAN_SYMBOL);
			}
			condition.append(filterValue);
		}
		return condition.toString();
	}

	/**
	 * Returns the list of examples that are contained into an example set.
	 * 
	 * @param exampleSet
	 * @return
	 */
	public static List<Example> getExampleListFromExampleSet(ExampleSet exampleSet) {
		List<Example> examples = Lists.newArrayList();
		for (int i = 0; i < exampleSet.size(); i++) {
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

	public static String calculateFilterCondition(FilterConditionForm form) {
		// Transformación de condición provisional
		if (!StringUtils.isBlank(form.getFilterValue())) {
			StringBuilder condition = new StringBuilder();
			condition.append(form.getFilterAttribute());
			if (form.getFilterOperator().equals(EQUALS)) {
				condition.append(EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(NON_EQUALS)) {
				condition.append(NON_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(GREATER_OR_EQUALS)) {
				condition.append(GREATER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(SMALLER_OR_EQUALS)) {
				condition.append(SMALLER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(SMALLER_THAN)) {
				condition.append(SMALLER_THAN_SYMBOL);
			} else if (form.getFilterOperator().equals(GREATER_THAN)) {
				condition.append(GREATER_THAN_SYMBOL);
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
	 * @param rules
	 * @param auc
	 * @return
	 */
	public static XYLineChart getAucChart(Collection<Rule> rules, double auc) {

		List<Rule> sortedRules = Lists.newArrayList(rules);
		Collections.sort(sortedRules, new RuleComparator());
		int rulesSize = sortedRules.size();

		// Curve
		double[] xValues = new double[rulesSize + 2];
		double[] yValues = new double[rulesSize + 2];
		xValues[0] = ZERO;
		yValues[0] = ZERO;

		int i;

		for (i = 0; i < sortedRules.size(); i++) {
			xValues[i + 1] = sortedRules.get(i).getFpr();
			yValues[i + 1] = sortedRules.get(i).getTpr();
		}

		xValues[i + 1] = ONE;
		yValues[i + 1] = ONE;

		Data xData = DataUtil.scaleWithinRange(ZERO, ONE, xValues);
		Data yData = DataUtil.scaleWithinRange(ZERO, ONE, yValues);

		XYLine line = Plots.newXYLine(xData, yData);
		line.setFillAreaColor(Color.YELLOW);
		XYLineChart chart = GCharts.newXYLineChart(line);
		chart.setSize(CHART_WIDTH, CHART_HEIGTH);
		chart.setTitle(AREA_UNDER_THE_CURVE + auc);

		chart.setAreaFill(Fills.newSolidFill(Color.GRAY));

		return chart;
	}

	public static XYLineChart getSingleRuleAucChart(Rule rule) {
		// Curve
		double[] xValues = new double[3];
		double[] yValues = new double[3];

		xValues[0] = ZERO;
		yValues[0] = ZERO;
		xValues[1] = rule.getFpr();
		yValues[1] = rule.getTpr();
		xValues[2] = ONE;
		yValues[2] = ONE;

		Data xData = DataUtil.scaleWithinRange(ZERO, ONE, xValues);
		Data yData = DataUtil.scaleWithinRange(ZERO, ONE, yValues);

		XYLine line = Plots.newXYLine(xData, yData);
		line.setFillAreaColor(Color.YELLOW);
		XYLineChart chart = GCharts.newXYLineChart(line);
		chart.setSize(CHART_WIDTH, CHART_HEIGTH);
		chart.setTitle(AREA_UNDER_THE_CURVE + rule.getAuc());

		chart.setAreaFill(Fills.newSolidFill(Color.GRAY));

		return chart;
	}

	/**
	 * This method applies ROC Analysis and returns all the rules that should be
	 * removed from the results.
	 * 
	 * @param rules
	 * @return
	 */
	public static Collection<Rule> getAucOptimizationRemovedRules(Collection<Rule> rules) {
		// Ordenación de reglas (obligatorio)
		List<Rule> sortedRules = Lists.newArrayList(rules);
		Collections.sort(sortedRules, new RuleComparator());
		// Reglas eliminadas
		List<Rule> deletedRules = Lists.newArrayList();

		boolean removed = true;
		// Se debe iterar sobre las reglas hasta que no queden reglas por
		// eliminar
		while (removed) {
			removed = false;
			for (int i = 0; i < sortedRules.size(); i++) {
				Rule curRule = sortedRules.get(i);

				Line line;
				if (i != 0 && i != (sortedRules.size() - 1)) {
					Rule prevRule = sortedRules.get(i - 1);
					Rule nextRule = sortedRules.get(i + 1);
					line = calculateLine(prevRule.getFpr(), prevRule.getTpr(), nextRule.getFpr(),
							nextRule.getTpr());
				} else if (i == 0) {
					Rule nextRule = sortedRules.get(i + 1);
					line = calculateLine(0.0, 0.0, nextRule.getFpr(), nextRule.getTpr());
				} else {
					Rule prevRule = sortedRules.get(i - 1);
					line = calculateLine(prevRule.getFpr(), prevRule.getTpr(), 1.0, 1.0);
				}

				if (isUnderTheLine(curRule.getFpr(), curRule.getTpr(), line)) {
					deletedRules.add(curRule);
					sortedRules.remove(curRule);
					// Se controla si se siguen borrando reglas para parar el
					// bucle while
					removed = true;
					// Se sale del for para volver a iterar sobre el nuevo
					// conjunto de reglas
					break;
				}
			}

		}

		return deletedRules;
	}

	/**
	 * This method calculates if the point given is under the line.
	 * 
	 * @param x
	 * @param y
	 * @param line
	 * @return
	 */
	public static boolean isUnderTheLine(double x, double y, Line line) {
		double yLine = (x * line.getM()) + line.getK();
		return yLine > y;
	}

	/**
	 * This method calculates the line defined by the points given.
	 * 
	 * @param xa
	 * @param ya
	 * @param xc
	 * @param yc
	 * @return
	 */
	public static Line calculateLine(double xa, double ya, double xc, double yc) {
		double m = (ya - yc) / (xa - xc);
		double k = ya - (xa * m);
		return new Line(m, k);
	}

	public static double calculateRulesAuc(Collection<Rule> rules) {
		SortedSet<Rule> sortedRules = Sets.newTreeSet(new RuleComparator());
		sortedRules.addAll(rules);
		int ruleCounter = 1;
		double auc = 0.0;
		double prevFpr = 0.0;
		double prevTpr = 0.0;
		for (Rule roisinRule : sortedRules) {
			if (ruleCounter > 1) {
				// Se debe de calcular el triángulo y el rectángulo teniendo en
				// cuenta los datos de esta regla y la anterior.
				// Triángulo:
				auc += Math
						.abs((((roisinRule.getFpr() - prevFpr) * (roisinRule.getTpr() - prevTpr)) / 2.0));
				// Rectángulo
				auc += Math.abs((roisinRule.getFpr() - prevFpr) * prevTpr);
				// Cambiamos el valor de las variables prev
				prevFpr = roisinRule.getFpr();
				prevTpr = roisinRule.getTpr();
			} else {
				// Sólo hay que calcular el primer triángulo.
				prevFpr = roisinRule.getFpr();
				prevTpr = roisinRule.getTpr();
				auc += Math.abs(((prevFpr * prevTpr) / 2.0));
			}
			ruleCounter++;
		}
		auc += Math.abs((1 - prevFpr) * prevTpr);
		auc += Math.abs(((1 - prevFpr) * (1 - prevTpr)) / 2.0);
		return auc;
	}

	public static boolean isNumericLabel(Attribute[] attributes, String label) {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].getName().equals(label)) {
				return attributes[i].isNumerical();
			}
		}
		return false;
	}

	public static boolean hasNumerical(Attribute[] attributes) {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].isNumerical()) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasNominal(Attribute[] attributes) {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].isNominal()) {
				return true;
			}
		}
		return false;
	}

}
