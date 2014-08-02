package com.roisin.spring.services;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.roisin.spring.model.File;
import com.roisin.spring.model.User;
import com.roisin.spring.repositories.FileRepository;
import com.roisin.spring.utils.Constants;
import com.roisin.spring.utils.FileUtils;

@Service
@Transactional
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	UserService userService;

	public FileService() {
		super();
	}

	public File create() {
		File file = new File();

		return file;
	}

	public Collection<File> findAll() {
		return fileRepository.findAll();
	}

	public File findOne(int fileId) {
		return fileRepository.findOne(fileId);
	}

	public void save(File file) {
		fileRepository.save(file);
	}

	public void delete(File file) {
		Assert.notNull(file);
		User principal = userService.findByPrincipal();
		boolean isOwner = principal.equals(file.getUser());
		Assert.isTrue(isOwner);
		fileRepository.delete(file);
	}

	// Otros métodos

	public Collection<File> findFilesByUserId(int userId) {
		return fileRepository.findFilesByUserId(userId);
	}

	public String writeFileFromDb(File file) {
		byte[] fileArray = file.getOriginalFile();
		String fileFormat = StringUtils.substringAfterLast(file.getName(), Constants.DOT_SYMBOL);
		String tmpPath = Constants.STORAGE_PATH + file.getHash() + Constants.DOT_SYMBOL
				+ fileFormat;
		// Escritura en disco del fichero
		FileUtils.writeFileFromByteArray(fileArray, tmpPath);

		return tmpPath;
	}

	public File findFileByFormId(int formId) {
		return fileRepository.findFileByFormId(formId);
	}
}
