package com.roisin.spring.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.Rule;

/**
 * Rule repository
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {

	/**
	 * Returns all the rules of de results object
	 * 
	 * @param resultsId
	 * @return rules
	 */
	@Query("select r from Rule r where r.results.id = ?1")
	Collection<Rule> findRulesByResultsId(int resultsId);

}
