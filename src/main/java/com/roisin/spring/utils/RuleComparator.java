package com.roisin.spring.utils;

import java.util.Comparator;

import com.roisin.spring.model.Rule;

/**
 * Rule comparator. This comparator sorts the rules by FPR (first option) and
 * TPR (second option).
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class RuleComparator implements Comparator<Rule> {

	@Override
	public int compare(final Rule rule1, final Rule rule2) {
		/*
		 * Si el FPR es igual, se tiene en cuenta el TPR.
		 */
		int res = 0;
		if ((rule1.getFpr() - rule2.getFpr()) == 0.0) {
			if ((rule1.getTpr() - rule2.getTpr()) >= 0.0) {
				res = 1;
			} else {
				res = -1;
			}
		} else {
			res = (rule1.getFpr() - rule2.getFpr()) > 0.0 ? 1 : -1;
		}
		return res;
	}

}
