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

	@Autowired
	private RuleRepository ruleRepository;

	public RuleService() {
		super();
	}

	public Rule create() {
		Rule rule = new Rule();

		return rule;
	}

	public Rule findOne(int roisinRuleId) {
		return ruleRepository.findOne(roisinRuleId);
	}

	public void save(Rule rule) {
		ruleRepository.save(rule);
	}

	public void delete(Rule rule) {
		ruleRepository.delete(rule);
	}

	public Collection<Rule> findAll() {
		return ruleRepository.findAll();
	}

	// TODO: Método para obtener todas las reglas de un proceso.

	public Collection<Rule> findRulesByResultsId(int resultsId) {
		return ruleRepository.findRulesByResultsId(resultsId);
	}

}
