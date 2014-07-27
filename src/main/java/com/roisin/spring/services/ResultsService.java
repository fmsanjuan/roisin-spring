package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.RoisinRule;
import com.roisin.spring.model.Results;
import com.roisin.spring.model.Rule;
import com.roisin.spring.repositories.ResultsRepository;

@Service
@Transactional
public class ResultsService {

	@Autowired
	private ResultsRepository resultsRepository;

	@Autowired
	private RuleService ruleService;

	public ResultsService() {
		super();
	}

	public Results create() {
		Results results = new Results();

		return results;
	}

	public Collection<Results> findAll() {
		return resultsRepository.findAll();
	}

	public Results findOne(int resultsId) {
		return resultsRepository.findOne(resultsId);
	}

	public Results save(Results results) {
		return resultsRepository.save(results);
	}

	public void delete(Results results) {
		resultsRepository.delete(results);
	}

	// Extra methods

	public Results saveResultRules(RoisinResults roisinResults) {
		Results results = create();
		results.setAuc(roisinResults.getRulesAuc());
		results = save(results);

		for (RoisinRule roisinRule : roisinResults.getRoisinRules()) {
			Rule rule = ruleService.create();
			rule.setAuc(roisinRule.getAuc());
			rule.setPremise(roisinRule.getPremise());
			rule.setConclusion(roisinRule.getConclusion());
			rule.setTp(roisinRule.getTruePositives());
			rule.setTn(roisinRule.getTrueNegatives());
			rule.setFp(roisinRule.getFalsePositives());
			rule.setFn(roisinRule.getFalseNegatives());
			rule.setTpr(roisinRule.getTruePositiveRate());
			rule.setFpr(roisinRule.getFalsePositiveRate());
			rule.setRulePrecision(roisinRule.getPrecision());
			rule.setSupport(roisinRule.getSupport());
			rule.setResults(results);
			ruleService.save(rule);
		}

		results = findOne(results.getId());

		return results;
	}
}
