package com.roisin.spring.utils;

/**
 * This interface contains all the constants needed to configure processes
 * advanced settings.
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public interface ProcessConstants {

	/*
	 * Ripper
	 */
	String INFORMATION_GAIN = "information_gain";

	String ACCURACY_RIPPER_TREE = "accuracy";

	/*
	 * Subgroup Discovery
	 */

	String K_BEST_RULES = "k best rules";

	String ABOVE_MINIMUM_UTILITY = "above minimum utility";

	String WRACC = "WRAcc";

	String COVERAGE = "Coverage";

	String PRECISION = "Precision";

	String ACCURACY_SUBGROUP = "Accuracy";

	String BIAS = "Bias";

	String LIFT = "Lift";

	String BINOMINAL = "Binominal";

	String SQUARED = "Squared";

	String ODDS = "Odds";

	String ODDS_RATIO = "Odds Ratio";

	/*
	 * Decision Tree
	 */

	String GAIN_RATIO = "gain_ratio";

	String GINI_INDEX = "gini_index";

}
