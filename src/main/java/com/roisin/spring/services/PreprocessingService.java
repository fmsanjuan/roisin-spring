package com.roisin.spring.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.roisin.core.processes.Preprocessing;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.utils.Constants;

@Service
public class PreprocessingService {

	private static final Logger logger = LoggerFactory.getLogger(PreprocessingService.class);

	public ExampleSet getExampleSetFromFile(MultipartFile file, String filePath) {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		ExampleSet exampleSet = null;

		try {
			inputStream = file.getInputStream();
			File newFile = new File(filePath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			logger.info("Iniciando rapidminer");
			RapidMiner.setExecutionMode(ExecutionMode.APPSERVER);
			RapidMiner.init();
			logger.error("No ha sido posible iniciar Rapidminer. Revise la configuración.");
			Process process = Preprocessing
					.getExampleSetFromFileProcess(StringUtils.substringAfterLast(
							file.getOriginalFilename(), Constants.DOT_SYMBOL), filePath);
			IOContainer container = process.run();
			exampleSet = (ExampleSet) container.asList().get(0);
		} catch (IOException e) {
			logger.error("Imposible subir el fichero al servidor", e);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exampleSet;
	}

	public PreprocessingForm calculateFilterCondition(PreprocessingForm form) {
		// Transformación de condición provisional
		if (!StringUtils.isBlank(form.getFilterValue())) {
			StringBuilder condition = new StringBuilder();
			condition.append(form.getFilterAttribute());
			if (form.getFilterOperator().equals(Constants.EQUALS)) {
				condition.append(Constants.EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.NON_EQUALS)) {
				condition.append(Constants.NON_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_OR_EQUALS)) {
				condition.append(Constants.GREATER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_OR_EQUALS)) {
				condition.append(Constants.SMALLER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_THAN)) {
				condition.append(Constants.SMALLER_THAN_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_THAN)) {
				condition.append(Constants.GREATER_THAN_SYMBOL);
			}
			condition.append(form.getFilterValue());
			form.setFilterCondition(condition.toString());
		}
		return form;
	}

}
