package com.roisin.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Rule;
import com.roisin.spring.model.SubgroupSettings;
import com.roisin.spring.repositories.SubgroupSettingsRepository;

@Service
@Transactional
public class SubgroupSettingsService {

	@Autowired
	private SubgroupSettingsRepository subgroupSettingsRepository;

	public SubgroupSettingsService() {
		super();
	}

	public Rule create() {
		Rule roisinRule = new Rule();

		return roisinRule;
	}

	public SubgroupSettings findOne(int subgroupSettingsId) {
		return subgroupSettingsRepository.findOne(subgroupSettingsId);
	}

	public void save(SubgroupSettings subgroupSettings) {

		subgroupSettingsRepository.save(subgroupSettings);
	}

	// TODO: Método para obtener todas las reglas de un proceso.
}
