package com.roisin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roisin.spring.model.RipperSettings;

@Repository
public interface RipperSettingsRepository extends JpaRepository<RipperSettings, Integer> {

}
