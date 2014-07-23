package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public SubgroupSettings create() {
		SubgroupSettings subgroupSettings = new SubgroupSettings();

		return subgroupSettings;
	}

	public SubgroupSettings findOne(int subgroupSettingsId) {
		return subgroupSettingsRepository.findOne(subgroupSettingsId);
	}

	public void save(SubgroupSettings subgroupSettings) {

		subgroupSettingsRepository.save(subgroupSettings);
	}

	public void delete(SubgroupSettings ripperSettings) {
		subgroupSettingsRepository.delete(ripperSettings);
	}

	public Collection<SubgroupSettings> findAll() {
		return subgroupSettingsRepository.findAll();
	}

	// TODO: Método para obtener todas las reglas de un proceso.
}
