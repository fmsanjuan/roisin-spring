package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.DeletedRow;
import com.roisin.spring.repositories.DeletedRowRepository;

@Service
@Transactional
public class DeletedRowService {

	@Autowired
	private DeletedRowRepository deletedRowRepository;

	public DeletedRowService() {
		super();
	}

	public DeletedRow create() {
		DeletedRow deletedRow = new DeletedRow();

		return deletedRow;
	}

	public Collection<DeletedRow> findAll() {
		return deletedRowRepository.findAll();
	}

	public DeletedRow findOne(int deletedRowId) {
		return deletedRowRepository.findOne(deletedRowId);
	}

	public void save(DeletedRow deletedRow) {
		deletedRowRepository.save(deletedRow);
	}

	public void delete(DeletedRow deletedRow) {
		deletedRowRepository.delete(deletedRow);
	}

	// Métodos extras

	public Collection<DeletedRow> findFormDeletedRows(int formId) {
		return deletedRowRepository.findFormDeletedRows(formId);
	}

}
