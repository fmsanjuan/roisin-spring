package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.TreeToRulesSettings;
import com.roisin.spring.repositories.TreeToRulesSettingsRepository;

@Service
@Transactional
public class TreeToRulesSettingsService {

	/**
	 * Tree to rules settings repository
	 */
	@Autowired
	private transient TreeToRulesSettingsRepository treeSettingsRepo;

	public TreeToRulesSettingsService() {
		super();
	}

	public TreeToRulesSettings create() {
		final TreeToRulesSettings settings = new TreeToRulesSettings();

		return settings;
	}

	public TreeToRulesSettings findOne(final int identifier) {
		return treeSettingsRepo.findOne(identifier);
	}

	public TreeToRulesSettings save(final TreeToRulesSettings settings) {
		return treeSettingsRepo.save(settings);
	}

	public void delete(final TreeToRulesSettings settings) {
		treeSettingsRepo.delete(settings);
	}

	public Collection<TreeToRulesSettings> findAll() {
		return treeSettingsRepo.findAll();
	}

}
