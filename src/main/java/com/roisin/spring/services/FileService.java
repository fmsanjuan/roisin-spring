package com.roisin.spring.services;

import static com.roisin.spring.utils.Constants.DOT_SYMBOL;
import static com.roisin.spring.utils.Constants.ROISIN_NULL;
import static com.roisin.spring.utils.Constants.SHA_256;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.rapidminer.example.ExampleSet;
import com.roisin.spring.exception.RoisinSpringException;
import com.roisin.spring.model.File;
import com.roisin.spring.model.User;
import com.roisin.spring.repositories.FileRepository;
import com.roisin.spring.utils.HashUtils;
import com.roisin.spring.utils.Runner;

@Service
@Transactional
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUtils fileUtils;

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
		Assert.notNull(fileId);
		User principal = userService.findByPrincipal();
		File file = fileRepository.findOne(fileId);
		boolean isOwner = principal.equals(file.getUser());
		Assert.isTrue(isOwner);
		return file;
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
		String fileFormat = StringUtils.substringAfterLast(file.getName(), DOT_SYMBOL);
		String tmpPath = fileUtils.getStoragePath() + file.getHash() + DOT_SYMBOL + fileFormat;
		// Escritura en disco del fichero
		fileUtils.writeFileFromByteArray(fileArray, tmpPath);

		return tmpPath;
	}

	public File findFileByFormId(int formId) {
		return fileRepository.findFileByFormId(formId);
	}

	public String upload(MultipartFile file) throws RoisinSpringException {
		File roisinFile = create();
		roisinFile.setName(file.getOriginalFilename());
		roisinFile.setDescription(ROISIN_NULL);
		try {
			String hash = HashUtils.fileChecksum(file.getBytes(), SHA_256);
			roisinFile.setHash(hash);
			roisinFile.setOriginalFile(file.getBytes());
		} catch (IOException e) {
			throw new RoisinSpringException(
					"No ha sido posible acceder al fichero subido por el usuario", e);
		}
		roisinFile.setUser(userService.findByPrincipal());
		save(roisinFile);
		return file.getOriginalFilename();
	}

	public ExampleSet getExampleSetFromFile(File file) {
		byte[] fileArray = file.getOriginalFile();
		String tmpPath = fileUtils.getFileTmpPath(file);

		fileUtils.writeFileFromByteArray(fileArray, tmpPath);
		ExampleSet exampleSet = Runner
				.getExampleSetFromFile(fileUtils.getFileFormat(file), tmpPath);
		return exampleSet;
	}
}
