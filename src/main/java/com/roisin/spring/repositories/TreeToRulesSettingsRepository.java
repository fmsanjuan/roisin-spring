package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.TreeToRulesSettings;

@Repository
public interface TreeToRulesSettingsRepository extends JpaRepository<TreeToRulesSettings, Integer> {

}
