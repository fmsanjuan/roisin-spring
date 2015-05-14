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

	/**
	 * Subgroup settings repository
	 */
	@Autowired
	private transient SubgroupSettingsRepository subgroupSetRepo;

	public SubgroupSettingsService() {
		super();
	}

	public SubgroupSettings create() {
		final SubgroupSettings subgroupSettings = new SubgroupSettings();

		return subgroupSettings;
	}

	public SubgroupSettings findOne(final int identifier) {
		return subgroupSetRepo.findOne(identifier);
	}

	public SubgroupSettings save(final SubgroupSettings subgroupSettings) {
		return subgroupSetRepo.save(subgroupSettings);
	}

	public void delete(final SubgroupSettings ripperSettings) {
		subgroupSetRepo.delete(ripperSettings);
	}

	public Collection<SubgroupSettings> findAll() {
		return subgroupSetRepo.findAll();
	}

}
