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

	/**
	 * Area under the curve title
	 */
	private static final String AUC_TITLE = "Area under the curve = ";

	public static String calculateFilterCondition(final String filterAttribute,
			final String filterOperator, final String filterValue) {
		// Transformación de condición provisional
		final StringBuilder condition = new StringBuilder();
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
	public static List<Example> getExampleListFromExampleSet(final ExampleSet exampleSet) {
		final List<Example> examples = Lists.newArrayList();
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
	public static List<String> getAttributeNameListFromExampleSet(final Attribute[] attributes) {
		final List<String> attributeSelection = Lists.newArrayList();
		for (int i = 0; i < attributes.length; i++) {
			attributeSelection.add(attributes[i].getName());
		}
		return attributeSelection;
	}

	public static List<Integer> getDeletedRowValues(final Collection<DeletedRow> deletedRows) {
		final List<Integer> res = Lists.newArrayList();
		for (final DeletedRow deletedRow : deletedRows) {
			res.add(deletedRow.getNumber());
		}
		return res;
	}

	public static String calculateFilterCondition(final FilterConditionForm form) {
		String res = StringUtils.EMPTY;
		// Transformación de condición provisional
		if (!StringUtils.isBlank(form.getFilterValue())) {
			final StringBuilder condition = new StringBuilder();
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
			res = condition.toString();
		}
		return res;
	}

	/**
	 * This method transforms deleted row collections to integer sorted set
	 * which represents the rows in order. This process is necessary to execute
	 * processes from core.
	 * 
	 * @param deletedRows
	 * @return
	 */
	public static SortedSet<Integer> getRowsFromDeletedRows(final Collection<DeletedRow> deletedRows) {
		final SortedSet<Integer> res = Sets.newTreeSet();
		for (final DeletedRow deletedRow : deletedRows) {
			res.add(deletedRow.getNumber());
		}
		return res;
	}

	public static List<String> getAttributesFromSelectedAttributes(
			final Collection<SelectedAttribute> selectedAttributes) {
		final List<String> res = Lists.newArrayList();
		for (final SelectedAttribute selectedAttribute : selectedAttributes) {
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
	public static XYLineChart getAucChart(final Collection<Rule> rules, final double auc) {

		final List<Rule> sortedRules = Lists.newArrayList(rules);
		Collections.sort(sortedRules, new RuleComparator());
		final int rulesSize = sortedRules.size();

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

		final Data xData = DataUtil.scaleWithinRange(ZERO, ONE, xValues);
		final Data yData = DataUtil.scaleWithinRange(ZERO, ONE, yValues);

		final XYLine line = Plots.newXYLine(xData, yData);
		line.setFillAreaColor(Color.YELLOW);
		final XYLineChart chart = GCharts.newXYLineChart(line);
		chart.setSize(CHART_WIDTH, CHART_HEIGTH);
		chart.setTitle(AUC_TITLE + auc);

		chart.setAreaFill(Fills.newSolidFill(Color.GRAY));

		return chart;
	}

	public static XYLineChart getSingleRuleAucChart(final Rule rule) {
		// Curve
		double[] xValues = new double[3];
		double[] yValues = new double[3];

		xValues[0] = ZERO;
		yValues[0] = ZERO;
		xValues[1] = rule.getFpr();
		yValues[1] = rule.getTpr();
		xValues[2] = ONE;
		yValues[2] = ONE;

		final Data xData = DataUtil.scaleWithinRange(ZERO, ONE, xValues);
		final Data yData = DataUtil.scaleWithinRange(ZERO, ONE, yValues);

		final XYLine line = Plots.newXYLine(xData, yData);
		line.setFillAreaColor(Color.YELLOW);
		final XYLineChart chart = GCharts.newXYLineChart(line);
		chart.setSize(CHART_WIDTH, CHART_HEIGTH);
		chart.setTitle(AUC_TITLE + rule.getAuc());

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
	public static Collection<Rule> getAucOptimizationRemovedRules(final Collection<Rule> rules) {
		// Ordenación de reglas (obligatorio)
		final List<Rule> sortedRules = Lists.newArrayList(rules);
		Collections.sort(sortedRules, new RuleComparator());
		// Reglas eliminadas
		final List<Rule> deletedRules = Lists.newArrayList();

		boolean removed = true;
		// Se debe iterar sobre las reglas hasta que no queden reglas por
		// eliminar
		Rule curRule;
		Rule prevRule;
		Rule nextRule;
		while (removed) {
			removed = false;
			for (int i = 0; i < sortedRules.size(); i++) {
				curRule = sortedRules.get(i);

				Line line;
				if (i != 0 && i != (sortedRules.size() - 1)) {
					prevRule = sortedRules.get(i - 1);
					nextRule = sortedRules.get(i + 1);
					line = calculateLine(prevRule.getFpr(), prevRule.getTpr(), nextRule.getFpr(),
							nextRule.getTpr());
				} else if (i == 0) {
					nextRule = sortedRules.get(i + 1);
					line = calculateLine(0.0, 0.0, nextRule.getFpr(), nextRule.getTpr());
				} else {
					prevRule = sortedRules.get(i - 1);
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
	public static boolean isUnderTheLine(final double x, final double y, final Line line) {
		final double yLine = (x * line.getM()) + line.getK();
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
	public static Line calculateLine(final double xa, final double ya, final double xc,
			final double yc) {
		final double m = (ya - yc) / (xa - xc);
		final double k = ya - (xa * m);
		return new Line(m, k);
	}

	public static double calculateRulesAuc(final Collection<Rule> rules) {
		final SortedSet<Rule> sortedRules = Sets.newTreeSet(new RuleComparator());
		sortedRules.addAll(rules);
		int ruleCounter = 1;
		double auc = 0.0;
		double prevFpr = 0.0;
		double prevTpr = 0.0;
		for (final Rule roisinRule : sortedRules) {
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

	public static boolean isNumericLabel(final Attribute[] attributes, String label) {
		boolean res = false;
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].getName().equals(label)) {
				res = attributes[i].isNumerical();
			}
		}
		return res;
	}

	public static boolean hasNumerical(final Attribute[] attributes) {
		boolean res = false;
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].isNumerical()) {
				res = true;
			}
		}
		return res;
	}

	public static boolean hasNominal(final Attribute[] attributes) {
		boolean res = false;
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].isNominal()) {
				res = true;
			}
		}
		return res;
	}

}
