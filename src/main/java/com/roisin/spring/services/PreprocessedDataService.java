package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.PreprocessedData;
import com.roisin.spring.repositories.PreprocessedDataRepository;

@Service
@Transactional
public class PreprocessedDataService {

	@Autowired
	private PreprocessedDataRepository preprocessedDataRepository;

	@Autowired
	private PreprocessingFormService preprocessingFormService;

	public PreprocessedDataService() {
		super();
	}

	public PreprocessedData create() {
		PreprocessedData preprocessedData = new PreprocessedData();

		return preprocessedData;
	}

	public Collection<PreprocessedData> findAll() {
		return preprocessedDataRepository.findAll();
	}

	public PreprocessedData findOne(int preprocessedDataId) {
		return preprocessedDataRepository.findOne(preprocessedDataId);
	}

	public PreprocessedData save(PreprocessedData preprocessedData) {
		return preprocessedDataRepository.save(preprocessedData);
	}

	public void delete(PreprocessedData preprocessedData) {
		preprocessedDataRepository.delete(preprocessedData);
	}

	// Extra methods

	public Collection<PreprocessedData> findDataByFileId(int fileId) {
		return preprocessedDataRepository.findDataByFileId(fileId);
	}

	public Collection<PreprocessedData> findNullData(int fileId) {
		return preprocessedDataRepository.findNullDataByFileId(fileId);
	}

	public void deleteNullData(Collection<PreprocessedData> data) {
		preprocessedDataRepository.deleteInBatch(data);
	}

}
