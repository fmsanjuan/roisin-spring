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

	/**
	 * Deleted row repository
	 */
	@Autowired
	private transient DeletedRowRepository delRowRepo;

	public DeletedRowService() {
		super();
	}

	public DeletedRow create() {
		final DeletedRow deletedRow = new DeletedRow();

		return deletedRow;
	}

	public Collection<DeletedRow> findAll() {
		return delRowRepo.findAll();
	}

	public DeletedRow findOne(final int deletedRowId) {
		return delRowRepo.findOne(deletedRowId);
	}

	public void save(final DeletedRow deletedRow) {
		delRowRepo.save(deletedRow);
	}

	public void delete(final DeletedRow deletedRow) {
		delRowRepo.delete(deletedRow);
	}

	// Métodos extras

	public Collection<DeletedRow> findFormDeletedRows(final int formId) {
		return delRowRepo.findFormDeletedRows(formId);
	}

	public void saveAndOverwrite(final DeletedRow deletedRow) {
		DeletedRow checkDeletedRow = delRowRepo.findSpecificDeletedRow(deletedRow
				.getPreprocessingForm().getId(), deletedRow.getNumber());
		if (checkDeletedRow == null) {
			save(deletedRow);
		}
	}

}
