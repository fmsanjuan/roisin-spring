package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.repositories.RipperSettingsRepository;

@Service
@Transactional
public class RipperSettingsService {

	/**
	 * Ripper settings repository
	 */
	@Autowired
	private transient RipperSettingsRepository ripperSetRepo;

	public RipperSettingsService() {
		super();
	}

	public RipperSettings create() {
		final RipperSettings ripperSettings = new RipperSettings();

		return ripperSettings;
	}

	public RipperSettings findOne(final int ripperSettingsId) {
		return ripperSetRepo.findOne(ripperSettingsId);
	}

	public RipperSettings save(final RipperSettings ripperSettings) {
		return ripperSetRepo.save(ripperSettings);
	}

	public void delete(final RipperSettings ripperSettings) {
		ripperSetRepo.delete(ripperSettings);
	}

	public Collection<RipperSettings> findAll() {
		return ripperSetRepo.findAll();
	}

}
