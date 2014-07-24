package com.roisin.spring.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;

public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	public static boolean started = false;

	public static ExampleSet getExampleSetFromFile(String fileFormat, String path) {
		checkStarted();

		Process process = com.roisin.core.processes.Preprocessing.getExampleSetFromFileProcess(
				fileFormat, path);
		IOContainer container;
		ExampleSet res = null;
		try {
			container = process.run();
			res = (ExampleSet) container.asList().get(0);
			return res;
		} catch (OperatorException e) {
			logger.error("No ha sido posible extraer información del fichero");
		}
		return res;
	}

	public static void startRapidminer() {
		logger.info("Iniciando rapidminer");
		RapidMiner.setExecutionMode(ExecutionMode.APPSERVER);
		RapidMiner.init();
		started = true;
	}

	public static boolean isStarted() {
		return started;
	}

	public static void checkStarted() {
		if (!isStarted()) {
			startRapidminer();
		}
	}

}
