package com.roisin.spring.services;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.roisin.core.processes.Preprocessing;
import com.roisin.spring.utils.Constants;

@Service
public class PreprocessingService {

	private static final Logger logger = LoggerFactory.getLogger(PreprocessingService.class);

	public static ExampleSet getExampleSetFromFile(String fileName, String filePath)
			throws OperatorException {
		try {
			logger.info("Iniciando rapidminer");
			RapidMiner.setExecutionMode(ExecutionMode.APPSERVER);
			RapidMiner.init();
		} catch (Exception e) {
			logger.error("No ha sido posible iniciar Rapidminer. Revise la configuración.");
		}
		Process process = Preprocessing.getExampleSetFromFileProcess(
				StringUtils.substringAfterLast(fileName, Constants.DOT_SYMBOL), filePath);
		IOContainer container = process.run();
		return (ExampleSet) container.asList().get(0);
	}

}
