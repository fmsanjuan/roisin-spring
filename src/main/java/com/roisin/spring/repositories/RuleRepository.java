package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {

}
