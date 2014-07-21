package com.roisin.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.repositories.RipperSettingsRepository;

@Service
@Transactional
public class RipperSettingsService {

	@Autowired
	private RipperSettingsRepository ripperSettingsRepository;

	public RipperSettingsService() {
		super();
	}

	public RipperSettings create() {
		RipperSettings ripperSettings = new RipperSettings();

		return ripperSettings;
	}

	public RipperSettings findOne(int ripperSettingsId) {
		return ripperSettingsRepository.findOne(ripperSettingsId);
	}

	public void save(RipperSettings ripperSettings) {

		ripperSettingsRepository.save(ripperSettings);
	}

}
