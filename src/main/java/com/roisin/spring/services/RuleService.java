package com.roisin.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Rule;
import com.roisin.spring.repositories.RuleRepository;

@Service
@Transactional
public class RuleService {

	@Autowired
	private RuleRepository roisinRuleRepository;

	public RuleService() {
		super();
	}

	public Rule create() {
		Rule roisinRule = new Rule();

		return roisinRule;
	}

	public Rule findOne(int roisinRuleId) {
		return roisinRuleRepository.findOne(roisinRuleId);
	}

	public void save(Rule roisinRule) {

		roisinRuleRepository.save(roisinRule);
	}

	// TODO: Método para obtener todas las reglas de un proceso.

}
