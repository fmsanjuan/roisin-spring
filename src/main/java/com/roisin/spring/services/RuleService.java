package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Rule;
import com.roisin.spring.repositories.RuleRepository;

@Service
@Transactional
public class RuleService {

	/**
	 * Rule repository
	 */
	@Autowired
	private transient RuleRepository ruleRepository;

	/**
	 * Results service
	 */
	@Autowired
	private transient ResultsService resultsService;

	public RuleService() {
		super();
	}

	public Rule create() {
		final Rule rule = new Rule();

		return rule;
	}

	public Rule findOne(final int roisinRuleId) {
		return ruleRepository.findOne(roisinRuleId);
	}

	public void save(final Rule rule) {
		ruleRepository.save(rule);
	}

	public void delete(final Rule rule) {
		ruleRepository.delete(rule);
	}

	public Collection<Rule> findAll() {
		return ruleRepository.findAll();
	}

	public Collection<Rule> findRulesByResultsId(final int resultsId) {
		resultsService.findOne(resultsId);
		return ruleRepository.findRulesByResultsId(resultsId);
	}

}
