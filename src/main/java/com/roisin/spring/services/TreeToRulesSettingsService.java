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

	@Autowired
	private TreeToRulesSettingsRepository treeToRulesSettingsRepository;

	public TreeToRulesSettingsService() {
		super();
	}

	public TreeToRulesSettings create() {
		TreeToRulesSettings treeToRulesSettings = new TreeToRulesSettings();

		return treeToRulesSettings;
	}

	public TreeToRulesSettings findOne(int treeToRulesSettingsId) {
		return treeToRulesSettingsRepository.findOne(treeToRulesSettingsId);
	}

	public TreeToRulesSettings save(TreeToRulesSettings treeToRulesSettings) {
		return treeToRulesSettingsRepository.save(treeToRulesSettings);
	}

	public void delete(TreeToRulesSettings treeToRulesSettings) {
		treeToRulesSettingsRepository.delete(treeToRulesSettings);
	}

	public Collection<TreeToRulesSettings> findAll() {
		return treeToRulesSettingsRepository.findAll();
	}

	// TODO: Método para obtener todas las reglas de un proceso.
}
