package com.roisin.spring.utils;

import java.util.Comparator;

import com.roisin.spring.model.Rule;

public class RuleComparator implements Comparator<Rule> {

	@Override
	public int compare(Rule o1, Rule o2) {
		/*
		 * Si el FPR es igual, se tiene en cuenta el TPR.
		 */
		int res = 0;
		if ((o1.getFpr() - o2.getFpr()) == 0.0) {
			if ((o1.getTpr() - o2.getTpr()) >= 0.0) {
				res = 1;
			} else {
				res = -1;
			}
		} else {
			res = (o1.getFpr() - o2.getFpr()) > 0.0 ? 1 : -1;
		}
		return res;
	}

}
